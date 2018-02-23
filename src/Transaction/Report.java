package Transaction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import Menu.MenuItem;
import Menu.PromoItem;
import Reservation.Order;
import restaurantSystem.FileReaderWriter;

public class Report {
	

	/**
	 * All the orders made for a particular day
	 */
	private static ArrayList<Order> todayOrderList;
	
	/**
	 * ArrayList where each element is a todayOrderList
	 * Database of every order made for every day
	 */
	private static ArrayList<ArrayList<Order>> orderHistory;
	
	/**
	 * Historical menu containing every MenuItem the restaurant has ever had
	 */
	private static ArrayList<MenuItem> histMenuItems;
	
	/**
	 * Historical menu containing every PromoItem the restaurant has ever had
	 */
	private static ArrayList<PromoItem> histPromoItems;
	
	/**
	 * A breakdown of the sales of each item and total sales of all the items over the time period specified
	 */
	private String salesBreakdown;
	
	/**
	 * Positions of the last main, drink and dessert item in histMenuItems
	 */
	private static int[] lastpos;
	
	/**
	 * The latest date in the database of orders
	 * ie. the date of the last element of orderHistory
	 * Initialized as zero, but will be replaced when each day passes
	 */
	private static int lastDate = 0;
	
	
	
	
	/**
	 * Creates a Report instance each time user asks to print report
	 * @throws IOException
	 */
	public Report() throws IOException{
		histMenuItems = new ArrayList<MenuItem>();
		histPromoItems = new ArrayList<PromoItem>();
		loadMenuItem();
		resetReport();
	}
	
	/**
	 * Resets the sales attribute of each MenuItem in histMenuItem and PromoItem in histPromoItems to zero before calculating the sales for the report
	 */
	private void resetReport(){
		for (int i = 0; i < histMenuItems.size(); i++){
			histMenuItems.get(i).setSales(0);
		}
		
		for (int i = 0; i < histPromoItems.size(); i++){
			histPromoItems.get(i).setSales(0);
		}
	}
	
	/**
	 * Called every time an invoice is printed ie. every time an order is confirmed
	 * Updates the database by adding a new order to today's todayOrderList
	 * @param myOrder The order to be added to the database
	 * @throws IOException
	 */
	public static void updateOrderHistory(Order myOrder) throws IOException{
		//this method takes in each order and adds it to the database orderHistory
		Order tempOrder = new Order();
		tempOrder = myOrder;
		orderHistory.get(orderHistory.size() - 1).add(tempOrder);
		
		System.out.println("\nDatabase updated.");
		System.out.println("Day no.: " + orderHistory.size());
		System.out.println("Order no.: " + orderHistory.get(orderHistory.size() - 1).size());
		
	}
	
	/**
	 * Adds a new todayOrderList to the database when a new day comes
	 */
	public static void addNewDay(){
		orderHistory.add(new ArrayList<Order>());
		System.out.println("No of days: " + orderHistory.size());
	}
	
	
	
	
	/**
	 * For each order in each day specified, sales of each MenuItem and PromoItem over the time period are calculated
	 * This value is then stored as an attribute of the corresponding MenuItems and PromoItems in histMenuItems and histPromoItems
	 * @param periodStart inputed by the user. Represents the number of days before today that the report should start from
	 * @param periodEnd inputed by the user. Represents the number of days before today that the report should end at
	 */
	public void calculateTotalSales(int periodStart, int periodEnd){
		resetReport();
		int n = orderHistory.size() - periodStart - 1;
		
		for(int k = 0; k < (periodStart - periodEnd) + 1; k++){ 
			//for each todayOrderList (each day)
			ArrayList<Order> tempDay = orderHistory.get(n);
			System.out.println("Day no. " + n);
			/*for (int i = 0; i<tempDay.size(); i++) {
				tempDay.get(i).();
			}*/
			 for(int j = 0; j < tempDay.size(); j++){ 
				 // for each Order in todayOrderList
				 Order tempOrder = tempDay.get(j);
				 
				 for (int i = 0; i < tempOrder.getMenuArray().size(); i++){ 
					 //for each MenuItem in Order
					 MenuItem tempMenuItem = tempOrder.getMenuArray().get(i);
					 double menuItemOrderSales = tempMenuItem.getQuantityPrice();
					 String menuItemName = tempMenuItem.getName();
					 double menuItemPrice = tempMenuItem.getPrice();
					 for (int m = 0; m < histMenuItems.size(); m++) {
						 MenuItem tempHistMenuItem = histMenuItems.get(m);
						 if (menuItemName.compareTo(tempHistMenuItem.getName()) == 0 && menuItemPrice == tempHistMenuItem.getPrice()){
							 tempHistMenuItem.setSales(tempHistMenuItem.getSales() + menuItemOrderSales);
						 }
					 }
				 }
				 
				 for (int i = 0; i < orderHistory.get(n).get(j).getPromoArray().size(); i++){ 
					 //for each PromoItem in Order
					 PromoItem tempPromoItem = tempOrder.getPromoArray().get(i);
					 double promoItemOrderSales = tempPromoItem.getQuantityPrice();
					 String promoItemName = tempPromoItem.getName();
					 double promoItemPrice = tempPromoItem.getPrice();
					 for (int m = 0; m < histPromoItems.size(); m++) {
						 PromoItem tempHistPromoItem = histPromoItems.get(m);
						 if (promoItemName.compareTo(histPromoItems.get(m).getName()) == 0 && promoItemPrice == tempHistPromoItem.getPrice()){
							 tempHistPromoItem.setSales(tempHistPromoItem.getSales() + promoItemOrderSales);
						 }
					 }
				 }
			 }
			 n++;
		}
	}
	
