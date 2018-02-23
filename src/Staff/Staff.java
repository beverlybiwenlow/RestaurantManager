package Staff;

/**Staff of the restaurant
 * 
 * @author tinghaong
 *
 */
class Staff {
	/** Name of staff*/
	private String name;
	/** Gender of staff */
	private char gender;
	/** Id of staff */
	private int staffId;
	/** Job Title of staff */
	private String jobTitle;
	
	/**Initialise the staff with its parameters
	 * 
	 * @param name of staff
	 * @param gender of staff
	 * @param staff id of staff
	 * @param job title of staff
	 */
	protected Staff(String name, char gender, int staff_id, String job_title) {
		setName(name);
		setGender(gender);
		setStaffId(staff_id);
		setJobTitle(job_title);
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	/**Sets staff's gender
	 * 
	 * @param gender of staff
	 */
	protected void setGender(char gender) {
		this.gender = gender;
	}
	
	/**Sets staff's ID
	 * 
	 * @param ID of the staff
	 */
	protected void setStaffId(int staff_id) {
		this.staffId = staff_id;
	}
	
	/**Set staff's job title
	 * 
	 * @param job title of staff
	 */
	protected void setJobTitle(String job_title) {
		this.jobTitle = job_title;
	}
	
	/**Return name of staff
	 * 
	 * @return name of staff
	 */
	protected String getName() {
		return name;
	}
	
	/**Return gender of staff
	 * 
	 * @return gender of staff
	 */
	protected char getGender() {
		return gender;
	}
	
	/**Return id of staff
	 * 
	 * @return id of staff
	 */
	protected int getStaffId() {
		return staffId;
	}
	
	/**Return job title of staff
	 * 
	 * @return job title of staff
	 */
	protected String getJobTitle() {
		return jobTitle;
	}
}