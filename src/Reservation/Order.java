package Reservation;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Menu.Menu;
import Menu.MenuItem;
import Menu.PromoItem;

import java.text.DecimalFormat;


public class Order {
	/** ID of order which is the same as the ID of the related table in the restaurant*/
	private int orderId;
	/** Array list of menu items already ordered */
	private ArrayList<MenuItem> menuArray;
	/** Array list of promotional items already ordered */
	private ArrayList<PromoItem> promoArray;
	/** Menu which belongs to this order 
	 *  ie. menu that was used by the restaurant as of the time order is created */
	private Menu myMenu;
	/** Name of staff creating the order for the table */
	private String staffName;
	/** Time order was created */
	private static String timeStamp;

	/** Order constructor which creates a menu with an arraylist of MenuItems and an arraylist of PromoItems
	 * 
	 * @throws IOException
	 */
	public Order() throws IOException{
		myMenu = new Menu();
		menuArray = new ArrayList<MenuItem>();
		promoArray = new ArrayList<PromoItem>();
		orderId= 0;
		timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	/**Gets the ID of this order
	 * 
	 * @return ID of order
	 */
	public int getOrderId() {
		return orderId;
	}
	
	/** Calculates the total price of all items in this order
	 * 
	 * @return total price of all items in this order
	 */
	public double getTotalPrice() {
		double price = 0;
		for (int i = 0; i < menuArray.size(); i++) {
			price += menuArray.get(i).getQuantityPrice();
		}
		for (int j = 0; j < promoArray.size(); j++) {
			price += promoArray.get(j).getQuantityPrice();
		}
		return price;
	}
	
	/** Prints out the contents of this order
	 * 
	 */
	public void viewOrder() {
		//Traverse through both menu and promotional item array and print out the contents
		System.out.println("\nMenu items ordered: ");
		for (int i = 0; i < menuArray.size(); i++) {
			System.out.println(i + 1 + " : " + menuArray.get(i).getName() + " : Quantity " + menuArray.get(i).getQuantity());
		}
		System.out.println("\nPromotional items ordered: ");
		for (int j = 0; j < promoArray.size(); j++) {
			System.out.println(j + 1 + " : " + promoArray.get(j).getName() + " : Quantity " + promoArray.get(j).getQuantity());
			}
		System.out.println("Subtotal Price: " + getTotalPrice());
	}
	
	/** Adds MenuItem object into the Order
	 * 
	 * @param menu_id index of menuItem in the menu
	 * @param quantity number of food ordered
	 */
	public void addMenuOrder(int menu_id, int quantity) {
		//Add menuItem to the menuItem ArrayList
		MenuItem temp = myMenu.getMenuItem(menu_id);
		for (int i = 0; i < menuArray.size(); i++) {
			if (temp.getName().equals(menuArray.get(i).getName())) {
				menuArray.get(i).addQuantity(quantity);
				return;
			}
		}
		temp.addQuantity(quantity);
		menuArray.add(temp);
	}
	
	/**Adds PromoItem object into the Order
	 * 
	 * @param promo_id index of promoItem in the menu
	 * @param quantity number of promotional food bundles ordered
	 */
	public void addPromoOrder(int promo_id, int quantity) {
		//Add promoItem to the menuItem ArrayList
		PromoItem temp = myMenu.getPromoItem(promo_id);
		for (int i = 0; i < promoArray.size(); i++) {
			if (temp.getName().equals(promoArray.get(i).getName())) {
				promoArray.get(i).addQuantity(quantity);
				return;
			}
		}
		temp.addQuantity(quantity);
		promoArray.add(temp);
	}

	/** removing an order of a promotional food bundle which has been ordered
	 * 
	 * @param promo_index index of promotional bundle in menu
	 */
	public void deletePromoOrder(int promo_index) {
		PromoItem temp = myMenu.getPromoItem(promo_index);
		for (int i = 0; i < promoArray.size(); i++) {
			if (temp.getName().equals(promoArray.get(i).getName())) {
				promoArray.remove(i);
				return;
			}
		}
		System.out.println("Promotional item is not found!");
	}
	
	/** removing an order of a normal menu item which has been ordered
	 * 
	 * @param menu_index index of normal menu item in menu
	 */
	public void deleteMenuOrder(int menu_index) {
		MenuItem temp = myMenu.getMenuItem(menu_index);
		for (int j = 0; j < menuArray.size(); j++) {
			if (temp.getName().equals(menuArray.get(j).getName())) {
				menuArray.remove(j);
				return;
			}
		}
		System.out.println("Menu item is not found!");
	}
	
	/** prints invoice
	 * 
	 * @throws IOException
	 */
	public void printInvoice() throws IOException {
		DecimalFormat twoDecimals = new DecimalFormat ("#.00");
		String gstPrice = twoDecimals.format(getTotalPrice()*1.177);
		System.out.println("         MCDONALD TRUMP   ");
		System.out.println("    WHITE HOUSE, WASHINGTON D.C.");
		System.out.println("     Time/Date: " + timeStamp);
		System.out.println("     Served by: " + staffName);
		System.out.println("**************************************");
		System.out.println("    Table = " + orderId);
		System.out.println("--------------------------------------");
		viewOrder();
		System.out.println("--------------------------------------");
		System.out.println("Price before GST = " + getTotalPrice());
		System.out.println("--------------------------------------");
		System.out.println("Nett Price = " + gstPrice);
		//to store the order in the database for report to use
	}


	public void setOrderId(int table_id) {
		orderId = table_id;		
	}
	
	public void setStaffName(String name) {
		staffName = name;
	}
	
	public String getStaffName() {
		return staffName;
	}
	
	public ArrayList<MenuItem> getMenuArray(){
		return menuArray;
	}
	
	public ArrayList<PromoItem> getPromoArray(){
		return promoArray;
	}
}