package Menu;
/**
 * Basic food item 
 * @author tinghaong
 *
 */
public class BasicItem {
	/** sales revenue of the item over a time period */
	private double sales;
	/** price of a single item */
	private double price;
	/** Qualitative description of the item */
	private String descrption;
	/** Name of the Basic item object */
	private String name;
	/** Quantity of the item sold */
	private int quantity;
	
	/**Basic item constructor which stores the name, description and price of the item
	 * 
	 * @param name of item 
	 * @param description of item
	 * @param price of item
	 */
	public BasicItem (String name, String description, double price) {
		this.name = name;
		quantity = 0;
		setDescription(description);
		setPrice(price);
	}
	/**Gets the quantity of the item sold
	 * 
	 * @return quantity of items sold
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**Sets the quantity of the item sold
	 * 
	 * @param new quantity of items sold
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**Increases the quantity of item sold by the input
	 * 
	 * @param quantity of items sold to add to this.quantity
	 */
	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}
	
	/**Returns the total price of the items sold
	 * 
	 * @return double total price of items sold
	 */
	public double getQuantityPrice() {
		return quantity * price;
	}
	
	/**Sets a description for the item
	 * 
	 * @param description of the item
	 */
	public void setDescription(String description) {
		this.descrption = description;
	}
	
	/**Sets the price of a single unit of the item
	 * 
	 * @param price of a single unit of item
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**Returns the price of a single unit of item
	 * 
	 * @return price of a single unit of item
	 */
	public double getPrice() {
		return price;
	}
	
	/**Returns the name of the item
	 * 
	 * @return name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**Returns the description of the item
	 * 
	 * @return description of the item
	 */
	public String getDescription() {
		return descrption;
	}
	
	/**Returns the sales of the item
	 * 
	 * @return total sales of the item
	 */
	public double getSales(){
		return sales;
	}
	
	/**Sets the total sales of the item for recording
	 * 
	 * @param total sales of the item
	 */
	public void setSales(double sales){
		this.sales = sales;
	}

}
