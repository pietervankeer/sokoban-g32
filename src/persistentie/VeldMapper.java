package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domein.Doel;
import domein.Muur;
import domein.Spel;
import domein.Spelbord;
import domein.Veld;

public class VeldMapper {
	
	// UC 4
	/**
	 * Geeft de spelborden terug die in het spel met de meegegeven naam zitten
	 * 
	 * @param spelNaam
	 * @return lijst van alle spelborden die in het bepaalde spel zitten
	 */
	public List<Spelbord> geefSpelborden(String spelNaam) {
		
		List<Spelbord> spelborden = new ArrayList<Spelbord>();
		List<Integer> spelbordIDs = new ArrayList<Integer>();
		

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn
						.prepareStatement("SELECT * "
								+ "FROM ID222177_g32.Spelbord sp " 
								+ "JOIN  ID222177_g32.Spel s ON sp.idSpel = s.idSpel " 
								+ "WHERE naam = ?")) {
			query.setString(1, spelNaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					do {
						spelbordIDs.add(rs.getInt("idSpelbord"));
					} while (rs.next());
				}
			}
		
			List<Integer> uniqueSpelbordIDs = spelbordIDs.stream().distinct().collect(Collectors.toList());

			for (int kekw : uniqueSpelbordIDs) {
				spelborden.add(new Spelbord(geefVelden(conn, kekw, spelNaam), kekw));
			}
		
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return spelborden;

	}

	/**
	 * Geeft alle velden die in een bepaald spelbord zitten, en die behoren tot een bepaald spel
	 * 
	 * @param spelbordID
	 * @param spelNaam
	 * @return lijst van velden die behoort tot het spelbord met spelbordID en spel met spelNaam
	 */
	private List<Veld> geefVelden(Connection conn, int spelbordID, String spelNaam) {

		List<Veld> spelbord = new ArrayList<Veld>();

		try (PreparedStatement query = conn
						.prepareStatement("SELECT sp.idSpel, s.idSpelbord, s.idVeld, v.xCord, v.yCord, v.typeVeld "
								+ "FROM ID222177_g32.Spelbord s " + "JOIN ID222177_g32.Veld v on s.idVeld = v.veldID "
								+ "JOIN ID222177_g32.Spel sp on s.idSpel = sp.idSpel "
								+ "WHERE idSpelbord = ? AND naam = ?")) {
			query.setInt(1, spelbordID);
			query.setString(2, spelNaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					do {
						int idVeld = rs.getInt("idVeld");
						int yCord = rs.getInt("yCord");
						int xCord = rs.getInt("xCord");
						String type = rs.getString("typeVeld");

						if (type.equals("M")) {
							spelbord.add(new Muur(idVeld, xCord, yCord));
						} else if (type.equals("D")) {
							spelbord.add(new Doel(idVeld, xCord, yCord));
						} else if (type.equals("P")) {
							spelbord.add(new Veld(idVeld, xCord, yCord, true, false));
						} else if (type.equals("K")) {
							spelbord.add(new Veld(idVeld, xCord, yCord, false, true));
						}

					} while (rs.next());
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return spelbord;
	}

	// UC 5
	/**
	 * Voert voor alle velden uit het spelbord voegVeldToe methode uit
	 * 
	 * @param spelbord
	 * @param spelID
	 */
	public void voegSpelbordToe(Spelbord spelbord, int spelID) {

		// veld wegschrijven naar databank table Veld
		
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
			for (Veld v : spelbord.getVelden()) {		
				voegVeldToe(conn, v, spelbord.getId(), spelID);
				
			}
		
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
			
	}
	
	// UC 6
	/**
	 * Geeft het id van het veld dat overeenkomt met meegegeven coordinaten en type
	 * 
	 * @param yCord
	 * @param xCord
	 * @param type
	 * @return id van het gevonden veld
	 */
	private int geefVeldID(int yCord, int xCord, String type) {
		int veldID = 0;

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(
						"SELECT veldID FROM ID222177_g32.Veld WHERE yCord = ? AND xCord = ? AND typeVeld = ? ORDER BY veldID DESC")) {
			query.setInt(1, yCord);
			query.setInt(2, xCord);
			query.setString(3, type);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					veldID = rs.getInt("veldID");
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return veldID;
	}
	
	/**
	 * Bepaalt het type van een meegegeven veld
	 * 
	 * @param v veld
	 * @return het type van het veld (P, K, M, D)
	 */
	private String bepaalType(Veld v) {
		String type = "";
		if (v instanceof Doel) {
			type = "D";
		} else if (v instanceof Muur) {
			type = "M";
		} else if (v.getIsKist()) {
			type = "K";
		} else if (v.getIsMannetje()) {
			type = "P";
		} else {
			type = "";
		}
		return type;
	}

	/**
	 * Voegt een veld toe aan de databank, voegt dat veld ook toe in de tabel Spelbord
	 * 
	 * @param v veld
	 * @param spelbordID
	 * @param spelID
	 */
	private void voegVeldToe(Connection conn, Veld v, int spelbordID, int spelID) {

		String type = bepaalType(v);
		
		try (PreparedStatement query = conn.prepareStatement(
						"INSERT INTO ID222177_g32.Veld (yCord, xCord, typeVeld)" + "VALUES (?,?,?)")) {
			query.setInt(1, v.getyCord());
			query.setInt(2, v.getxCord());
			query.setString(3, type);
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
		int veldID = geefVeldID(v.getyCord(), v.getxCord(), type);
		
		try (PreparedStatement query = conn.prepareStatement(
						"INSERT INTO ID222177_g32.Spelbord (idSpelbord, idSpel, idVeld)" + "VALUES (?,?,?)")) {
			query.setInt(1, spelbordID);
			query.setInt(2, spelID);
			query.setInt(3, veldID);
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Registreert alle wijzigingen aan de spelborden in de databank
	 * 
	 * @param spelborden, spelborden die gewijzigd werden door admin
	 * @param spelID
	 * @param spelNaam
	 */
	public void wijzigSpelbord(Spelbord spelbord, int spelID, String spelNaam) {
		
		// door alle spelborden in je spel loopen
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
			verwijderSpelbord(spelbord.getId(), spelNaam);
			
			// door alle velden in je spelbord loopen
			for (Veld v : spelbord.getVelden()) {
					voegVeldToe(conn, v, spelbord.getId(), spelID);	
			}
		
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Overschrijft een bestaand veld in de databank met een nieuw meegegeven veld
	 * 
	 * @param v veld
	 */
	public void wijzigVeld(Veld v) {
		String type = bepaalType(v);

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn
						.prepareStatement("UPDATE ID222177_g32.Veld SET typeVeld = ? WHERE xCord = ? AND yCord = ?")) {
			query.setString(1, type);
			query.setInt(2, v.getxCord());
			query.setInt(3, v.getyCord());
			query.executeUpdate();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Verwijdert een veld uit de databank dat overeenkomt met het id van het meegegeven veld, verwijdert daarna ook de link met het spelbord
	 * 
	 * @param v veld
	 */
	private void verwijderVeld(Connection conn, Veld v) {
		
		// eerst in tabel Spelbord
		try (PreparedStatement query = conn.prepareStatement("DELETE FROM ID222177_g32.Spelbord WHERE idVeld = ?")) {
			query.setInt(1, v.getVeldID());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
		// Daarna in tabel Veld
		try (PreparedStatement query = conn.prepareStatement("DELETE FROM ID222177_g32.Veld WHERE veldID = ?")) {
			query.setInt(1, v.getVeldID());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Verwijdert het hele spelbord (alle velden) en ook de link met spel
	 * 
	 * @param spelbordID
	 * @param spelNaam
	 */
	public void verwijderSpelbord(int spelbordID, String spelNaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
			List<Veld> velden = geefVelden(conn, spelbordID, spelNaam);
			
			for (Veld v : velden) {
				verwijderVeld(conn, v);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
