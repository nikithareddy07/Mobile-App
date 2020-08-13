/* @author Mayuri Waghmode
 * @since 10-30-2018 
 * Time : 10:30 AM
 * Purpose : MobilePhone that holds the ArrayList of Contacts, and has functionalities like add,delete,update,search,print.
 * 			 read and Writes contact to file contact.txt
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class MobilePhone {
	//Private instance variable
	private ArrayList<Contact> myContacts;
	private static final String FILENAME = "contact.txt";
	private PrintWriter outputStream = null;
	private Scanner inputStream = null;
	/**
	 * Default constructor to initialize the ArrayList: myContacts
	 */
	public MobilePhone() {
		myContacts = new ArrayList<Contact>(); 
		processContactIntoArrayList();
	}
	
	/**
	 * Method processContactIntoArrayList : initializes input and output stream and
	 * fills arraylist with contact.txt
	 * Used ":" as content seperator in contact.txt file
	 */
	private void processContactIntoArrayList() {
		try
		{
			
			inputStream = new Scanner(new FileInputStream(FILENAME));
			System.out.println(inputStream.hasNextLine());
			while(inputStream.hasNextLine()) {
				String record =inputStream.nextLine();
				
				String[] nmPhoneNo =record.split(":");
				myContacts.add(new Contact(nmPhoneNo[0],nmPhoneNo[1]));
			}
			writeContactsToFile();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + FILENAME);
			System.exit(0);
		}
	}
	
	/**
	 * Method writeContactsToFile : Writes contacts from arraylist myContacts to file contact.txt
	 */
	private void writeContactsToFile() {
		try
		{
			File file = new File(FILENAME); 
			outputStream = new PrintWriter(file);
			for(Contact contact : myContacts ) {
				outputStream.append(contact.getName()+":"+contact.getPhoneNumber()+System.lineSeparator());
			}
			outputStream.close();
		}catch(Exception e) {
			System.out.println("Error writing in the file"+FILENAME);
		}
	}
	/**
	 * Method camelCase : converts name to camel case.
	 * @param name
	 * @return
	 */
	public String camelCase(String name) {
		String[] op;
		op = name.trim().split("\\s+");
		name="";
		for(int i= 0;i<op.length;i++) {
			String str = op[i];
			str= str.substring(0, 1).toUpperCase()+str.substring(1).toLowerCase();
			if(i==op.length-1)
				name +=str;
			else
				name +=str+" ";
		}
		return name;
	}
	/**
	 * Method addNewContact : adds new contact into myContact ArrayList 
	 */
	 public String addNewContact(String name, String phoneNo) {
		 
		int index = search(name);
		String response = null;
		if(index ==-1) {
			Contact contact = new Contact(name,phoneNo);
			myContacts.add(contact);
			writeContactsToFile();
			response = "New contact added:"+System.lineSeparator()+"Name: "+contact.getName()+",\nPhone: "+contact.getPhoneNumber();
		}
		else {
			response = "Contact already exists.";
		}
		return response;
	 }
	 
	 /**
	  * Method updateContact : update the oldContact with the newContact information if found else prompts not found.
	  */
	 public String updateContact(String name, String newPhoneNo) {
		int index = search(name);
		String response = null;
		if(index ==-1)
			response = "Contact not found in the list.";
		else {
			myContacts.set(index, new Contact(name,newPhoneNo));
			writeContactsToFile();
			response = "Successfully updated record."+System.lineSeparator()+"Name: "+name+",\nPhone: "+newPhoneNo;
		}
		return response;
	 }
	 
	 /**
	  * Method removeContact : removes the contact if found else prompts not found.
	  */
	 public String removeContact(String name) {
		 
		int index = search(name);
		String response = null;
		if(index ==-1)
			response = "Contact not found in the list.";
		else {
			myContacts.remove(index);
			writeContactsToFile();
			response = name+" was deleted.\n"+"Successfully deleted.";
		}
		return response;
	 }
	 
	 /**
	  * Method searchContact : searches the contact if present else prompts not found.
	  */
	 public String searchContact(String name) {
		
		String response = null;
		int index = search(name);
		if(index ==-1)
			response = "Contact not found in the list.";
		else
			response = "Name: "+myContacts.get(index).getName()+",\nPhone: \n"+myContacts.get(index).getPhoneNumber(); 
		return response;
	 }
	 
	 /**
	  * Method search : searches the contact name in myContacts and returns index of contact if not found returns -1
	  * @param name
	  * @return index
	  */
	 private int search(String name) {
		int index = 0;
		for(Contact contact : myContacts ) {
			if(contact.getName().equals(name))
				return index;
			index++;
		}
		return -1;
	 }
	 
	 /**
	  * Method printContacts : print's out all the contact information in the ArrayList
	  */
	 public String printContacts() {
		 int cnt=0;
		 String response = "";
		 if(myContacts.isEmpty())
			 response = "List is Empty";
		 else
			 for(Contact contact : myContacts ) {
				cnt++;
				response += cnt+". "+contact.getName()+" -> "+contact.getPhoneNumber()+"\n";
			 }
		 return response;
	 }
}
