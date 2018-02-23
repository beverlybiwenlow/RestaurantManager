package Staff;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores the list of all staff that works for the restaurant
 * @author tinghaong
 *
 */
public class StaffManager {
/** ArrayList of staff that works in the restaurant */
private ArrayList<Staff> list;
/** Index of current staff that is working 
 * initialised at -1 */
private int currentUser = -1;

/** Initialise the 
 * 
 */
public StaffManager() throws IOException {
	list = new ArrayList<Staff>();
	loadStaff();
}

public void selectStaff() {
	viewStaff();
	System.out.println("Please select your choice");
	Scanner sc = new Scanner(System.in);
	int choice = sc.nextInt();
	currentUser = choice -1;
}

public String getCurrentName() {
	if (currentUser == -1) {
		return "No one";
	}
	return list.get(currentUser).getName();
}


public void deleteStaff() {
	Scanner sc = new Scanner(System.in).useDelimiter("\\n");
	System.out.println("Please enter name of staff to be removed");
	String name = sc.next();
	for (int i = 0; i < list.size(); i++) {
		if (name.equals(list.get(i).getName())) {
			list.remove(i);
			System.out.println("Staff " + name + " is removed!");
		}
	}
}

public void addStaff() {
	Scanner sc = new Scanner(System.in).useDelimiter("\\n");
	System.out.println("Please enter new staff name");
	String name = sc.next();
	System.out.println("Please enter new staff gender");
	char gender = sc.next().charAt(0);
	System.out.println("Please enter new staff id");
	int staffId = sc.nextInt();
	System.out.println("Please enter new job title");
	String jobTitle = sc.next();
	Staff temp = new Staff(name, gender, staffId, jobTitle);
	list.add(temp);
}

public void viewStaff() {
	for (int i = 0; i < list.size(); i++) {
		System.out.println(i+1 + " : " + list.get(i).getName() + " : " + list.get(i).getGender() + " : " + list.get(i).getStaffId() + " : " + list.get(i).getJobTitle());
	}
	System.out.println("");
}

public void loadStaff() throws NumberFormatException, IOException {
	 try(BufferedReader br1 = new BufferedReader(new FileReader("staffList.txt"))) {
		   String line1;
	       while ((line1 = br1.readLine()) != null) {
	    	   //splitter using : to find desired strings
	    	   String[] splitter = line1.split(":");
	    	   Staff holder = new Staff(splitter[0],splitter[1].charAt(0),Integer.parseInt(splitter[2]), splitter[3]);
	    	   list.add(holder);
	           }
	       }
}

public void saveStaffList() throws IOException {
 	  FileWriter writer = new FileWriter("staffList.txt", false); 
 	for (int i = 0; i < list.size(); i ++) {
    String temp = list.get(i).getName() + ":";
    temp += "" + list.get(i).getGender() + ":";
    temp += Integer.toString(list.get(i).getStaffId()) + ":";
    temp += list.get(i).getJobTitle();
    writer.write(temp + "\n");
 	}
    writer.flush();
    writer.close();
}
}
