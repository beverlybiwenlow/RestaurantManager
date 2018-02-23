package restaurantSystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import Reservation.Booking;
/**
 * Stores the static system functions that is used by many of the other classes
 * @author Crystal
 *
 */
public class SystemFunctions {
 /**
  * Creates Calendar object that can get system clock information
 */
 private static Calendar current;
 
 /**
  * Reservations can only be made for 30 days in advance. 
  * This method checks for the current date based on the system clock 
  * and returns the furthest date one can make a reservation
  * @return Maximum date for reservation
  */
 public static int getMaxDate() {
  int currentDate=SystemFunctions.getCurrentDate();
  int maxDate=currentDate+100; //Adds one month
  int maxDateMonth=(maxDate/100)%100;
  
  if(maxDateMonth==13){ //Last month is 12 (December) so must initialise to start from the new year
   int tempDay=maxDate%100;
   int maxDateYearMonth2=(maxDate/100) + 88; //To get the first month of next year (YYYY13+88=YYYY[+1]01)
   maxDate=(maxDateYearMonth2*100) + tempDay;
  }   
  int maxDateYearMonth=maxDate/100;
  
  if ((maxDate%100)>getDaysInMonth(maxDateYearMonth)) 
   maxDate=(maxDateYearMonth*100)+getDaysInMonth(maxDateYearMonth);
  return maxDate;
 }
 
 /**
  * Gets the current time from the system clock in HHmm/24h format
  * @return Current Time
  */
 public static int getCurrentTime(){//returns current time in HHmm
  //get current time !done
  //check upcoming reservations, get list of reservations (wenjun)
  //for each reservation, check if over reservation time !done
  //call cancel reservation if true (main call cancel)
  
  current=Calendar.getInstance();
  int currentTime=current.get(Calendar.HOUR_OF_DAY)*100;
  currentTime+=current.get(Calendar.MINUTE);
  return currentTime;
 }
 
 /**
  * Gets current date from the system clock in YYYYMMDD format
  * @return Current Date
  */
 public static int getCurrentDate(){//returns current date in YYYYMMDD
  current=Calendar.getInstance();
  int currentDate=current.get(Calendar.YEAR)*100;
  currentDate=(currentDate + (current.get(Calendar.MONTH)+1))*100;
  currentDate+=(current.get(Calendar.DAY_OF_MONTH));
  
  return currentDate;
 }
 
 /**
  * Gets the number of days a given month
  * @param yearmonth Any specific month in YYYYMM format
  * @return Days in the month in int
  */
 public static int getDaysInMonth(int yearmonth) {
  YearMonth month = YearMonth.of(yearmonth/100, yearmonth%100);
  int daysInMonth = month.lengthOfMonth(); 
  return daysInMonth;
 }
 
}
