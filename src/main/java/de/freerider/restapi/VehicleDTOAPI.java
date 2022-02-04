package de.freerider.restapi;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.freerider.restapi.dto.VehicleDTO;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


/**
 * Spring Controller interface for /vehicle REST endpoint to access the
 * collection of vehivle resources maintained in a vehicleRepository.
 * 
 * Operations provided by the endpoint:
 * 
 * - GET /Vehicles			- return JSON data for all customer in the repository,
 * 							  status: 200 OK.
 * 
 * - GET /Vehicles/{id}	- return JSON data for Vehicle with id,
 * 							  status: 200 OK, 404 not found.
 * 
 * - POST /Vehicles		- create new objects in the repository from JSON objects
 * 							  passed with the request,
 * 							  status: 201 created, 409 conflict, 400 bad request.
 * 
 * - PUT /Vehicles			- updated existing objects in the repository from JSON
 * 							  objects passed with the request,
 * 							  status: 202 accepted, 404 not found, 400 bad request.
 * 
 * - DELETE /Vehicles/{id}	- delete Vehicle with id,
 * 							  status: 202 accepted, 404 not found, 400 bad request.
 * 
 * @author sgra64
 *
 */

/*
 * Refer to endpoint URL from swagger.properties, replaces: "/api/v1/Vehicle"
 */
@RequestMapping( "${app.api.endpoints.vehicles}" )

public interface VehicleDTOAPI {


	/**
	 * GET /vehicles
	 * 
	 * @return JSON Array with vehicles (compact).
	 */

	/*
	 * Swagger API doc annotations:
	 */
//	@Operation(
//		summary = "Return all vehicles from repository.",		// appears as text in API short-list
//		description = "Return all vehicles from repository.",	// appears as text inside API
//		tags={ "vehicles-dto-controller" }	// appears in swagger-ui URL: http://localhost:8080/swagger-ui/index.html#/vehicles-controller
//	)
//	@ApiResponses( value = {	// also auto-derived by Swagger
//		@ApiResponse( responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
//		@ApiResponse( responseCode = "401", description = "Unauthorized"),
//		@ApiResponse( responseCode = "403", description = "Forbidden"),
//		// to remove "404" from docs, set SwaggerConfig::Docket.useDefaultResponseMessages(true) // ->false
////		@ApiResponse( responseCode = "404", description = "Not Found")
//	})

	/*
	 * Spring REST Controller annotation:
	 */
	@RequestMapping(
		method = RequestMethod.GET,
		value = "",	// relative to interface @RequestMapping
		produces = { "application/json" }
	)
	//
	ResponseEntity<List<VehicleDTO>> getVehicles();


	/**
	 * GET /Vehicles/{id}
	 * 
	 * @return JSON Array with Vehicles (compact).
	 */

	/*
	 * Swagger API doc annotations:
	 */
	@Operation(
		summary = "Return vehicle with {id} from repository.",
		description = "Return Vehicle with {id} from repository.",
		tags={ "Vehicles-dto-controller" }
	)

	/*
	 * Spring REST Controller annotation:
	 */
	@RequestMapping(
		method=RequestMethod.GET,
		value="{id}",	// relative to interface @RequestMapping
		produces={ "application/json" }
	)
	//
	ResponseEntity<VehicleDTO> getVehicle(
		@PathVariable("id")
		@ApiParam(value = "Vehicle id", required = true)
		long id
	);


	/**
	 * POST /Vehicles
	 * 
	 * Add new Vehicles from JSON data passed as array of JSON objects in the request body.
	 * Multiple Vehicles can be posted with multiple JSON objects from the same request.
	 * Id's are assigned, if id-attributes are missing or are empty in JSON data.
	 * 
	 * JSON data containing id of objects that are already present are rejected. Rejected
	 * objects are returned in the response with error 409 (conflict).
	 * 
	 * Status 201 (created) is returned with empty array of conflicts when all objects were
	 * accepted. Partial acceptance of objects from the request is possible, but error 409 is
	 * returned with the array of rejected objects.
	 * 
	 * @param jsonMap array of maps with raw JSON {@code <key,obj>}-data.
	 * @return JSON array with the rejected JSON objects, empty array [] if all objects were accepted.
	 */

	/*
	 * Swagger API doc annotations:
	 */
	@Operation(
		summary = "Add new Vehicles to repository.",
		description = "Add new Vehicles to repository.",
		tags={ "Vehicles-dto-controller" }
	)

	/*
	 * Spring REST Controller annotation:
	 */
	@RequestMapping(
		method = RequestMethod.POST,
		value = ""	// relative to interface @RequestMapping
	)
	//
	public ResponseEntity<List<VehicleDTO>> postVehicles( @RequestBody List<VehicleDTO> dtos );


	/**
	 * PUT /Vehicles
	 * 
	 * Update existing Vehicles from JSON data passed as array of JSON objects in the request body.
	 * Multiple Vehicles can be updated from multiple JSON objects from the same request.
	 * 
	 * JSON data missing id or with id that are not found are rejected. Rejected JSON objects
	 * are returned in the response with error 404 (not found).
	 * 
	 * Status 202 (accepted) is returned with empty array of conflicts when all updates could be
	 * performed. Partial acceptance of updates is possible for entire objects only (not single
	 * attributes). Error 409 (conflict) is returned for errors other than an object (id) was not
	 * found along with the array of rejected objects.
	 * 
	 * @param jsonMap array of maps with raw JSON {@code <key,obj>}-data.
	 * @return JSON array with the rejected JSON objects, empty array [] if all updates were accepted.
	 */

	/*
	 * Swagger API doc annotations:
	 */
	@Operation(
		summary = "Update existing Vehicles in repository.",
		description = "Update existing Vehicles in repository.",
		tags={ "Vehicles-dto-controller" }
	)

	/*
	 * Spring REST Controller annotation:
	 */
	@RequestMapping(
		method = RequestMethod.PUT,
		value = ""	// relative to interface @RequestMapping
	)
	//
	public ResponseEntity<List<VehicleDTO>> putVehicles( @RequestBody List<VehicleDTO> dtos );


	/**
	 * DELETE /Vehicles/{id}
	 * 
	 * Delete existing Vehicle by its id. A missing id or an id that was not found
	 * returns error 404 (not found).
	 * 
	 * Status 202 (accepted) is returned with successful completion of the operation.
	 * 
	 * @param id id of object to delete.
	 * @return status code: 202 (accepted), 404 (not found).
	 */

	/*
	 * Swagger API doc annotations:
	 */
	@Operation(
		summary = "Delete Vehicle by its id from repository.",
		description = "Delete Vehicle by its id from repository.",
		tags={ "Vehicles-dto-controller" }
	)

	/*
	 * Spring REST Controller annotation:
	 */
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = "{id}"	// relative to interface @RequestMapping
	)
	//
	public ResponseEntity<?> deleteVehicle( @PathVariable("id") long id );


	/*
	 * Alternative using DTO serialization.
	 */
//	@RequestMapping( method = RequestMethod.POST, value = "" )
//	public ResponseEntity<List<?>> postVehicles( @RequestBody VehicleJsonDTO[] dtos );

}