	/**
	 * Accesses the sales attribute of each MenuItem in histMenuItems and PromoItem in histPromoItems and adds them all together
	 * @return the total sales the restaurant earned over the entire period
	 */
	public double getTotalRevenue(){
		 double totalRevenue = 0.0;
		 for(int i = 0; i < histMenuItems.size(); i++){
			 totalRevenue += histMenuItems.get(i).getSales();
		 }
		 for(int i = 0; i < histPromoItems.size(); i++){
			 totalRevenue += histPromoItems.get(i).getSales();
		 }
		 return totalRevenue;
	 }
	
	/**
	 * Adds each individual MenuItem and PromoItem's sales and the total revenue to the string salesBreakdown
	 * @param periodStart inputed by the user. Represents the number of days before today that the report should start from
	 * @param periodEnd inputed by the user. Represents the number of days before today that the report should end at
	 * @return the string which contains the sales of the individual MenuItems and PromoItems and the total sales revenue
	 */
	public String getSalesBreakdown(int periodStart, int periodEnd){
		 //report will detail the period, individual sale items (either menu items or promotional items) and total revenue.
		 calculateTotalSales(periodStart, periodEnd);
		 salesBreakdown = "Time period of report: From " + periodStart + " days ago to " + periodEnd + " days ago.";
		 for(int i = 0; i < histMenuItems.size(); i++){
			 MenuItem tempHistMenuItem = histMenuItems.get(i);
			 if(tempHistMenuItem.getSales() != 0){
				 salesBreakdown += ("\n\nItem name: " + tempHistMenuItem.getName() 
						 		+ "\nUnit price: $" + tempHistMenuItem.getPrice() 
						 		+ "\nTotal sales: $" + tempHistMenuItem.getSales());
			 }
		 }
		 		 
		 for(int i = 0; i < histPromoItems.size(); i++){
			 PromoItem tempHistPromoItem = histPromoItems.get(i);
			 if(tempHistPromoItem.getSales() != 0){
				 salesBreakdown += ("\n\nPromo name: " + tempHistPromoItem.getName() 
						 		+ "\nUnit price: $" + tempHistPromoItem.getPrice() 
						 		+ "\nTotal sales: $" + tempHistPromoItem.getSales());
			 }
		 }
		 salesBreakdown += ("\n\nTotal revenue of all products during time period: $" + getTotalRevenue());

		 return salesBreakdown;
	 }
	
