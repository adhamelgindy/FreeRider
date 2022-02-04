package de.freerider.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.freerider.datamodel.Vehicle;
import de.freerider.repository.VehicleRepository;
import de.freerider.restapi.dto.VehicleDTO;

@RestController
public class VehicleDTOController implements VehicleDTOAPI {
	
	private final HttpServletRequest request;

	//
	@Autowired
	private ApplicationContext context;

	@Autowired
	private VehicleRepository vehicleRepository;
	
	
	/**
	 * Constructor.
	 * 
	 * @param objectMapper entry point to JSON tree for the Jackson library
	 * @param request      HTTP request object
	 */
	public VehicleDTOController(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public ResponseEntity<List<VehicleDTO>> getVehicles() {
		System.err.println(request.getMethod() + " " + request.getRequestURI());
		ArrayList<VehicleDTO> list = new ArrayList<>();
		vehicleRepository.findAll().forEach(v -> {
			list.add(new VehicleDTO(v));
		});
		return new ResponseEntity<List<VehicleDTO>>(list, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<VehicleDTO> getVehicle(long id) {
		System.err.println(request.getMethod() + " " + request.getRequestURI());
		
		ResponseEntity<VehicleDTO> ov = vehicleRepository
				.findById(id)
				.map(e -> new ResponseEntity<VehicleDTO>(new VehicleDTO(e), HttpStatus.OK))
				.orElse(new ResponseEntity<VehicleDTO>(HttpStatus.NOT_FOUND));
		return ov;
	}

	@Override
	public ResponseEntity<List<VehicleDTO>> postVehicles(List<VehicleDTO> dtos) {
	
		System.out.println("post aufgerufen");
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<List<VehicleDTO>> putVehicles(List<VehicleDTO> dtos) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<?> deleteVehicle(long id) {
		System.out.println("delete aufgerufen");
		if (!vehicleRepository.existsById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		vehicleRepository.deleteById(id);
		System.err.println("DELETE /customers/" + id);
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}

}
