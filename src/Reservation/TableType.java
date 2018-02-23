package Reservation;

/**Stores the number of tables of each size in the restaurant
 * 
 * @author tinghaong
 *
 */
class TableType {
	/** number of tables of each size */
	private int tenseater, eightseater, fourseater, twoseater;
	
	/**
	 * Constructor to initialise the number of each tables
	 */
	protected TableType() {
		tenseater=5;
		eightseater=5;
		fourseater=10;
		twoseater=10;
	}
	
	/** Returns the number of tables of a certain size
	 * 
	 * @param number of seats of the table
	 * @return number of tables
	 */
	protected int getTables(int seats) { //Returns how many tables there are for a type of table (10/8/4/2 seats)
		int noOfTables=0;
		switch(seats) {
		case 10:
			noOfTables=tenseater;
			break;
		case 8: 
			noOfTables=eightseater;
			break;
		case 4:
			noOfTables=fourseater;
			break;
		case 2: 
			noOfTables=twoseater;
			break;
		} return noOfTables;
	}
}