	/**
	 * Calls on FileReaderWriter to print the report onto a .txt file
	 * @param periodStart inputed by the user. Represents the number of days before today that the report should start from
	 * @param periodEnd inputed by the user. Represents the number of days before today that the report should end at
	 * @throws IOException When writing to the file fails
	 */
	public void printReport(int periodStart, int periodEnd) throws IOException{		
		FileReaderWriter.printReport(getSalesBreakdown(periodStart, periodEnd));
		System.out.println("Check report.txt for full report.");
	}
	
	
	
	
	/**
	 * Prints out the historical menu using histMenuItems and histPromoItems
	 */
	public void viewHistoricalMenu() {
		System.out.println("=====List of Menu Items=====");
		//initialise i to search through arraylist
		int i = 0;
		System.out.println("\n=====Main Courses=====");
		
		//Prints out the main courses in the list
		while (i <= lastpos[0]) {
			MenuItem temp = histMenuItems.get(i);
			System.out.println(i+1 + " : " + temp.getName() + " : $" + temp.getPrice());
			System.out.println(temp.getDescription());
			i++;
		}
		
		//Prints out the drinks in the list
		System.out.println("\n=====Drinks=====");
		while (i <= lastpos[1]) {
			MenuItem temp = histMenuItems.get(i);
			System.out.println(i+1 + " : " + temp.getName() + " : $" + temp.getPrice());
			System.out.println(temp.getDescription());
			i++;
		}
		
		//Prints out the desserts in the list
		System.out.println("\n=====Desserts=====");
		while (i <= lastpos[2]) {
			MenuItem temp = histMenuItems.get(i);
			System.out.println(i+1 + " : " + temp.getName() + " : $" + temp.getPrice());
			System.out.println(temp.getDescription());
			i++;
		}
		System.out.println("");
		
		//Prints out the promotional items in the list
		System.out.println("=====List of Promotional Items=====");
		for (int j = 0; j < histPromoItems.size(); j++) {
			PromoItem temp2 = histPromoItems.get(j);
			System.out.println(j+1 + " : " +temp2.getName() + " : $" + temp2.getPrice());
			temp2.printPromoItems();
			System.out.println("");
		} 
	}
	
	/**
	 * Adds a PromoItem to histPromoItems when a new PromoItem is added to Menu
	 * @param item The PromoItem to be added
	 * @throws FileNotFoundException The exception where the file is not present
	 * @throws IOException When reading from the file fails
	 */
	
	public static void addPromoItem(PromoItem item) throws FileNotFoundException, IOException {
		histPromoItems.add(item);
		saveItem();
	}
	
	/**
	 * Adds a MenuItem to histMenuItems when a new MenuItem is added to Menu
	 * @param item The MenuItem to be added
	 * @throws FileNotFoundException The exception where the file is not present
	 * @throws IOException When reading from the file fails
	 */
	
	public static void addMenuItem(int choice, MenuItem item) throws FileNotFoundException, IOException {
		histMenuItems.add(lastpos[choice-1]+1, item);
		for (int j = choice-1; j < lastpos.length; j++) {
			// increase the last position of the array behind
			lastpos[j]++;
		}
		saveItem();
	}
	
	/**
	 * Loads .txt files and saves them into the historical menus
	 * @throws FileNotFoundException The exception where the file is not present
	 * @throws IOException When writing to the file fails
	 */
	public static void loadMenuItem() throws FileNotFoundException, IOException {
		// loads presaved files
		histMenuItems = FileReaderWriter.readMenuItemFile("histMenuItem.txt");
		lastpos = FileReaderWriter.readLastPos("histlastpos.txt");
		histPromoItems = FileReaderWriter.readPromoItemFile("histPromoItem.txt");
	}
	
	/**
	 * Saves new added item into the .txt files
	 * @throws FileNotFoundException The exception where the file is not present
	 * @throws IOException When writing to the file fails
	 */
	public static void saveItem() throws FileNotFoundException, IOException {
		//saves current data onto a txt file
		FileReaderWriter.createFile("histMenuItem.txt");
		for (int i = 0; i < histMenuItems.size(); i++) {
			FileReaderWriter.addMenuItem("histMenuItem.txt", histMenuItems.get(i));
		}
		FileReaderWriter.createFile("histPromoItem.txt");
		for (int j = 0; j < histPromoItems.size(); j++) {
			FileReaderWriter.addPromoItem("histPromoItem.txt", histPromoItems.get(j));
		}
		FileReaderWriter.createFile("histlastpos.txt");
		FileReaderWriter.saveLastPos("histlastpos.txt", lastpos);
		
	}
	
	
	/**
	 * Creating the database when the application first starts up
	 */
	public void initializingDatabase(){
		todayOrderList = new ArrayList<Order>();
		orderHistory = new ArrayList<ArrayList<Order>>();
	}
	
	
	/**
	 * Gets the date of the last day in the database
	 * @return date of the last day in the database
	 */
	public static int getLastDate(){
		return lastDate;
	}
	
	/**
	 * After new day comes, last date is updated to be the date of the new day
	 * @param n last date
	 */
	public static void setLastDate(int n){
		lastDate = n;
	}
	
	
}