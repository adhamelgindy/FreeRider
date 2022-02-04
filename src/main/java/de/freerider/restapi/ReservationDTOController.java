package de.freerider.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.freerider.repository.ReservationRepository;
import de.freerider.restapi.dto.ReservationDTO;

@RestController
public class ReservationDTOController implements ReservationDTOAPI{
	
	private final HttpServletRequest request;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public ReservationDTOController(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public ResponseEntity<List<ReservationDTO>> getReservations() {
		// TODO Auto-generated method stub
		System.err.println( request.getMethod() + " " + request.getRequestURI());
		
		List<ReservationDTO> response = new ArrayList<ReservationDTO>();
		reservationRepository
		.findAll() //load all objects 
		.forEach( //convert to DTO
				e -> response.add(new ReservationDTO(e)));
		return new ResponseEntity<List<ReservationDTO>>(response, HttpStatus.OK);//save Objs in list
	}

	@Override
	public ResponseEntity<ReservationDTO> getReservations(long id) {
		// TODO Auto-generated method stub
		System.err.println( request.getMethod() + " " + request.getRequestURI());
		
		ResponseEntity<ReservationDTO> response = 
				reservationRepository
				.findById(id)
				.map(e -> new ResponseEntity<ReservationDTO>(new ReservationDTO(e), HttpStatus.OK))
				.orElse(new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND));
		return response;
	}


	public ResponseEntity<List<ReservationDTO>> putReservations() {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<?> deleteReservations(long id) {
		System.err.println( request.getMethod() + " " + request.getRequestURI());
		
		if(reservationRepository.existsById(id)) {
			reservationRepository.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<List<ReservationDTO>> postReservations(List<ReservationDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<ReservationDTO>> putReservations(List<ReservationDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
