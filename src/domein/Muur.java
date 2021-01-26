package domein;

/**
 * Muur is een subklasse van Veld
 */
public class Muur extends Veld {

	public Muur(int xCord, int yCord) {
		super(xCord, yCord, false, false);
	}
	public Muur(int veldID ,int xCord, int yCord) {
		super(veldID, xCord, yCord, false, false);
	}

}
