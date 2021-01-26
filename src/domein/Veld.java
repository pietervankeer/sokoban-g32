package domein;

import resources.Taal;

/**
 * Een veld dat in een spelbord zit, een veld is ofwel een mannetje, een kist, een doel of een muur
 */
public class Veld {
	// properties
	private int veldID;
	private int xCord;
	private int yCord;
	/**
	 * Boolean die op true staat als het veld een mannetje is
	 */
	private boolean isMannetje;
	/**
	 * Boolean die op true staat als het veld een kist is
	 */
	private boolean isKist;

	// constructors
	/**
	 * Constructor met 4 parameters
	 * 
	 * @param xCord
	 * @param yCord
	 * @param isM
	 * @param isK
	 */
	public Veld(int xCord, int yCord, boolean isM, boolean isK) {
		setxCord(xCord);
		setyCord(yCord);
		setIsKist(isK);
		setIsMannetje(isM);
	}

	/**
	 * Constructor met 5 parameters (extra veld id)
	 * @param veldID
	 * @param xCord
	 * @param yCord
	 * @param isM
	 * @param isK
	 */
	public Veld(int veldID, int xCord, int yCord, boolean isM, boolean isK) {
		setVeldID(veldID);
		setxCord(xCord);
		setyCord(yCord);
		setIsMannetje(isM);
		setIsKist(isK);
	}

	// getters
	public int getxCord() {
		return xCord;
	}

	public int getyCord() {
		return yCord;
	}

	public boolean getIsMannetje() {
		return isMannetje;
	}

	public boolean getIsKist() {
		return isKist;
	}

	public int getVeldID() {
		return this.veldID;
	}

	// setters
	public final void setxCord(int xCord) {
		controleerCord(xCord);
		this.xCord = xCord;
	}

	public final void setyCord(int yCord) {
		controleerCord(yCord);
		this.yCord = yCord;
	}

	public final void setIsMannetje(boolean IsM) {
		this.isMannetje = IsM;
	}

	public final void setIsKist(boolean IsK) {
		this.isKist = IsK;
	}

	public final void setVeldID(int id) {
		this.veldID = id;
	}

	// hulpmethodes
	/**
	 * Controleert of de coordinaten van het veld binnen het bereik [0-9] ligt
	 * 
	 * @param cord
	 */
	private void controleerCord(int cord) {
		if (cord < 0 || cord > 9) {
			throw new IllegalArgumentException(Taal.getVertaling("foutCoordinaat"));
		}
	}

}
