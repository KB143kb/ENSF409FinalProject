public class FurnitureNotAvailableException extends Exception {
	public FurnitureNotAvailableException () {
		super("The furniture type you want to order is not available");
	}
}
