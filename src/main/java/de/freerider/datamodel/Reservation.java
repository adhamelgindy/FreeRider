package de.freerider.datamodel;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.jpa.repository.Temporal;

public class Reservation {

	@Column(name = "ID")
	private int id = -1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id")
	private Vehicle v;
	
	@Column(name = "ZEIT_Begin")
	//@Temporal(TemporalType.TIMESTAMP)
	private Date zeitraumBeginn;
	
	@Column(name = "ZEIT_Ende")
	//@Temporal(TemporalType.TIMESTAMP)
	private Date zeitraumEnde;
	
	@Column(name = "LOC_Beginn")
	@Enumerated(EnumType.STRING)
	private LocCode lokationBeginn;
	
	@Column(name = "LOC_Ende")
	@Enumerated(EnumType.STRING)
	private LocCode lokationEnde;
	
	public enum Status {
		Requested, Confirmed, Rejected, Cancelled
	}
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status = Status.Requested;
	
	public static final DateFormat ISO8601DATEFormat = new SimpleDateFormat("MMM dd, yyy HH:mm 'UTC'Z", Locale.US);
	
	
	public Reservation() {}  //default constructor needed by JPA obj creation

	public Reservation(int id, Customer customer, Vehicle v) {
		this.id = id;
		this.customer = customer;
		this.v = v;
	}
	
	public Date getZeitraumBeginn() {
		return zeitraumBeginn;
	}
	
	public void setZeitraumBeginn(Date zeitraumBeginn) {
		this.zeitraumBeginn = zeitraumBeginn;
	}
	
	public Date getZeitraumEnde() {
		return zeitraumEnde;
	}
	
	public void setZeitraumEnde(Date zeitraumEnde) {
		this.zeitraumEnde = zeitraumEnde;
	}
	
	public void setLokationBeginn(LocCode lokationBeginn) {
		this.lokationBeginn = lokationBeginn;
	}

	public LocCode getlokationBeginn() {
		return lokationBeginn;
	}
	
	public void setLokationEnde (LocCode lokationEnde) {
		this.lokationEnde = lokationEnde;
	}

	public LocCode getlokationEnde() {
		return lokationEnde;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Vehicle getVehicle() {
		return v;
	}
	
	public void setVehicle(Vehicle v) {
		this.v = v;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public Status getStatus() {
        // TODO implement here
        return status;
    }

    /**
     * @param status 
     * @return
     */
    public Reservation setStatus(Status status) {
        this.status = status;
        return this;
    }

	public Reservation pickup(LocCode locPickup, String pickUpDate) {
		this.lokationBeginn = locPickup;
		this.zeitraumBeginn = imporDateStr(pickUpDate);
		return this;
	}
	
	public Reservation dropoff(LocCode locReturn, String dropDate) {
		this.lokationEnde = locReturn;
		this.zeitraumEnde = imporDateStr(dropDate);
		return this;
	}
	
	private Date imporDateStr (String dateStr) throws IllegalArgumentException {
		try {
			return ISO8601DATEFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace(); 
		}
		return zeitraumBeginn;
	}

}
