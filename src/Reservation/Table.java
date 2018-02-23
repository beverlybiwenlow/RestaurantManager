package Reservation;
import java.io.IOException;
import java.util.Scanner;

import Transaction.Report;

public class Table {
	/** Table's booking status */
	private boolean booked;
	/** Number of seats in table */
	private int seats;
	/** ID of table in restaurant */
	private int tableId;
	/** Order which is created by and belongs to this table */
	private Order myOrder;
	/** Whether table is in the AM shift or PM shift */
	private boolean time;//session AM or PM
	/** Whether table is occupied */
	private boolean occupied;
	
	/** Creates a table
	 * 
	 * @throws IOException
	 */
	public Table() throws IOException{
		booked = false;
		occupied = false;
		seats = 0;
		tableId = 0;
	}
	
	/** Overloading of original table constructor if more details are added
	 * 
	 * @param seats number of seats of table
	 * @param table_id ID of table in restaurant
	 * @throws IOException
	 */
	public Table(int seats, int table_id) throws IOException{
		setTableId(table_id);
		booked = false;
		occupied = false;
		this.seats = seats;
		myOrder = new Order();
	}
	
	/** Gets the table ID
	 * 
	 * @return ID of table in restaurant
	 */
	public int getTableId() {
		return tableId;
	}
	
	/** Checks whether a table is occupied
	 * 
	 * @return table's current occupancy status
	 */
	public boolean getOccupied() {
		return occupied;
	}
	/** Sets whether a table is occupied
	 * 
	 * @param occupied table's current occupancy status
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	/**Checks whether table is an AM or PM table
	 * 
	 * @return shift which table is operating
	 */
	public boolean getTime() { 
		return time;
	}
	
	/**Checks whether table is booked
	 * 
	 * @return table's booking status
	 */
	public boolean getBooked() {
		return booked;
	}
	/** Sets whether a table is booked
	 * 
	 * @param booking table's booking status
	 */
	public void setBooked(boolean booking) {
		booked=booking;
	}
	
	/** Sets table ID of table in restaurant
	 * 
	 * @param table_id ID of table in restaurant
	 */
	public void setTableId(int table_id) {
		tableId=table_id;
	}
	
	/** Sets number of seats in this table
	 * 
	 * @param table_type number of seats in table
	 */
	public void setSeats(int table_type) {
		seats=table_type;
	}
	
	/** Sets whether time is an AM or PM shift table
	 * 
	 * @param table_time table is AM or PM shift
	 */
	public void setTime(boolean table_time) {
		time=table_time;
	}
	
	/** Views the order of the table
	 * 
	 */
	public void viewOrder() {
		myOrder.viewOrder();
	}
	
	@SuppressWarnings("resource")
	/**Provides an option to add either menu items or promotional items
	 * 
	 */
	public void updateOrder() {
		//Calls private methods updatePromoItem and updateMenu Item
		System.out.println("1. Update Menu Item");
		System.out.println("2. Update Promo Item");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			updateMenuItem();
			break;
		case 2:
			updatePromoItem();
			break;
		}
	}
	
	@SuppressWarnings("resource")
	/** Gives the table the option to add promotional items or remove promotional items in the table's order
	 * 
	 */
	private void updatePromoItem() {
		System.out.println("1. Add Promo Item");
		System.out.println("2. Remove Promo Item");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			System.out.println("Select Promo Id");
			int k = sc.nextInt() -1;
			System.out.println("Please enter desired quantity");
			int quantity = sc.nextInt();
			myOrder.addPromoOrder(k, quantity);
			break;
		case 2:
			System.out.println("Select Promo Id");
			int j = sc.nextInt() -1;
			myOrder.deletePromoOrder(j);
			break;
		}
	}
	
	@SuppressWarnings("resource")
	/**Gives the table the option to add promotional items or remove normal menu items in the table's order
	 * 
	 */
	private void updateMenuItem() {
		System.out.println("1. Add Menu Item");
		System.out.println("2. Remove Menu Item");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			System.out.println("Select Menu Id");
			int k = sc.nextInt() -1;
			System.out.println("Please enter desired quantity");
			int quantity = sc.nextInt();
			myOrder.addMenuOrder(k, quantity);
			break;
		case 2:
			System.out.println("Select Menu Id");
			int j = sc.nextInt() -1;
			myOrder.deleteMenuOrder(j);
			break;
		default:
			System.out.println("Please enter a valid input");
			break;
		}
	}
	
	/** Get the number of seats in the table
	 * 
	 * @return number of seats in the table
	 */
	public int getSeats() {
		return seats;
	}
	
	/** creation of a new order for the table
	 * 
	 * @param table_id ID of table in restaurant
	 * @param staff name of staff taking the order
	 * @throws IOException
	 */
	public void newOrder(int table_id, String staff_name) throws IOException { 
		//initialize Order object
		myOrder = new Order();
		myOrder.setOrderId(table_id);
		myOrder.setStaffName(staff_name);
		System.out.println("New Order taken");
	}
	
	/**Creation of a new order for the table
	 * Overloading of the other newOrder() method
	 * @param staff name of staff taking orders
	 * @throws IOException
	 */
	public void newOrder(String staff_name) throws IOException { 
		//initialize Order object
		myOrder = new Order();
		myOrder.setStaffName(staff_name);
		myOrder.setOrderId(tableId);
		System.out.println("New Order taken");
	}

	/* Prints the invoice and sets table to vacant and unbooked
	 * 
	 */
	public void printInvoice() throws IOException {
		//prints invoice and clear the table's bookings and occupancy
		myOrder.printInvoice();
		this.setOccupied(false);
		this.setBooked(false);
		System.out.println("Table is now available");
		Report.updateOrderHistory(myOrder);
	}
}