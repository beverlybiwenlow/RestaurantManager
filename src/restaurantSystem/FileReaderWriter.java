package restaurantSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//import Booking;
import Menu.MenuItem;
import Menu.PromoItem;
import Reservation.Booking;


/**
 * Class of static methods to read / write objects into txt files and back
 * @author tinghaong
 *
 */
public class FileReaderWriter {
	
	/** creates or resets a txt file
	 * 
	 * @param name of file
	 * @throws IOException
	 */
   public static void createFile(String file_name) throws IOException{
	  //creates / resets the file 
      File file = new File(file_name);
      FileWriter reset = new FileWriter(file, false);
      reset.close();
   }
   
   /**Add MenuItem to write and append into txt file
    * 
    * @param name of file
    * @param MenuItem to be written
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static void addMenuItem(String file_name, MenuItem menu_item) throws IOException, FileNotFoundException {
	   	  FileWriter writer = new FileWriter(file_name, true); 
	   	  
	   	  //saves menu item into a string using : for splitting
	      String temp = menu_item.getName() + ":";
	      temp += Integer.toString(menu_item.getMenuId()) + ":";
	      temp += menu_item.getDescription()+":";
	      temp += Double.toString(menu_item.getPrice());
	      writer.write(temp + "\n");
	      writer.flush();
	      writer.close();
   }
   	
   /** Add PromoItem to write and append into txt file
    * 
    * @param name of file
    * @param PromoItem to be written
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static void addPromoItem(String file_name, PromoItem promo_item) throws IOException, FileNotFoundException {
	   FileWriter writer = new FileWriter(file_name, true); 
	   
	   //saves each promotional item into a string using : as splitter
	   //the word promo is used to differentiate between promoitem and menuitem 
	   //when reading
	   String temp2 = "Promo:" + promo_item.getName() + ":";
	   //temp2 += Integer.toString(promoItem.getPromoId()) + ":";
	   temp2 += Double.toString(promo_item.getPrice());
	   writer.write(temp2 + "\n");
	   writer.flush();
	     for (int i = 0; i < promo_item.getSize(); i++) {
	    	 //saves menu item that is stored within promoitem
	    	  addMenuItem(file_name, promo_item.getItem(i));
	     }
	     writer.close();
   }
   
   /**Reads and returns an ArrayList of MenuItem from a txt file
    * 
    * @param file name to read
    * @return ArrayList of MenuItem parsed from txt file
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static ArrayList<MenuItem> readMenuItemFile(String file_name) throws IOException,FileNotFoundException{
	   //int size = 0;
	   ArrayList<MenuItem> menuItem = new ArrayList<MenuItem>();
	   	   
	   // method to reach each line and store it in menu item object
	   try(BufferedReader br1 = new BufferedReader(new FileReader(file_name))) {
		   String line1;
	       while ((line1 = br1.readLine()) != null) {
	    	   //splitter using : to find desired strings
	    	   String[] splitter = line1.split(":");
	    	   MenuItem holder = new MenuItem(splitter[0],splitter[2],Double.parseDouble(splitter[3]), Integer.parseInt(splitter[1]));
	    	   menuItem.add(holder);
	           }
	       }
	   //return menu item object
	   return menuItem;
   }
   
   /**Reads and returns an ArrayList of PromoItem from a txt file
    * 
    * @param file name to read
    * @return ArrayList of PromoItem parsed from txt file
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static ArrayList<PromoItem> readPromoItemFile(String file_name) throws IOException,FileNotFoundException{
	   //int size = 0;
	   ArrayList<PromoItem> promoItem = new ArrayList<PromoItem>();
	   
	   try(BufferedReader br1 = new BufferedReader(new FileReader(file_name))) {
		   String line1;
		   int k = 0; //number of PromoItem
	       while ((line1 = br1.readLine()) != null) {
	    	   //splits the string in the txt file
	    	   String[] splitter = line1.split(":");
	    	   
	    	   //if the string in position zero is Promo, it is a PromoItem
	    	   if (splitter[0].equals("Promo")) {
	    		   PromoItem temp = new PromoItem(splitter[1], Double.parseDouble(splitter[2]));
	    		   promoItem.add(temp);
	    		   k++;
	    	   }
	    	   else {
	    		   //else the item is a menu item
	    		   //inititialise MenuItem object
		    	   MenuItem holder = new MenuItem(splitter[0],splitter[2],Double.parseDouble(splitter[3]), Integer.parseInt(splitter[1]));
		    	   //adds it into current PromoItem object
		    	   promoItem.get(k-1).addItem(holder);
	    	   	}
	           }
	       }
	   //returns PromoItem
	   return promoItem;

   }
   
   
   /**Saves an integer array into a txt file
    * 
    * @param file name to read
    * @param integer array to save
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static void saveLastPos(String file_name, int[] last_pos) throws IOException, FileNotFoundException {
	   	  FileWriter writer = new FileWriter(file_name, false); 
	   	  
	   	  //saves integer array into a txt file
	   	  for (int i = 0; i < last_pos.length; i++) {
	   		  String temp = Integer.toString(last_pos[i]);
	   		  writer.write(temp + "\n");
	   		  writer.flush();
	   	  }
	   	  writer.close();

   }
   
   
   /**Reads and parse integers into an array from txt file
    * 
    * @param file name to read
    * @param integer array from txt
    * @throws FileNotFoundException
    * @throws IOException
    */
   public static int[] readLastPos(String file_name) throws FileNotFoundException, IOException {
	   int size = 0;
	   int[] lastpos;
	   
	   try(BufferedReader br = new BufferedReader(new FileReader(file_name))) {
		   String line;
	       while ((line = br.readLine()) != null) {
	    	   size++;
	       }
	   }
	       
	   lastpos = new int[size];
	   
	   try(BufferedReader br1 = new BufferedReader(new FileReader(file_name))) {
		   String line1;
	       int i = 0;
	       while ((line1 = br1.readLine()) != null) {
	    	   int temp = Integer.parseInt(line1);
	    	   lastpos[i] = temp;
	    	   i++;
	           }
	       }
   return lastpos;
   }
  
   
   /**Reads and returns an ArrayList of bookings from a txt file
    * 
    * @param file name to read
    * @return ArrayList of bookings parsed from txt file
    * @throws FileNotFoundException
    * @throws IOException
    */
   public static ArrayList<Booking> readBookingFile(String file_name) throws FileNotFoundException, IOException {
	   //int size = 0;
	   ArrayList<Booking> bookingList = new ArrayList<Booking>();
	   	   
	   // method to reach each line and store it in menu item object
	   try(BufferedReader br1 = new BufferedReader(new FileReader(file_name))) {
		   String line1;
	       while ((line1 = br1.readLine()) != null) {
	    	   //splitter using : to find desired strings
	    	   String[] splitter = line1.split(":");
	    	   Booking holder = new Booking(splitter[0],Integer.parseInt(splitter[1]),Integer.parseInt(splitter[2]), Integer.parseInt(splitter[3]), Integer.parseInt(splitter[4]));
	    	   holder.setTableId(Integer.parseInt(splitter[5]));
	    	   bookingList.add(holder);
	           }
	       }
	   //return menu item object
	   return bookingList;
   }
   
   /**Saves and append booking to a txt file
    * 
    * @param file name to read
    * @param booking to be written into txt file
    * @throws IOException
    */
   public static void saveBooking(String file_name, Booking booking) throws IOException {
	   	  FileWriter writer = new FileWriter(file_name, true); 

	   	  //saves booking item into a string using : for splitting
	      String temp = booking.getName() + ":";
	      temp += Integer.toString(booking.getNoOfPeople()) + ":";
	      temp += Integer.toString(booking.getPhone()) + ":";
	      temp += Integer.toString(booking.getTimeExtended()) + ":";
	      temp += Integer.toString(booking.getDate()) + ":";
	      temp += Integer.toString(booking.getTableId());
	      writer.write(temp + "\n");
	      writer.flush();
	      writer.close();
   }
   
   /**Prints report into a txt file
    * 
    * @param Report
    * @throws IOException
    */
   public static void printReport(String sales_breakdown) throws IOException{
	   try{
		   FileWriter fwStream = new FileWriter("report.txt");
		   BufferedWriter bwStream = new BufferedWriter(fwStream);
		   PrintWriter pwStream = new PrintWriter(bwStream);
		   pwStream.println(sales_breakdown);
		   pwStream.close();
	   }finally{}
   }
}
