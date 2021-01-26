package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Spel;
import domein.Spelbord;
import domein.Speler;

public class SpelMapper {
	// properties
	private SpelerMapper spelerMapper = new SpelerMapper();
	private VeldMapper veldMapper = new VeldMapper();

	// public methods
	/**
	 * Geeft alle spellen terug in de databank
	 * 
	 * @return lijst van alle spellen
	 */
	public List<Spel> geefSpellen() {
		
		List<Spel> spellen = new ArrayList<Spel>();

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g32.Spel")) {
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					do {
						String spelNaam = rs.getString("naam");
						int spelID = rs.getInt("idSpel");
						spellen.add(new Spel(spelNaam, spelID));
					} while (rs.next());
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return spellen;

	}

	/**
	 * Geeft het spel uit de databank terug dat overeenkomt met de meegegeven naam
	 * 
	 * @param naam
	 * @return het overeenkomende spel
	 */
	public Spel geefSpel(String naam) {
		
		Spel spel = null;

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g32.Spel WHERE naam = ?")) {
			query.setString(1, naam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					do {
						String spelNaam = rs.getString("naam");
						int spelID = rs.getInt("idSpel");
						
						spel = new Spel(spelNaam, spelID);
					} while (rs.next());
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return spel;

	}
	
	/**
	 * Geeft alle spellen terug die als maker de meegegeven naam hebben
	 * 
	 * @param naam van de maker
	 * @return lijst van spellen die de de juiste maker hebben
	 */
	public List<Spel> geefSpellen(String naam) {

		List<Spel> spellen = new ArrayList<Spel>();

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g32.Spel WHERE maker = ?")) {
			query.setString(1, naam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					do {
						String spelNaam = rs.getString("naam");
						int spelID = rs.getInt("idSpel");
						spellen.add(new Spel(spelNaam, spelID));
					} while (rs.next());
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return spellen;

	}
	
	// UC 5
	/**
	 * Voegt het spel toe aan de databank met als maker de meegegeven speler
	 * 
	 * @param spel
	 * @param speler (maker)
	 */
	public void voegSpelToe(Spel spel, Speler speler) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g32.Spel (naam, maker)" + "VALUES (?,?)");
			{
				query.setString(1, spel.getNaam());
				query.setString(2, speler.getGebruikersnaam());
				query.executeUpdate();
			}
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
		for (Spelbord sp : spel.getSpelborden()) {
			veldMapper.voegSpelbordToe(sp, geefSpel(spel.getNaam()).getSpelID());
		}
	}
	
	// UC 7
	/**
	 * Verwijdert het meegegeven spel uit de databank
	 * 
	 * @param spel
	 */
	public void verwijderSpel(Spel spel) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = conn.prepareStatement("DELETE FROM ID222177_g32.Spel WHERE naam = ?");
			{
				query.setString(1, spel.getNaam());

				query.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
