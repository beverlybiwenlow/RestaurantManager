package Reservation;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Layout of restaurant tables for a certain day
 * @author tinghaong
 *
 */
class RestaurantLayoutDaily {
	/** first position for each table size*/
	private int firstPos[];
	/** number of free tables in the morning session */
	private int freeTablesAM[];
	/** number of free tables in the evening session */
	private int freeTablesPM[];
	/** ArrayList of tables in the restaurant to be used in the morning session */
	private ArrayList<Table> tableLayoutsAM;
	/** ArrayList of tables in the restaurant to be used in the evening session */
	private ArrayList<Table> tableLayoutsPM;
	/** TableType which stores the number of each sizes of tables */
	private TableType initialiseTables;
	
	/** ArrayList of all the bookings for a certain day */
	private ArrayList<Booking> allDayBookings; //Add into class diagram
	/** Date of this restaurant layout*/
	private int Date; //Add this to class diagram!
	
	/**Initialise the Restaurant layout
	 * 
	 * @param Date of restaurant layout
	 * @throws IOException
	 */
	protected RestaurantLayoutDaily(int date) throws IOException {
		Date=date;
		firstPos = new int[11];
		freeTablesAM = new int[11];
		freeTablesPM = new int[11];
		initialiseTables=new TableType();
		tableLayoutsAM = new ArrayList<Table>();
		tableLayoutsPM = new ArrayList<Table>();
		initialiseTables();
		
		allDayBookings=new ArrayList<Booking>();
		
	}
	
	/** Initialise the ArrayList of AM and PM tables based on TableType
	 * 
	 * @throws IOException
	 */
	private void initialiseTables() throws IOException {
		//initiliase the layout of the restaurant using the number 
		//of 2,4,8,10 seaters defined in tabletype
		int tables = 0;
		freeTablesAM[2] = getNoOfTables(2);
		freeTablesPM[2] = getNoOfTables(2);
		firstPos[2] = tables;
		for(int i = 0; i < getNoOfTables(2); i++) {
			tableLayoutsAM.add(new Table(2, tables +1));
			tableLayoutsPM.add(new Table(2, tables +1));
			tables++;
		}
		
		freeTablesAM[4] = getNoOfTables(4);
		freeTablesPM[4] = getNoOfTables(4);
		firstPos[4] = tables;
		for(int i = 0; i < getNoOfTables(4); i++) {
			tableLayoutsAM.add(new Table(4, tables +1));
			tableLayoutsPM.add(new Table(4, tables +1));
			tables++;
		}
		
		freeTablesAM[8] = getNoOfTables(8);
		freeTablesPM[8] = getNoOfTables(8);
		firstPos[8] = tables;
		for(int i = 0; i < getNoOfTables(8); i++) {
			tableLayoutsAM.add(new Table(8, tables +1));
			tableLayoutsPM.add(new Table(8, tables +1));
			tables++;
		}
		
		freeTablesAM[10] = getNoOfTables(10);
		freeTablesPM[10] = getNoOfTables(10);
		firstPos[10] = tables;
		for(int i = 0; i < getNoOfTables(10); i++) {
			tableLayoutsAM.add(new Table(10, tables +1));
			tableLayoutsPM.add(new Table(10, tables +1));
			tables++;
		}
	}
	
	/**Returns number of tables for a certain number of seats
	 * 
	 * @param number of seats
	 * @return total number of tables with a certain size
	 */
	protected int getNoOfTables(int seats) {   //Gets the correct type of table
		return initialiseTables.getTables(seats);
	}

	/**Returns the number of free tables currently
	 * 
	 * @param time forAM or PM
	 * @param size of table
	 * @return number of free tables of that size
	 */
	protected int getFreeTables(boolean time, int seats) {
		if (time==true) {
			return freeTablesAM[seats];
		}
		else {		
			return freeTablesPM[seats];
		}
	}
	
	/**Returns the date of the layout
	 * 
	 * @return date of layout
	 */
	protected int getDate() {
		return Date;
	}
	
	/**Adds reservation into an allocated free table 
	 * 
	 * @param time AM or PM
	 * @param number of seats required
	 * @return ID of the tabled allocated
	 * @throws IOException
	 */
	protected int addReservation(boolean time, int seats) throws IOException { 
		//Searches for an available table and set it to booked if there is one
		//Returns id of table to be stored in reservation
		int tableFirstPos = firstPos[seats];
		if (time) {
			if (freeTablesAM[seats] == 0) {
				System.out.println("Restaurant is fully booked!");
				return -1;
			}
			for (int i = 0; i < getNoOfTables(seats); i++) {
				if (tableLayoutsAM.get(tableFirstPos + i).getBooked() == false) {
					tableLayoutsAM.get(tableFirstPos + i).setBooked(true);
					freeTablesAM[seats]--;
					return tableLayoutsAM.get(tableFirstPos + i).getTableId();
					}
				}
			return -1;
		}
		else {
			if (freeTablesPM[seats] == 0) {
				System.out.println("Restaurant is fully booked!");
				return -1;
			}
			for (int i = 0; i < getNoOfTables(seats); i++) {
				if (tableLayoutsPM.get(tableFirstPos + i).getBooked() == false) {
					tableLayoutsPM.get(tableFirstPos + i).setBooked(true);
					freeTablesPM[seats]--;
					return tableLayoutsPM.get(tableFirstPos + i).getTableId();
					}
				}	
			return -1;
		}
	}
	
