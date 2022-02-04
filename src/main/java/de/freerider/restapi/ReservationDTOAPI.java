package de.freerider.restapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.freerider.restapi.dto.ReservationDTO;

@RequestMapping("/api/v1/reservations")//endpoint url
public interface ReservationDTOAPI {
	
	@RequestMapping(method=RequestMethod.GET, value="", produces= {"application/json"})
	ResponseEntity<List<ReservationDTO>> getReservations();
	
	@RequestMapping(method=RequestMethod.GET, value="{id}", produces= {"application/json"})
	ResponseEntity<ReservationDTO> getReservations(@PathVariable("id") long id);
	
	@RequestMapping(method=RequestMethod.POST, value="")
	ResponseEntity<List<ReservationDTO>> postReservations( @RequestBody List<ReservationDTO> dtos );//list of json dtos
	
	@RequestMapping(method=RequestMethod.PUT, value="")
	ResponseEntity<List<ReservationDTO>> putReservations( @RequestBody List<ReservationDTO> dtos );
	
	@RequestMapping(method=RequestMethod.DELETE, value="{id}")
	ResponseEntity<?> deleteReservations( @PathVariable ("id") long id);
	
	
}
