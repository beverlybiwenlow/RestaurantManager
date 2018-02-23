package Menu;
import java.util.ArrayList;

/**
 * PromoItem to be stored in the menu
 * @author tinghaong
 *
 */
public class PromoItem extends BasicItem{
	/** ArrayList of MenuItem that is part of the PromoItem
	 */
	private ArrayList<MenuItem> promoItem;
	
	/**Initialise the PromoItem with its parameters
	 * Description of PromoItem will be a description of its MenuItem
	 * 
	 * @param name of PromoItem
	 * @param price of PromoItem
	 */
	public PromoItem(String name, double price) {
		super(name, null, price);
		promoItem = new ArrayList<MenuItem>();
	}
	
	/**Returns the number of MenuItems in PromoItem
	 * 
	 * @return number of MenuItems in PromoItem
	 */
	public int getSize() {
		return promoItem.size();
	}
	
	/**Returns the MenuItem in a certain index
	 * 
	 * @param index of MenuItem 
	 * @return MenuItem at that index
	 */
	public MenuItem getItem(int index) {
		return promoItem.get(index);
	}
	
	/**Adds a MenuItem to the ArrayList
	 * 
	 * @param MenuItem to be added
	 */
	public void addItem(MenuItem item) {
		promoItem.add(item);
	}
	
	/**Removes a MenuItem from the ArrayList
	 * 
	 * @param name of MenuItem to remove (case sensitive)
	 */
	public void removeItem(String name) {
		boolean complete = false;
		
		for (int i = 0; i < promoItem.size(); i++) {
			if (promoItem.get(i).getName().equals(name)) {
				System.out.println("Item removed!");
				promoItem.remove(i);
				complete = true;
				break;
			}
		}
		
		if (!complete) {
			System.out.println(name + " is not found in promotional item");
		}
	}
	
	/**Prints the list of MenuItems stored in promoItems
	 */
	public void printPromoItems() {
		
		//prints out list of its items
		for (int i = 0; i < promoItem.size(); i++) {
			System.out.println(promoItem.get(i).getName() + " : " + promoItem.get(i).getDescription());
		}
	}

}