	/**Adds a reservation for the day for walk in customers
	 * 
	 * @param time AM or PM
	 * @param number of seats required
	 * @return ID of the table allocated
	 */
	protected int addWalkIn(boolean time, int seats) {
		//Checks for available tables on the day itself and returns id of free table
		//Returns -1 if there's an error
		int tableFirstPos = firstPos[seats];
		if (time) {
			if(freeTablesAM[seats] == 0) {
				System.out.println("Restaurant is full!");
				return -1;
			}
			for (int i = 0; i < getNoOfTables(seats); i++) {
				if (tableLayoutsAM.get(tableFirstPos + i).getBooked() == false) {
					tableLayoutsAM.get(tableFirstPos + i).setBooked(true);
					freeTablesAM[seats]--;
					return tableLayoutsAM.get(tableFirstPos + i).getTableId();
					}	
			}
		}
		else {
			if(freeTablesPM[seats] == 0) {
				System.out.println("Restaurant is full!");
				return -1;
			}
			for (int i = 0; i < getNoOfTables(seats); i++) {
				if (tableLayoutsPM.get(tableFirstPos + i).getBooked() == false) {
					tableLayoutsPM.get(tableFirstPos + i).setBooked(true);
					freeTablesPM[seats]--;
					return tableLayoutsPM.get(tableFirstPos + i).getTableId();
					}	
			}
		}
		return -1;
	}
	
	/**Returns the desired table from the layout
	 * 
	 * @param time AM or PM
	 * @param tableId of desired Table
	 * @param occupied to set table as occupied or not
	 * @return
	 */
	protected Table getTable(boolean time, int tableId, boolean occupied) {
		//returns table upon arrival into the restaurant
		if (time) {
			if (occupied) 
			tableLayoutsAM.get(tableId-1).setOccupied(true);
			return tableLayoutsAM.get(tableId-1);
		}
		else {
			if (occupied)
			tableLayoutsPM.get(tableId-1).setOccupied(true);
			return tableLayoutsPM.get(tableId-1);
		}
	}
	
	/**Removes reservation from a certain table
	 * 
	 * @param time AM or PM
	 * @param tableId of table to remove reservation from
	 */
	protected void removeReservation(boolean time, int tableId) { 
		//Remove Table reservation
		if (time==true) {
			freeTablesAM[tableLayoutsAM.get(tableId-1).getSeats()]++;
			tableLayoutsAM.get(tableId-1).setBooked(false);
		}
		else {
			freeTablesPM[tableLayoutsAM.get(tableId-1).getSeats()]++;
			tableLayoutsPM.get(tableId-1).setBooked(false);
		}
		
	}
	
	/**Adds a booking to the ArrayList of booking
	 * 
	 * @param reservation to be stored in the ArrayList
	 */
	protected void passBookingtoAdd(Booking reservation) {
		allDayBookings.add(reservation);
	}
	
	/**Removes a booking from the ArrayList of booking
	 * 
	 * @param booking used to find the booking to remove
	 */
	protected void passBookingtoRemove(Booking reservation) {
		for(int i=0; i<allDayBookings.size(); i++) {
			if(allDayBookings.get(i).getBookingID()==reservation.getBookingID())
				allDayBookings.remove(i);
		}
	}
	
	/**Returns the ArrayList of all the booking for the day
	 * 
	 * @return ArrayList of all bookings for the day
	 */
	protected ArrayList<Booking> returnDayBookings() {
		return allDayBookings;
	}
	
	/**Returns an ArrayList of Booking for the day's AM or PM session
	 * 
	 * @param A boolean: True for Morning session and False for afternoon session
	 * @return ArrayList of bookings for the AM or PM session for the day
	 */
	protected ArrayList<Booking> returnBooking(boolean time) { 
		ArrayList<Booking> tablesAtTime=new ArrayList<Booking>();
		for(int i=0; i<allDayBookings.size(); i++) {
			if(allDayBookings.get(i).getTime()==time) 
				tablesAtTime.add(allDayBookings.get(i));
		}
		return tablesAtTime;
	}
	
	/**Finds the correct reservation based on phone number
	 * 
	 * @param The customer's phone used to reserve reservations
	 * @return The booking with the matching phone number
	 */
	protected Booking returnReservedBooking(int phone) {
		int reservecounter=0;
		Booking tempbooking=new Booking();
		for(int i=0; i<allDayBookings.size(); i++) {
			if(allDayBookings.get(i).getPhone()==phone) {
				tempbooking = allDayBookings.get(i);
				reservecounter++;
			}
		}
			
		if (reservecounter==0) 
			tempbooking.setName("NoBooking");
		return tempbooking;
	}	

}
