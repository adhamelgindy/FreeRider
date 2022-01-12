package de.freerider.datamodel;

import java.util.*;

/**
 * 
 */


public class Customer {

    /**
     * 
     */
    private long id ;

    /**
     * 
     */
    private String lastName = "";

    /**
     * 
     */
    private String firstName = "";

    /**
     * 
     */
    private List<String> contacts  =  new ArrayList<String>();

    /**
     * 
     */
    private Status status = null;

    public Customer() {
    	
    	this.lastName = lastName != null ? lastName : "";
		this.firstName = firstName != null ? firstName : "";
		this.id = -1;
		this.status = Status.New;
    }


    /**
     * @return
     */
    public long getId() {
        // TODO implement here
        return id;
    }

    /**
     * @param id 
     * @return
     */
    //chainable self refrence/ method chaining
    
    public Customer setId(long id) {
        // questionmark operator 
        this.id = (this.id < 0 && id >= 0)? id : this.id; //true - false
        return this;
    }

    /**
     * @return
     */
    public String getName() {
    	
    	String name;
    	
    	if(firstName != null) {
    		name = lastName + ", "+ firstName; 
    	} else {
    		name = lastName + ".";
    	}
        return name;
    }

    /**
     * @return
     */
    public String getFirstName() {
        // TODO implement here
        return firstName;
    }

    /**
     * @return
     */
    public String getLastName() {
        // TODO implement here
        return lastName;
    }

    /**
     * @param name 
     * @return
     */
    public Customer setName(String name) {
        
    	if(lastName == null) {
			this.lastName = "";
		}else {
			this.lastName = name;
		}
		return this;

    }

    /**
     * @param first 
     * @param last 
     * @return
     */
    public Customer setName(String first, String last) {
        this.lastName = last;
        this.firstName = first;
        return this;
    }

    /**
     * @return
     */
    public int contactsCount() {
        // TODO implement here
    	int sum = 0;
    	//for()
    	sum++;
    	
        return sum;
    }

    /**
     * @return
     */
    public Iterable<String> getContacts() {
        // TODO implement here
        return contacts;
    }

    /**
     * @param contact 
     * @return
     */
    public Customer addContact(String contact) {
        contacts.add(contact);
        return this;
    }

    /**
     * @param i 
     * @return
     */
    public void deleteContact(int id) {
        contacts.remove(id);
        this.id = id;
    }

    /**
     * @return
     */
    public void deleteAllContacts() {
        contacts.removeAll(contacts);
    }

    /**
     * @return
     */
    public Status getStatus() {
        // TODO implement here
        return status;
    }

    /**
     * @param status 
     * @return
     */
    public Customer setStatus(Status status) {
        this.status = status;
        return this;
    }


}