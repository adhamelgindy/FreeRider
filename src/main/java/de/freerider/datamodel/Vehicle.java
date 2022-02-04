package de.freerider.datamodel;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Vehicle")
public class Vehicle {

	/**
	 * id attribute, {@code < 0} invalid, can be set only once.
	 */
	@Id // PRIMARY KEY attribute
	@Column(name = "ID")
	private int id;
	/**
	 * 
	 */
	@Column(name = "PERSONENANZAHL")

	private int personenzahl;
	/**
	   * 
	   */
	@Column(name = "MAKE")
	private String make;

	/**
	   * 
	   */
	@Column(name = "MODEL")
	private String model;

	/**
	   * 
	   */
	@Column(name = "LOCATION")
	private String location;

	/**
	   * 
	   */
	@Column(name = "COLOR")
	private String color;
	
	/**
     * 
     */
	  @Transient // 1:n relation, currently not in table
	  private List<String> fotos = new ArrayList<String>();
	  
	  public enum Power {

			electric,
			diesel,
			gas,
			hybrid
		}
	  
	  private Power power = Power.gas; //default power is gasoline


	public Vehicle() {
		// TODO Auto-generated constructor stub
	}

	public Vehicle(int id, String make, String model, String color) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.color = color;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getMake() {
		return make;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setPower(Power power) {
		// TODO Auto-generated method stub
		this.power = power;
	}
	
	public Power getPower() {
		// TODO Auto-generated method stub
		return power;
	}

	public void setPassengers(int personenzahl) {
		// TODO Auto-generated method stub
		this.personenzahl = personenzahl;
	}
	
	public int getPassengers() {
		// TODO Auto-generated method stub
		return personenzahl;
	}

	public List<String> getImgUrl() {
		// TODO Auto-generated method stub
		return fotos;
	}

	
//	public void setPower(Power power) {
//		this.Power = power;
//	}

}
