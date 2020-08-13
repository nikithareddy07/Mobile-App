package application;
/* @author Mayuri Waghmode
 * @since 10-21-2018 
 * Time : 5:15 AM
 * Purpose : a Contact class for contacts (name and phone number)
 */

public class Contact {
	//Private instance variable
	private String name;
	private String phoneNumber;
	
	/**
	 * Constructor with parameters
	 * @param name
	 * @param phoneNumber
	 */
	public Contact(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	//getters and setters

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
}
