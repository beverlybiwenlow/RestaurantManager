package Menu;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Transaction.Report;
import restaurantSystem.FileReaderWriter;
/**
 * Stores food item into the Menu
 * @author tinghaong
 *
 */
public class Menu {
	/** Integer index which stores the last position of ala carte item
	 *  Index 0 for main course
	 *  Index 1 for drinks
	 *  Index 2 for dessert
	 */
	private static int[] lastpos;
	/** ArrayList of MenuItems that is sold by the restaurant */
	private static ArrayList<MenuItem> items;
	/** ArrayList of Promotional items that is sold by the restaurant */
	private static ArrayList<PromoItem> promoItems;
	/** Instantiates a report to save a historical record of items sold in restaurant*/
	private Report myReport;
	
	/**Initialise the ArrayLists in Menu, and intArray to store last position of different types 
	 * of Menu items, loads the list of items stored in text files
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Menu() throws FileNotFoundException, IOException {
		items = new ArrayList<MenuItem>();
		promoItems = new ArrayList<PromoItem>();
		
		lastpos = new int[3];
		for (int i = 0; i < lastpos.length; i++) {
			lastpos[i] = -1;
		}
		loadMenuItem();
		myReport = new Report();
	}
	
	/**Returns a menu item stored in the ArrayList
	 * 
	 * @param index of MenuItem to return
	 * @return Menu Item
	 */
	public MenuItem getMenuItem(int k) {
		return items.get(k);
	}
	
	/**Returns a promotional item stored in the ArrayList
	 * 
	 * @param index of PromoItem to return
	 * @return PromoItem
	 */
	public PromoItem getPromoItem(int k) {
		return promoItems.get(k);
	}
	
	/**Prints of the Arraylist of both MenuItems arranged by Menu Item type and PromoItems 
	 */
	public void viewMenu() {
		System.out.println("=====List of Menu Items=====");
		int i = 0;
		System.out.println("\n=====Main Courses=====");
		
		//Prints out the main courses in the list
		while (i <= lastpos[0]) {
			MenuItem temp = items.get(i);
			System.out.println(i+1 + " : " + temp.getName() + " : $" + temp.getPrice());
			System.out.println(temp.getDescription());
			i++;
		}
		
		//Prints out the drinks in the list
		System.out.println("\n=====Drinks=====");
		while (i <= lastpos[1]) {
			MenuItem temp = items.get(i);
			System.out.println(i+1 + " : " + temp.getName() + " : $" + temp.getPrice());
			System.out.println(temp.getDescription());
			i++;
		}
		
		//Prints out the desserts in the list
		System.out.println("\n=====Desserts=====");
		while (i <= lastpos[2]) {
			MenuItem temp = items.get(i);
			System.out.println(i+1 + " : " + temp.getName() + " : $" + temp.getPrice());
			System.out.println(temp.getDescription());
			i++;
		}
		System.out.println("");
		
		//Prints out the promotional items in the list
		System.out.println("=====List of Promotional Items=====");
		for (int j = 0; j < promoItems.size(); j++) {
			PromoItem temp2 = promoItems.get(j);
			System.out.println(j+1 + " : " +temp2.getName() + " : $" + temp2.getPrice());
			temp2.printPromoItems();
			System.out.println("");
		} 
	}
	
	/**Deletes either a PromoItem or MenuItem from the ArrayList
	 */
	public void deleteItem() {
		Scanner sc = new Scanner(System.in).useDelimiter("\\n");
		System.out.println("Please select type of item to delete:");
		System.out.println("1. Promotional Item");
		System.out.println("2. Menu Item");
		int choice = sc.nextInt();
		boolean found = false;
		//make a switch for promotional item and menu item
		switch(choice) {
		case 1:
			System.out.println("Please enter promotional item id to delete");
			int id = sc.nextInt();
			if (id <= promoItems.size()) {
			System.out.println(promoItems.get(id-1).getName() + " is removed!");
			promoItems.remove(id-1);
			found = true;
			return;
			}
		case 2:
			System.out.println("Please enter menu item id to delete");
			int menuId = sc.nextInt();
			if (menuId <= items.size()) {
			System.out.println(items.get(menuId-1).getName() + " is removed!");
			items.remove(menuId-1);
			for (int j = 0; j < 3; j++) {
				if (menuId-1 <= lastpos[j]) {
					lastpos[j]--;
				}
			}
			found = true;
			return;
			}
		default:
			System.out.println("Error! Please enter a valid input");
			break;
		}
		
		//if item is not found in either cases, error message will be shown
		if (!found) {
			System.out.println("invalid id entered!");
		}
		}
	
