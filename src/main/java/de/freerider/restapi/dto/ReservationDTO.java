package de.freerider.restapi.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.freerider.datamodel.LocCode;
import de.freerider.datamodel.Reservation;

public class ReservationDTO {
	
	public static final DateFormat ISO8601DATEFORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss:SSS 'UTC'Z", Locale.US);
	
	@JsonProperty("reservation-id")
	private long rid;
	
	@JsonProperty("customer-name")
	private String customerName;
	
	@JsonProperty("vehicle")
	private VehicleDTO vehicle;
	
	@JsonProperty("reservation-begin")
	private String reservationBegin;

	@JsonProperty("reservation-ende")
	private String reservationEnde;
	
	@JsonProperty("reservation-pickup")
	private String reservationPickup;

	
	@JsonProperty("reservation-drop")
	private String reservationDrop;
	
	@JsonProperty("reservation-status")
	private String reservationStatus;
	

	public ReservationDTO (Reservation res) {
		this.rid = res.getId();
		this.customerName = res.getCustomer().getName();
		this.vehicle = new VehicleDTO(res.getVehicle());
		this.reservationBegin = ISO8601DATEFORMAT.format(res.getZeitraumBeginn());
		this.reservationEnde = ISO8601DATEFORMAT.format(res.getZeitraumEnde());
		this.reservationPickup = res.getlokationBeginn().toString();
		this.reservationDrop = res.getlokationEnde().toString();
		this.reservationStatus = res.getStatus().name();
	}
	
	public Optional <Reservation> create(){
		return Optional.empty();
	}
		
}
