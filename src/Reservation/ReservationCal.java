package Reservation;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

import restaurantSystem.FileReaderWriter;
import restaurantSystem.SystemFunctions;

public class ReservationCal {
	/** An ArrayList of days in the month. Days include all the reservations and tables for the day*/
	private ArrayList<RestaurantLayoutDaily> dailyLayout;
	/** An integer which is a date with the format YYYYMM*/
	private int monthOfYear;  //YYYYMM
	/** Integers which stores the values for the previous, current and next month*/
	private static int previousMonth, currentMonth, nextMonth;

	/** Checks if the reservation is from this month or the next month
	 * 
	 * @param A reservation from the Booking Class
	 * @return A number to indicate if the reservation is from the current or next month
	 * @throws IOException
	 */
	public static int isReservationThisMonth(Booking reservation)throws IOException{
		  //check if this month or next month
		  if ((reservation.getDate()/100)%100==(SystemFunctions.getCurrentDate()/100)%100){//i.e. next month
		   return currentMonth;
		  }else return nextMonth;  
	 }
	
	/** An overloaded method to take in the date of the reservation 
	 * and check if the reservation is from the current or next month
	 * @param A date formatted in YYYYMMDD in integer form
	 * @return A number to indicate if the reservation is from the current or next month
	 * @throws IOException
	 */
	public static int isReservationThisMonth(int date)throws IOException{ //returns correct month index
		  //check if this month or next month
		  if (date/100%100==SystemFunctions.getCurrentDate()/100%100){
		   return currentMonth;
		  }else return nextMonth;  
		 }
	/**Gets the index for the next month
	 * 
	 * @return The index for the next Month
	 */
	public static int getNextMonthIndex(){
		  return nextMonth;
		 }
	/** Gets the index for the previous month
	 * 
	 * @return The index for the previous month
	 */
	public static int getLastMonthIndex(){
		return previousMonth;
	}
	
	/**Sets the index for the current, previous and next month
	 * 
	 * @param current_month: The index for the current month
	 * @param previous_month: The index for the previous month
	 * @param next_month: The index for the next month
	 */
	public static void setMonthIndexes(int current_month, int previous_month, int next_month){
		previousMonth=previous_month;
		currentMonth=current_month;
		nextMonth=next_month;
	}
	/** Gets the index for the current month
	 * 
	 * @return The index for the current month
	 */
	public static int getCurrentMonthIndex(){
		return currentMonth;
	}

