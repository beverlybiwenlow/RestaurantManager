package Reservation;

import restaurantSystem.SystemFunctions;

/**
 * Represents a reservation made by a customer before arriving at the restaurant
 * @author Crystal
 *
 */
public class Booking {
 /**
  * First name of the booker
  */
 private String name;
 /**
  * Number of people arriving and minumum seats required
  */
 private int noOfPeople;
 /**
  * Phone number of this booker
  */
 private int phone;
 /**
  * Unique ID given to each booking
  */
 private int bookingID;
 /**
  * Whether the booker turned up for his reservation
  */
 private boolean personTurnedUp;
 /**
  * The assigned table to this reservation
  */
 private int tableId;
 
 /**
  * Session of the booking (AM or PM)
  */
 private boolean time;//am or pm
 /**
  * The 24h time of arrival of the reservation
  */
 private int timeExtended;
 /**
  * Date of the reservation
  */
 private int date;
 
 /**
  * Creates a new booking
  */
 public Booking() {};
 /**
  * Creates a new booking with given fields
  * @param n The booker's first name
  * @param ppl Number of people for this reservation
  * @param hp The booker's phone number
  * @param time The 24h time of arrival
  * @param date The date of the booking
  */
 public Booking(String n, int ppl, int hp, int time, int date){
  this.setName(n);
  this.setNoOfPeople(ppl);
  this.setPhone(hp);
  this.setDate(date);//YYYYMMDD
  this.setTimeExtended(time);
  bookingID=date+hp;
  this.setPersonTurnedUp(false);
  
 }
 
 /**
  * Sets the assigned table to the reservation
  * @param table_id The assigned tableID
  */
 public void setTableId(int table_id) {
  this.tableId = table_id;
 }
 
 /**
  * Gets the assigned table ID
  * @return This reservation's assigned table's ID
  */
 public int getTableId() {
  return tableId;
 }
 
 /**
  * Gets if the booker turned up for the reservation
  * @return as above
  */
 public boolean getPersonTurnedUp(){
    return personTurnedUp;
 }
   
 /**
  * Sets whether the person turned up
  * @param person_turned_up
  */
 public void setPersonTurnedUp(boolean person_turned_up){
    personTurnedUp=person_turned_up;
 }
 
 /**
  * Get's booker's phone number
  * @return Booker's phone number
  */
 public int getPhone(){
  return phone;
 }
 
 /**
  * Gets the number of seats (type) of table required
  * @return Type of table
  */
 public int getType() {
  int type=0;
  switch(noOfPeople) {
  case 1:
  case 2: type= 2; break;
  
  case 3:
  case 4: type= 4; break;
  
  case 5:
  case 6:
  case 7:
  case 8: type= 8; break;
  
  case 9:
  case 10: type= 10; break; //Add the case if >10 ppl 
  
  
  }return type;
 }
 
 /**
  * Sets booker's phone number
  * @param phone_num
  */
 public void setPhone(int phone_num){
  phone=phone_num;
 }
 
 /**
  * Gets the booker's first name
  * @return Booker's first name
  */
 public String getName(){
  return name;
 }
 
 /**
  * Sets the name of the booker
  * @param name_input Name of booker
  */
 public void setName(String name_input){
  name=name_input;
 }
 
 /**
  * Gets number of people arriving for the booking
  * @return Number of people
  */
 public int getNoOfPeople(){
  return noOfPeople;
 }
 
 /**
  * Sets number of people ariving for the booking
  * @param num_of_people
  */
 public void setNoOfPeople(int num_of_people){
  noOfPeople = num_of_people;
 }
 
 /**
  * Gets the unique booking ID of the booking
  * @return Booking ID
  */
 public int getBookingID(){
  return bookingID;
 }
 
 /**
  * Returns the Session of the booking 
  * @return AM or PM
  */
 public boolean getTime(){//am or pm
  return time;
 }
 /**
  * Sets the Session of the booking 
  * @param time_input Whether it is AM or PM
  */
 public void setTime(int time_input){//am(0) or pm(1) (should i setTime based on time?)
  if ((time_input/100)<=15 && (time_input/100)>=11) time=true;
  else if ((time_input/100)>=1800 && (time_input/100)<=2200)
  time=false;
  //else
   //System.out.println("Time is outside Restaurant opening hours");
 }
 /**
  * Gets the time of arrival for the booking in 24h format
  * @return Time of arrival
  */
 public int getTimeExtended(){
  return timeExtended;
 }
 
 /**
  * Sets the time of arrival of the booker in 24h format
  * @param time Time of arrival
  */
 public void setTimeExtended(int time){//in 2359
  timeExtended=time;
  setTime(time);
 }
 
 /**
  * Gets the date of the reservation in YYYYMMDD format
  * @return Date of the reservation
  */
 public int getDate(){//YYYYMMDD
  return date;  
 }
 
 /**
  * Sets the date of the reservation in YYYYMMDD format
  * @param date_input Booker's required date of reservation
  */
 public void setDate(int date_input){//YYYYMMDD
  date=date_input;
 }
 
 /**
  * If reservation's time has passed more than 30mins, the reservation has expired
  * @return True if expired, else False
  */
 public boolean reservationExpireCheck(){
  //create cutofftime that is 30min after booking time

  //return true if expired
  int CutOffHour=timeExtended/100;
  int CutOffMin=(timeExtended%100)+30;
  if (CutOffMin>59){
   CutOffHour++;
   CutOffMin-=60;
  }
  
  //compare cutoff to current
  int CutOffTime=(CutOffHour*100)+CutOffMin;
  int c=SystemFunctions.getCurrentTime();
  
  if (c>CutOffTime)return true;
  else return false;

 }
 
 /**
  * Checks which session a given 24h time is in
  * @param time_input This is the 24h time 
  * @return True for AM, False for PM
  */
 public static boolean isItAm(int time_input){
  if ((time_input/100)<=15 && (time_input/100)>=11) return true;
  else //if ((t/100)>=1800 && (t/100)<=2200)
  return false;
 }
 
}