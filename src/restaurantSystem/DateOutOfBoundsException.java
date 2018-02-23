package restaurantSystem;

/**Exception class to handle dates that are out of the 1 month range
 * 
 * @author tinghaong
 *
 */
public class DateOutOfBoundsException extends Exception {
	
	public DateOutOfBoundsException() {
	super("Date exceeds 1 month limit!");
	}
}