	/**Update an existing PromoItem or MenuItem that is in the ArrayList
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void updateItem() throws FileNotFoundException, IOException {
		//calls two seperate private methods for menu and promo item
		Scanner sc = new Scanner(System.in);
		System.out.println("Please select: ");
		System.out.println("1. Promotional Item");
		System.out.println("2. Menu Item");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			updatePromoItem();
			break;
		case 2:
			updateMenuItem();
			break;
		default:
			System.out.println("Error! Please enter a valid input");
			break;
		}
	}
	
	/**Method called by updateItem to update a PromoItem in the ArrayList
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void updatePromoItem() throws FileNotFoundException, IOException {
		Scanner sc = new Scanner(System.in).useDelimiter("\\n");
		System.out.println("Please enter promotional item id to update");
		int promoItem = sc.nextInt();
		boolean found = false;
		//initialise some data
		
		if (promoItem <= promoItems.size()) {
		found = true;
		System.out.println("Please select an option");
		System.out.println("1. change price");
		System.out.println("2. add items");
		System.out.println("3. remove items");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			//changes price
			System.out.println("Please enter a new price");
			promoItems.get(promoItem-1).setPrice(sc.nextDouble());
			System.out.println("Price updated!");
			myReport.addPromoItem(promoItems.get(promoItem-1));
			break;
		case 2:
			//add item by calling appendPromo method
			int k = appendPromo();
			// method will return -1 if menu item is not found in the arraylist
			if (k != -1)
			promoItems.get(promoItem-1).addItem(items.get(k));
			break;
		case 3:
			//removes item
			System.out.println("Please enter item to remove");
			String remove = sc.next();
			promoItems.get(promoItem-1).removeItem(remove);
			System.out.println(remove + " is removed!");
			return;
		default:
			System.out.println("Error! Please enter a valid input");
			break;
				}
			}

		//returns error message if the Promo item is not found
		if (!found) {
			System.out.println("Promotional item not found!");
		}
	}
	
	/**Method called by updateItem to update a MenuItem in the ArrayList
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void updateMenuItem() throws FileNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter menu item id to update");
		int menuId = sc.nextInt();
		boolean found = false;
		if (menuId <= items.size()) {
		//searches for menu item
				System.out.println("1. update price");
				System.out.println("2. update description");
				int choice = sc.nextInt();
				switch(choice) {
				case 1:
					//changes price
					System.out.println("enter new price");
					double temp = sc.nextDouble();
					items.get(menuId-1).setPrice(temp);
					myReport.addMenuItem(items.get(menuId-1).getMenuId(), items.get(menuId-1));
					break;
				case 2:
					//changes description
					System.out.println("enter new description");
					String temp2 = sc.next();
					items.get(menuId-1).setDescription(temp2);
					break;
				default:
					System.out.println("Error! Please enter a valid input");
					break;
				}
				found = true;
		}
		if (!found) {
			//prints error message if its not found
			System.out.println(items.get(menuId-1).getName() + " is not in menu!");
		}
	}
	
	/**Method to add an item to the Menu
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addItem() throws FileNotFoundException, IOException {
		//calls two seperate private method for adding items
		Scanner sc = new Scanner(System.in);
		System.out.println("Please select type of item to add:");
		System.out.println("1. Promotional Item");
		System.out.println("2. Menu Item");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			addPromoItem();
			break;
		case 2:
			addMenuItem();
			break;
		default:
			System.out.println("Error! Please enter a valid input");
			break;
		}
	}
	
	/**Private method called by addItem to add a PromoItem to both the ArrayList and Report 
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void addPromoItem() throws FileNotFoundException, IOException {
		Scanner sc = new Scanner(System.in).useDelimiter("\\n");
		System.out.println("Please enter the name of the new promotional item");
		String name = sc.next();
		
		for (int i = 0; i < promoItems.size(); i ++) {
			if (name.equals(promoItems.get(0).getName())) {
				System.out.println("Error! Item is already in the menu");
				return;
			}
		}
		
		System.out.println("Please enter the price of promotional item");
		double price = sc.nextDouble();
		PromoItem newPromo = new PromoItem(name,price);
				
		System.out.println("Please enter number of menu items included in promotion");
		int item = sc.nextInt();
		for (int i = 0; i < item; i++) {
			int k = appendPromo();
			if (k != -1)
			newPromo.addItem(items.get(k));
		}
		promoItems.add(newPromo);
		myReport.addPromoItem(newPromo);
	}
	
	
	/**Private method called by addItem to add a MenuItem to both the ArrayList and Report 
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void addMenuItem() throws FileNotFoundException, IOException {
		//get required data
		Scanner sc = new Scanner(System.in).useDelimiter("\\n");
		int choice = 0;
		System.out.println("1. Main Course");
		System.out.println("2. Drinks");
		System.out.println("3. Dessert");
		
		do {
		System.out.println("Please enter item type");
		choice = sc.nextInt();
		if (choice < 1 || choice > 3) {
			System.out.println("Error! Please enter a valid input!");
		}
		} while (choice < 1 || choice > 3);
		
		System.out.println("please enter item name");
		String tempName = sc.next();
		for (int i = 0; i < items.size(); i ++) {
			if (tempName.equals(items.get(0).getName())) {
				System.out.println("Error! Item is already in the menu");
				return;
			}
		}
		System.out.println("please enter item price");
		Double tempPrice = sc.nextDouble();
		System.out.println("please enter description");
		String tempDescription = sc.next();
		
		// initialise the menu item
		MenuItem temp = new MenuItem(tempName, tempDescription, tempPrice, choice);
		
		//  add based on the position of the last item of the same class
		items.add(lastpos[choice -1]+1,temp);
		myReport.addMenuItem(choice, temp);
		
		for (int j = choice-1; j < lastpos.length; j++) {
			// increase the last position of the array behind
			lastpos[j]++;
		}
		System.out.println("Menu Item " + tempName + " added!");
	}
	
	/**Load MenuItems that are stored in txt file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void loadMenuItem() throws FileNotFoundException, IOException {
		// loads presaved files
		items = FileReaderWriter.readMenuItemFile("menuItem.txt");
		lastpos = FileReaderWriter.readLastPos("lastpos.txt");
		promoItems = FileReaderWriter.readPromoItemFile("promoItem.txt");
	}
	
	/**Saves both ArrayList and lastpos array into txt files
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveItem() throws FileNotFoundException, IOException {
		//saves current data onto a txt file
		FileReaderWriter.createFile("menuItem.txt");
		for (int i = 0; i < items.size(); i++) {
			FileReaderWriter.addMenuItem("menuItem.txt", items.get(i));
			}
		FileReaderWriter.createFile("promoItem.txt");
		for (int j = 0; j < promoItems.size(); j++) {
			FileReaderWriter.addPromoItem("promoItem.txt", promoItems.get(j));
		}
		FileReaderWriter.createFile("lastpos.txt");
		FileReaderWriter.saveLastPos("lastpos.txt", lastpos);
		
	}
	
	/**Returns index of the menu item 
	 * 
	 * @return index of desired menu item
	 */
	private int appendPromo() {
		//finds desired menu item's position in the arraylist
		Scanner sc = new Scanner(System.in).useDelimiter("\\n");
		System.out.println("Please enter the position of menu item");
		int itemId = sc.nextInt();
		if (itemId <= items.size()) {
			return itemId-1;
		}
		else {
			System.out.println("Item not found");
			return -1;
		}
	}
}
