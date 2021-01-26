package persistentie;

import domein.Speler;
import resources.Taal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpelerMapper {

	/**
	 * Geeft de speler terug van de gegeven gebruikersnaam
	 * 
	 * @param gebruikersnaam, de gebruikersnaam van de speler die uit de databank gehaald moet worden als die bestaat
	 * @return De speler met de gebruikersnaam
	 */
	public Speler geefSpeler(String gebruikersnaam) {
		Speler speler = null;
		
					speler = new Speler("pvk", "pvk", true, "Van Keer", "Pieter", 2);


		return speler;

	}
	
	/**
	 * Geeft de speler terug van het gegeven id
	 * 
	 * @param id, het id van de speler die uit de databank gehaald moet worden als die bestaat
	 * @return De speler met het id
	 */
	public Speler geefSpeler(int id) {
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn
						.prepareStatement("SELECT * FROM ID222177_g32.Speler WHERE id= ?")) {
			query.setInt(1, id);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					String gebruikersnaamDB = rs.getString("gebruikersnaam");
					String wachtwoord = rs.getString("wachtwoord");
					boolean isAdmin = rs.getBoolean("isAdmin");
					String naam = rs.getString("naam");
					String voornaam = rs.getString("voornaam");
					int spelerID = rs.getInt("id");
					speler = new Speler(gebruikersnaamDB, wachtwoord, isAdmin, naam, voornaam, spelerID);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return speler;

	}

	/**
	 * Voegt de meegegeven speler toe aan de databank
	 * 
	 * @param speler, de speler die moet toegevoegd worden.
	 */
	public void voegSpelerToe(Speler speler) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

				PreparedStatement query = conn.prepareStatement(
						"INSERT INTO ID222177_g32.Speler (gebruikersnaam, wachtwoord, isAdmin,naam,voornaam)"
								+ "VALUES (?,?,?,?,?)")) {

			query.setString(1, speler.getGebruikersnaam());
			query.setString(2, speler.getWachtwoord());
			query.setBoolean(3, speler.getIsAdmin());
			query.setString(4, speler.getNaam());
			query.setString(5, speler.getVoornaam());

			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Controleert of de meegegeven gebruikersnaam van de speler al in de databank zit (niet uniek is), als dit zo is wordt hij niet toegevoegd
	 * 
	 * @param speler, de speler die moet toegevoegd worden maar waarvan eerst gecontroleerd moet worden of de gebruikersnaam uniek is. .
	 */
	public void controleerUniekeGebruikersnaam(Speler speler) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

				PreparedStatement query = conn
						.prepareStatement("SELECT * FROM ID222177_g32.Speler WHERE gebruikersnaam = ?")) {
			query.setString(1, speler.getGebruikersnaam());
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					throw new IllegalArgumentException(Taal.getVertaling("bestaatAlError"));
				} else {
					voegSpelerToe(speler);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Geeft het id van de meegegeven speler terug
	 * 
	 * @param speler
	 * @return id van de overeenkomende speler
	 */
	public int geefIdSpeler(Speler speler) {
		int id = 0;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn
						.prepareStatement("SELECT id FROM ID222177_g32.Speler WHERE gebruikersnaam = ?")) {
			query.setString(1, speler.getGebruikersnaam());
			ResultSet rs = query.executeQuery();
			id = rs.getInt("id");
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return id;
	}
}
