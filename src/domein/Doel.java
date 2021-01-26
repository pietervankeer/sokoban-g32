package domein;

/**
* Doel is een subklasse van Veld
*/
public class Doel extends Veld {
	
	public Doel(int xCord, int yCord) {
		super(xCord, yCord, false, false);
	}

	public Doel(int veldID, int xCord, int yCord) {
		super(veldID, xCord, yCord, false, false);
	}

}