	/** Sets the month for ReservationCal
	 * 
	 * @param The month to set the month inside ReservationCal
	 */
	public void setMonth(int month) {
		int year = SystemFunctions.getCurrentDate()/100;
		monthOfYear=(year*100)+month; 
	}
	/**Gets the month of the ReservationCal
	 * 
	 * @return The month of the ReservationCal
	 */
	public int getMonth() {
		return monthOfYear;
	}
	/**Constructor to initialise the days in the month for ReservationCal
	 * 
	 * @param The month in format YYYYMM
	 * @throws IOException
	 */
	public ReservationCal(int yearmonth) throws IOException {   
		dailyLayout = new ArrayList<RestaurantLayoutDaily>();
		for(int i=0; i<SystemFunctions.getDaysInMonth(yearmonth); i++) { //Adds a new day object for the month into arrayList
			dailyLayout.add(new RestaurantLayoutDaily((yearmonth*100)+(i+1))); //modifieddate+1 appends the day to get YYYYMMDD
		}
	}	 
	/** Adds the reservation to the ReservationCal
	 * 
	 * @param A reservation of the Booking class
	 * @throws IOException
	 */
	public void addReservation(Booking reservation) throws IOException {  
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==reservation.getDate()) {
				if(checkTableAvailability(reservation.getDate(), reservation.getTime(), reservation.getType())==true) { //Add in the type of table to Booking class!!!
					int tableId = dailyLayout.get(i).addReservation(reservation.getTime(), reservation.getType());
					System.out.println("Reservation has been succesfully added!");
					reservation.setTableId(tableId);
					dailyLayout.get(i).passBookingtoAdd(reservation);
				}
				else 
					System.out.println("Sorry, there are no available tables");
			}
		}

	}
	/** Gets the particular table for the customer
	 * 
	 * @param date in the format YYYYMMDD
	 * @param Morning or Afternoon session
	 * @param Table ID of table
	 * @param If the table is occupied
	 * @return The correct table based on parameters
	 */
	public Table getTable(int date, boolean time, int table_id, boolean occupied) {
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==date) {
				Table temp = dailyLayout.get(i).getTable(time, table_id, occupied);
				return temp;
			}
		}
		return null;
	}
	/** Adds a Walk-in customer and gets the table ID for table assigned
	 * 
	 * @param date in the format YYYYMMDD
	 * @param Morning or Afternoon session
	 * @param The number of seats for the customer
	 * @return The table ID for the table assigned to the walk-in customer
	 */
	public int addWalkIn(int date, boolean time, int seats) {
		int tableId = -1;
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==date) {
				if(checkTableAvailability(date, time, seats)) { //Add in the type of table to Booking class!!!
					//Can simplify checkTableAvailability method to remove date from arguments passed
					tableId = dailyLayout.get(i).addWalkIn(time, seats);
					System.out.println("Success! There are available seats");
					return tableId;
				}
				else {
					System.out.println("Sorry, there are no available tables");
					return -1;
				}
			}
		}
		return tableId;
	}
	/** Checks the availability of tables
	 * 
	 * @param date in the format YYYYMMDD
	 * @param Morning or Afternoon session
	 * @param The number of seats for the customer
	 * @return A boolean to indicate if there is a table available
	 */
	public boolean checkTableAvailability(int date, boolean time, int seats) {
		boolean availability = false;
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==date) {
				if(dailyLayout.get(i).getFreeTables(time, seats) != 0)
					availability = true;
				else
					availability = false;
			}
		} return availability;
	}
	/**
	 * 
	 * @param Table ID of table
	 * @param date in the format YYYYMMDD
	 * @param Morning or Afternoon session
	 */
	public void removeOccupied(int table_id, int date, boolean time) {
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==date) {
				dailyLayout.get(i).removeReservation(time, table_id);
			}
		}
	}
	/** Removes the particular reservation
	 * 
	 * @param A reservation from the Booking class
	 */
	public void removeReservation(Booking reservation) {
		int removecounter=0;
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==reservation.getDate()) {
				ArrayList<Booking> bookingsEachDay=dailyLayout.get(i).returnDayBookings();
				for(int j=0; j<bookingsEachDay.size(); j++) {
					if(bookingsEachDay.get(j).getPhone()==reservation.getPhone()) {
						dailyLayout.get(i).removeReservation(reservation.getTime(), reservation.getTableId()); //Remove Table Reservation
						System.out.println("Reservation at "+reservation.getTimeExtended()+"h for "+reservation.getName()+"(pax#:"+reservation.getNoOfPeople()+") has been succesfully removed!");
						dailyLayout.get(i).passBookingtoRemove(reservation); //Remove Booking Reservation
						removecounter++;
					}
					 
				}
			}
		} 
		if(removecounter==0)
			System.out.println("No such reservation to be removed");
	} 

	/** Prints the number of tables available
	 * 
	 * @param date in the format YYYYMMDD
	 * @param The number of seats for the customer
	 * @param Morning or Afternoon session
	 */
	public void checkFreeTables(int date, int seats, boolean time) { //Check for the free tables on a date
		int counter=0;
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==date) {
				int availableTables=dailyLayout.get(i).getFreeTables(time, seats);
				System.out.println("There are " +availableTables+ " tables available to reserve");
				counter++;
				break;
			}
		}
		if(counter==0) 
			System.out.println("Incorrect Date");
	}
	/** Checks using the phone number if the customer has made a reservation
	 * 
	 * @param Phone number of customer
	 * @return The booking of the customer
	 */
	public Booking checkReservation(int phone) { //Change name in Class Diagram
		Booking specificBooking=new Booking();
		specificBooking.setName("NoBooking");
		for (int i=0; i<dailyLayout.size(); i++) {
			Booking temp= dailyLayout.get(i).returnReservedBooking(phone);
			if(temp.getName()!="NoBooking")
				specificBooking=temp;
		}
		return specificBooking;

	}

	
	/** Gets all the upcoming reservations for a particular day's AM or PM session
	 * 
	 * @param date in the format YYYYMMDD
	 * @param Morning or Afternoon session
	 * @return An ArrayList of all the upcoming reservations for the time period
	 */
	public ArrayList<Booking> checkUpcomingReservation(int date, boolean time) {  //Add this method into class
		ArrayList<Booking> temp=new ArrayList<Booking>();
		for (int i=0; i<dailyLayout.size(); i++) {
			if (dailyLayout.get(i).getDate()==date) {
				temp= dailyLayout.get(i).returnBooking(time);
			}
		}return temp;

	} 
	/** Gets the whole month's reservations and tables
	 * 
	 * @return The ArrayList of all the days' objects in the month
	 */
	public ArrayList<RestaurantLayoutDaily> getDailyLayout() {
		return dailyLayout;
	}
	
	/**Save bookings for the day into a txt file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveBookings() throws FileNotFoundException, IOException {
			ArrayList<RestaurantLayoutDaily> Month= dailyLayout;
			for(int j=0; j<Month.size(); j++) {
				ArrayList<Booking> everyDay=Month.get(j).returnDayBookings();
				for(int k=0; k<everyDay.size(); k++) 
					FileReaderWriter.saveBooking("Bookings.txt", everyDay.get(k));
			}

	}
	
}