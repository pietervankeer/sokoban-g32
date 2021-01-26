package exception;
/**
 * Exception die wordt gegooid wanneer de gebruiker een foute beweging maakt
 * (tegen een muur bewegen, tegen een kist bewegen die niet verder geduwt kan worden,...)
 */
public class fouteBewegingException extends Exception{

	public fouteBewegingException(String message) {
		super(message);
	}
	
}
