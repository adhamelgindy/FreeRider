package de.freerider.restapi.dto;

import de.freerider.datamodel.Vehicle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleDTO {

	/*
	 * internal counter to create unique number for each generated DTO.
	 */
	private static long serialno = 0;
	
	/**
	 * JsonProperty, serial number incremented for each DTO.
	 */
	@JsonProperty("vehicle-id")
	private long vid;
	
	@JsonProperty("vehicle-make")
	private String make;
	
	@JsonProperty("vehicle-model")
	private String model;
	
	@JsonProperty("vehicle-passengers")
	private int passengers;
	
	@JsonProperty("vehicle-power")
	private String power;

	@JsonProperty("vehicle-color")
	private String color;
	
	@JsonProperty("vehicle-images")
	private List<String> imageURL;
	
	public VehicleDTO(Vehicle vehicle) {
		this.vid = vehicle.getId();
		this.make = vehicle.getMake();
		this.model = vehicle.getModel();
		this.passengers = vehicle.getPassengers();
		this.power = vehicle.getPower().name();
		this.color = vehicle.getColor();
		this.imageURL = vehicle.getImgUrl();
	}

	public Optional<Vehicle> create() {
		return Optional.empty();
	}

}
 