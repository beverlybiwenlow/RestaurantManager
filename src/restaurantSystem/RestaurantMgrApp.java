package restaurantSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import Menu.Menu;
import Reservation.Booking;
import Reservation.ReservationCal;
import Reservation.Table;
import Staff.StaffManager;
import Transaction.Report;

import java.util.ArrayList;

/**
 * Main interface to access purposes of restaurant app
 * @author tinghaong
 *
 */
public class RestaurantMgrApp {
	/** Array of ReservationCal for 3 months*/
	protected static ReservationCal[] reserve=new ReservationCal[3];
	/**Integer to store yesterday's month, initialised as 13(infinity) and updated later
	 * used to check for a new month*/
	private static int yesterdaysMonth=13;
	/**Integer to store yesterday's day, initialised as 13(infinity) and updated later
	 * used to check for a new day*/
	private static int yesterdaysDay=32;
	/**Integer to store the current month in YYYYMM format*/
	private static int yearmonth;
	/**Counter for months*/
	private static int monthsCounter=0;
	/** Instantiates a Menu */
	private static Menu myMenu;
	/** Instantiates a StaffManager */
	private static StaffManager myStaffManager;
	/** Instantiates a Report */
	private static Report myReport;

/**
 * Main method to access all the applications purposes
 * @param args
 * @throws FileNotFoundException
 * @throws IOException
 */
public static void main(String[] args) throws FileNotFoundException, IOException {
	Scanner sc = new Scanner(System.in);
	
	myMenu = new Menu();
	myStaffManager = new StaffManager();
	myReport = new Report();
	myReport.initializingDatabase();
	int choice = 0;
	
	updateSystem();    //Always update system so proper stuff initialized
	loadSavedBookings(); //Load all the previous saved Bookings

	
	do {
		
		//Switch to choose which functions to access
		System.out.println("\n===== MCDONALD TRUMP =====");
		System.out.println("0: Update System");
		System.out.println("1. Staff");
		System.out.println("2. Menu");
		System.out.println("3. Reservation");
		System.out.println("4. Order");
		System.out.println("5. Report");
		System.out.println("6. Quit");
		System.out.println("What would you like to access?");
		
	choice = sc.nextInt();
	switch(choice) {
	case 0:
	 updateSystem();
	 break;
	case 1:
		accessStaff();
		break;
	case 2:
		accessMenu();
		break;
	case 3:
		accessReservation();
		break;
	case 4:
		accessOrder();
		break;
	case 5:
		accessReport();
		break;
	case 6:
		System.out.println("Terminating Restaurant de Derek Manager...");
		System.out.println("Thank you for using our Restaurant App");
		break;
	default:
		System.out.println("Please enter a valid input");
		break;
		}
	} while (choice !=6);
	sc.close();
}

/**
 * Method to interact with Report
 * @throws IOException
 */
@SuppressWarnings("resource")
private static void accessReport() throws IOException {
	int choice;
	Scanner sc = new Scanner(System.in);
	do{	
		//Displays every single item / price that the restaurant ever had
		System.out.println("\n1. View Historical Menu");
		//Saves Report into report.txt
		System.out.println("2. Print Report");
		//Returns to the functions menu
		System.out.println("3. Return to Main Menu");
		choice = sc.nextInt();
		
		switch(choice){
		case 1:
			myReport.viewHistoricalMenu();
			break;
		case 2:
			int periodStart, periodEnd;
			System.out.println("Identify starting point of report (how many days from today).");
			periodStart = sc.nextInt();
			System.out.println("Identify ending point of report (how many days from today).");
			periodEnd = sc.nextInt();
			if(periodStart >= periodEnd){
				//if the two periods are valid, calls printReport method from Report class
				myReport.printReport(periodStart, periodEnd);
			}
			else{
				//Prints out error message and restart accessReport class
				System.out.println("\nError: starting point is after ending point. Please retry.");
				accessReport();
			}
			break;
		case 3:
			System.out.println("Returning to main menu...");
			break;
		default:
			System.out.println("Please enter a valid input.");
			break;
		}
		
	}while(choice != 3);
}

/**
 * Method to interact with Orders
 * @throws IOException
 */
@SuppressWarnings("resource")
private static void accessOrder() throws IOException {
	int month = ReservationCal.getCurrentMonthIndex();
	boolean time;
	if(SystemFunctions.getCurrentTime()<=1500)
		time=true;
	else 
		time=false;
	
	int choice = 0;
	Table tempTable = null;
	Scanner sc = new Scanner(System.in);
	
		//Either create or new order or access a previous order
		System.out.println("\n1. Create Order");
		System.out.println("2. Current Order");
		System.out.println("Please select an option");
		choice = sc.nextInt();
		switch(choice) {
		case 1:
			//Creates order for arrival of customer with booking 
			int choice2 = 0;
			checkNewDay();
			System.out.println("\n1. Reservation");
			System.out.println("2. Walk in Customer");
			System.out.println("Please select an option");
			choice2  = sc.nextInt();
			//Creates order for arrival of customer with booking 
			//Or creates order for walk in customers
			
			switch(choice2) {
			case 1:
				System.out.println("Please enter your mobile number");
				int hpNo = sc.nextInt();
				//get customer table from reservation
				Booking bookingTurnedUp = reserve[month].checkReservation(hpNo);
				if(bookingTurnedUp.getName()=="NoBooking") {
					System.out.println("No Reservation for this phone number");
					return;
				}
				else {
					System.out.println("This reservation exists!");
					bookingTurnedUp.setPersonTurnedUp(true);
					//get table id and add Order to the table
					int tableId = bookingTurnedUp.getTableId();
					tempTable = reserve[month].getTable(bookingTurnedUp.getDate(), bookingTurnedUp.getTime(), bookingTurnedUp.getTableId(), true);
					tempTable.newOrder(myStaffManager.getCurrentName());
					System.out.println("your table is" + tempTable.getTableId());
				}
				break;
			case 2:
				System.out.println("How many seats? ");
				int seats=sc.nextInt();
				int type = 0;
				switch(seats) {
				case 1: case 2:
					type = 2;
					break;
				case 3: case 4:
					type = 4;
					break;
				case 5: case 6: case 7: case 8:
					type = 8;
					break;
				case 9: case 10:
					type = 10;
					break;
				default:
					System.out.println("Sorry, our restaurant do not have such tables");
					break;
					}

				boolean availability=reserve[month].checkTableAvailability(SystemFunctions.getCurrentDate(), time, type);
				//check availability and gets a table if restaurant is not full
				if (availability==true) {
					int tableId2 = reserve[month].addWalkIn(SystemFunctions.getCurrentDate(), time, type);
					tempTable = reserve[month].getTable(SystemFunctions.getCurrentDate(), time, tableId2, true);		
					System.out.println("Your table number is " + tableId2);
					tempTable.newOrder(tableId2 , myStaffManager.getCurrentName());
					}
					else 
						System.out.println("Restaurant is full"); 
					break;
				default:
					System.out.println("Error! Please enter a valid input");
					break;
				}
			break;
		case 2:
			System.out.println("What is your table number?");
			int tableNumber = sc.nextInt();
			tempTable = reserve[month].getTable(SystemFunctions.getCurrentDate(), time, tableNumber, false);
			if (tempTable.getOccupied() == false) {
				System.out.println("Table is not occupied!");
				return;
			}
			System.out.println("Table found!");
			break;
		default:
			System.out.println("Error! Please enter a valid input");
			break;
		}
		do {
		System.out.println("\n1. View Order");
		System.out.println("2. Update Order");
		System.out.println("3. Print Order Invoice");
		System.out.println("4. Return to Main Menu");
		System.out.println("Please select an option");
		choice = sc.nextInt();
		switch(choice) {
		case 1:
			//Calls viewOrder method for the table
			tempTable.viewOrder();
			break;
		case 2:
			//Calls viewMenu to access menu items and id
			//Calls updateOrder method to add / remove order
			myMenu.viewMenu();
			tempTable.updateOrder();
			break;
		case 3:
			//Calls printInvoice method from the table
			tempTable.printInvoice();
			reserve[month].removeOccupied(tempTable.getTableId(), SystemFunctions.getCurrentDate(), time);
			break;
		case 4:
			System.out.println("Returning to main menu...");
			break;
		default:
			System.out.println("Please enter a valid input");
			break;
		}
	} while (choice !=4);
}

/**
 * Method to interact with ReservationCal and bookings
 * @throws IOException
 */
@SuppressWarnings("resource")
private static void accessReservation() throws IOException {
	int choice = 0;
	Scanner sc = new Scanner(System.in);
	do {
		updateSystem();
		System.out.println("\n1. Create Reservation");
		System.out.println("2. Check Reservation");
		System.out.println("3. Remove Reservation");
		System.out.println("4. Check Restaurant Availability");
		System.out.println("5. Return to Main Menu");
		System.out.println("Please select an option");
		choice = sc.nextInt();
		
		//initialise some data
		int currentMonth= ReservationCal.getCurrentMonthIndex();
		int nextMonth=ReservationCal.getNextMonthIndex();
		switch(choice) {
		case 1:
			 
			System.out.println("Reservation for which date? YYYYMMDD format");
			int date=sc.nextInt();
			//*****Check if date is in correct format!!!
			int maxDate=SystemFunctions.getMaxDate();
			try {
			if(date>maxDate) 
				throw new DateOutOfBoundsException();
			//creates new bookings
			System.out.println("No of People: ");
			int people=sc.nextInt();
			System.out.println("Phone: ");
			int phone=sc.nextInt();
			System.out.println("Time: (in integer)");
			int time=sc.nextInt();
			System.out.println("Name: ");
			String name=sc.next();
			Booking booking=new Booking(name, people, phone, time, date);
			reserve[ReservationCal.isReservationThisMonth(booking)].addReservation(booking);
			}
			catch (DateOutOfBoundsException e) {
			System.out.println(e.getMessage());
			}
			break;
			
		case 2:
			System.out.println("Key in the phone number to check: ");
			int phone=sc.nextInt();
			Booking temp;
			
			//Checks for booking in current month
			temp = reserve[currentMonth].checkReservation(phone);
			
			//If there is no booking for current month, check booking for the next month
			if(temp.getName()=="NoBooking")
				temp = reserve[nextMonth].checkReservation(phone);
			
			if(temp.getName()=="NoBooking")
				System.out.println("No Reservation for this phone number");
			else {
				System.out.println("This reservation exists!");
				System.out.println("Reservation by :"+temp.getName());
				System.out.println("Date of Reservation(YYYYMMDD): "+temp.getDate());
				System.out.println("Time of Reservation: "+temp.getTimeExtended());
				System.out.println("Reservation for "+temp.getNoOfPeople()+ " people");
			}
			break;
		case 3:
			
			System.out.println("Key in phone number to remove reservation: ");
			phone=sc.nextInt();
			temp = reserve[currentMonth].checkReservation(phone);
			int month=currentMonth;
			
			//Checks for valid reservation
			if(temp.getName()=="NoBooking"){
				temp = reserve[nextMonth].checkReservation(phone);
				month=nextMonth;
			}
			if(temp.getName()=="NoBooking")
				System.out.println("No Reservation for this phone number");
			else {
				
				//Confirmation for removal or reservation
				System.out.println("Is this the reservation you want to remove? (Yes:1, No: 0)");
				System.out.println("Reservation by :"+temp.getName());
				System.out.println("Date of Reservation(YYYYMMDD): "+temp.getDate());
				System.out.println("Time of Reservation: "+temp.getTimeExtended());
				System.out.println("Reservation for "+temp.getNoOfPeople()+ " people");
			}
			int yes=sc.nextInt();
			if(yes==1){
				reserve[month].removeReservation(temp);
			}else {
				System.out.println("Bringing you back to the Reservation Menu");
			}
			break;
		case 4:
			System.out.println("Which day do you want to check? YYYYMMDD");
			date=sc.nextInt();
			maxDate=SystemFunctions.getMaxDate();
			try {
			if(date>maxDate) 
				throw new DateOutOfBoundsException();
			month=ReservationCal.isReservationThisMonth(date);
			
			boolean ampm;
			System.out.println("Key 1 for AM or 0 for PM");
			if(sc.nextInt()==1) 
				ampm=true;
			else 
				ampm=false;
			
			//Prints out list of free tables for a certain day and time
			System.out.println("Two Seater: " );
			reserve[month].checkFreeTables(date, 2, ampm); 
			System.out.println("Four Seater: " );
			reserve[month].checkFreeTables(date, 4, ampm); 
			System.out.println("Eight Seater: " );
			reserve[month].checkFreeTables(date, 8, ampm); 
			System.out.println("Ten Seater: " );
			reserve[month].checkFreeTables(date, 10, ampm); 
			}
			catch (DateOutOfBoundsException e) {
				System.out.println(e.getMessage());
			}
			break;
			
		case 5:
			//save bookings into txt files upon returning the the main function menu
			saveBookings();
			System.out.println("Returning to main menu...");
			break;
		default:
			System.out.println("Please enter a valid input");
			break;
		}
	} while (choice !=5);
}

/**
 * Method to interact with StaffManager
 * @throws IOException
 */
@SuppressWarnings("resource")
private static void accessStaff() throws IOException {
	int choice = 0;
	Scanner sc = new Scanner(System.in);
	do {
		System.out.println("\n1. Select current working Staff");
		System.out.println("2. View All Staff");
		System.out.println("3. Add Staff");
		System.out.println("4. Remove Staff");
		System.out.println("5. Return to Main Menu");
		System.out.println(myStaffManager.getCurrentName() + " is currently working");
		System.out.println("Please select an option");
		choice = sc.nextInt();
		switch(choice) {
		case 1:
			//Select current staff who is working
			myStaffManager.selectStaff();
			System.out.println(myStaffManager.getCurrentName() + " is now working\n");
			break;
		case 2:
			//Views list of staff who is working in the restaurant using the staffManager
			myStaffManager.viewStaff();
			break;
		case 3:
			//Calls method to add a new staff into the restaurant
			myStaffManager.addStaff();
			break;
		case 4:
			//Calls method to remove a staff from the restaurant
			myStaffManager.deleteStaff();
			break;
		case 5:
			//Saves list of staff of the restaurant into staffList.txt
			System.out.println("Returning to main menu...");
			myStaffManager.saveStaffList();
			break;
		default:
			System.out.println("Please enter a valid input");
			break;
		}
	} while (choice!=5);

}

/**
 * Method to interact with Menu
 * @throws FileNotFoundException
 * @throws IOException
 */
@SuppressWarnings("resource")
private static void accessMenu() throws FileNotFoundException, IOException {
	int choice = 0;
	Scanner sc = new Scanner(System.in);
	do {
		System.out.println("1. view menu");
		System.out.println("2. add menu item");
		System.out.println("3. delete menu item");
		System.out.println("4. update menu item");
		System.out.println("5. return to main menu");
		System.out.println("Please make your choice");
		choice = sc.nextInt(); 
		switch(choice) {
		case 1:
			myMenu.viewMenu();
			break;
		case 2:
			myMenu.addItem();
			break;
		case 3:
			myMenu.deleteItem();
			break;
		case 4:
			myMenu.updateItem();
			break;
		case 5:
			//Saves the update into menuItem, promoItem and lastpos txt files
			System.out.println("returning to main menu...");
			Menu.saveItem();
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}
	} while (choice !=5);

}

/**
 * Method to refresh the system to check expiring bookings, new months and days
 * @throws IOException
 */
private static void updateSystem() throws IOException{
	//background check for expired reservations and new month

		yearmonth=SystemFunctions.getCurrentDate()/100;
		int currentMonth= ReservationCal.getCurrentMonthIndex();

		if(yesterdaysMonth==13){ //i.e. infinity, means system just started up
			//START UP FUNCTIONS BELOW
			System.out.println("System starting up, please hold...");
			ReservationCal.setMonthIndexes(0, 2, 1);
			
			//initialise calendar
			yesterdaysMonth=(SystemFunctions.getCurrentDate()/100)%100;
			yesterdaysDay=SystemFunctions.getCurrentDate()%100;
			for(monthsCounter=0; monthsCounter<3; monthsCounter++) {
				if ((yearmonth%100)<13) {
					reserve[monthsCounter]=new ReservationCal(yearmonth);
				}
				else  { //This is to make sure month does not exceed 12
					int month=(yearmonth%100)-12; //Gets the correct month
					yearmonth=(yearmonth/100) +1; //Get year
					yearmonth=(yearmonth*100) +month; //Get YYYYMM format
					reserve[monthsCounter]=new ReservationCal(yearmonth);				
				} 
				yearmonth++;
			}
			System.out.println("Reservation Cal for next 3 months initialised\n");
		}
		
		if (yesterdaysMonth!=(SystemFunctions.getCurrentDate()/100)%100){//i.e. new month!
			//NEW MONTH FUNCTIONS BELOW
			System.out.println("System updating, please hold...");
			//update month indexes
			switch(currentMonth){
			case 0:
				ReservationCal.setMonthIndexes(1, 0, 2);
				break;
			case 1:
				ReservationCal.setMonthIndexes(2, 1, 0);
				break;
			case 2:
				ReservationCal.setMonthIndexes(0, 2, 1);
				break;
			}
			
			yearmonth=(SystemFunctions.getCurrentDate()/100);
			yearmonth=yearmonth+2; //adds at the last array;
			if ((yearmonth%100)>12) { //gets month
				int month=(yearmonth%100)-12;
				yearmonth=(yearmonth/100) +1;
				yearmonth=(yearmonth*100) +month;
			}
//			monthsCounter+=2;    //Might not work!!!!******************************************************************
//			if (monthsCounter>2) 
//				monthsCounter=0;
			reserve[ReservationCal.getLastMonthIndex()]=new ReservationCal(yearmonth);
			System.out.println("Old month deleted. New month added!\n");
		}
		yesterdaysMonth=(SystemFunctions.getCurrentDate()/100)%100;
		
		
		//RESERVATION EXPIRY CHECK
		currentMonth=ReservationCal.getCurrentMonthIndex();
		int currentDate = SystemFunctions.getCurrentDate();
		int currentTime = SystemFunctions.getCurrentTime();
		boolean currentAmPm= Booking.isItAm(currentTime);
		//get upcoming reservations
		ArrayList<Booking> upcomingBookings = reserve[currentMonth]
				.checkUpcomingReservation(currentDate, currentAmPm);
		int noOfUpcomingBookings = upcomingBookings.size();
		//check reservation expiry for each upcoming reservation
		for (int i=0; i<noOfUpcomingBookings; i++){
			Booking ithBooking=upcomingBookings.get(i);//get table, check if occupied
			int tableID=ithBooking.getTableId();
			Table ithTable = reserve[currentMonth].getTable(currentDate, currentAmPm, tableID, false);
			//ithBooking.getTableId();
			if(ithBooking.reservationExpireCheck() && !ithTable.getOccupied()){
				reserve[currentMonth].removeReservation(upcomingBookings.get(i));
			};
		}
		
	}



/**
 * Method to save bookings made into txt
 * @throws FileNotFoundException
 * @throws IOException
 */
public static void saveBookings() throws FileNotFoundException, IOException {
	FileReaderWriter.createFile("Bookings.txt");
	for (int i = 0; i < 3; i++) {
		reserve[i].saveBookings();
	}
}

/**
 * Method to load bookings from txt files
 * @throws FileNotFoundException
 * @throws IOException
 */
public static void loadSavedBookings() throws FileNotFoundException, IOException {
	// loads presaved files
	ArrayList<Booking> allSavedBookings;
	allSavedBookings = FileReaderWriter.readBookingFile("Bookings.txt");
	for(int i=0; i<allSavedBookings.size(); i++) {
		reserve[ReservationCal.isReservationThisMonth(allSavedBookings.get(i))].addReservation(allSavedBookings.get(i));
	}
}

/**
 * Checks if it a new day
 */
public static void checkNewDay(){
	int currentDate = SystemFunctions.getCurrentDate();
	if(currentDate != Report.getLastDate()){
		Report.addNewDay();
		Report.setLastDate(currentDate);
	}
}

}

