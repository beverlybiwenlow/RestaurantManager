package Menu;

/**
 * AlaCarte Menu Item to be stored in the menu
 * @author tinghaong
 *
 */
public class MenuItem extends BasicItem {
	/* position of item in menu index
	 */
	private int menuId;
	
	/**Initialise Menu Item with its parameters
	 * 
	 * @param name of Menu Item
	 * @param description of Menu Item
	 * @param price of Menu Item
	 * @param menu_id of Menu Item
	 */
	public MenuItem (String name, String description, double price, int menu_id) {
		super(name, description, price);
		setMenuId(menu_id);
	}
	
	/**Sets the id of the MenuItem
	 * 
	 * @param ID of MenuItem
	 */
	public void setMenuId(int menu_id) {
		this.menuId = menu_id;
	}
	
	/**Returns the id of the MenuItem
	 * 
	 * @return ID of MenuItem
	 */
	public int getMenuId() {
		return menuId;
	}
	
}
