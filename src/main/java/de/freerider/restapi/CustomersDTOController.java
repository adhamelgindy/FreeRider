package de.freerider.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import de.freerider.datamodel.Customer;
import de.freerider.repository.CustomerRepository;
import de.freerider.restapi.dto.CustomerDTO;

import java.util.Optional;

@RestController
class CustomersDTOController implements CustomersDTOAPI {

	//
	@Autowired
	private ApplicationContext context;

	@Autowired
	private CustomerRepository customerRepository;
	//
	//
	private final HttpServletRequest request;

	/**
	 * Constructor.
	 * 
	 * @param objectMapper entry point to JSON tree for the Jackson library
	 * @param request      HTTP request object
	 */
	public CustomersDTOController(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public ResponseEntity<CustomerDTO> getCustomer(long id) {
		System.err.println(request.getMethod() + " " + request.getRequestURI());
		Optional<Customer> oc = customerRepository.findById(id);
		if (oc.isEmpty()) {
			return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_FOUND);
		}

		Customer c = oc.get();

		CustomerDTO dto = new CustomerDTO(c);

		return new ResponseEntity<CustomerDTO>(dto, HttpStatus.OK);
	}

	/**
	 * GET /people
	 * 
	 * Return JSON Array of people (compact).
	 * 
	 * @return JSON Array of people
	 */
	@Override
	public ResponseEntity<List<CustomerDTO>> getCustomers() {
		//
		System.err.println(request.getMethod() + " " + request.getRequestURI());
		ArrayList<CustomerDTO> list = new ArrayList<>();
		customerRepository.findAll().forEach(c -> {
			list.add(new CustomerDTO(c));
		});
		return new ResponseEntity<List<CustomerDTO>>(list, HttpStatus.OK);
	}

	/*private Optional<Customer> accept(CustomerDTO dto) {

		Long id = 0l;
		if (!kvpairs.containsKey("id")) {
			for (Customer c : customerRepository.findAll()) {
				id += c.getId();
			}
		} else {
			id = Long.valueOf((int)kvpairs.get("id"));
		}

		if (!kvpairs.containsKey("name") || !kvpairs.containsKey("first") || id <= 0) {
			return Optional.empty();
		}

		Customer c1 = new Customer().setId(id).setName((String) kvpairs.get("first"), (String) kvpairs.get("name"));
		String contacts = (String) kvpairs.get("contacts");
		if (contacts != null) {
			String[] parts = contacts.split(";");
			for (String part : parts) {
				c1.addContact(part);
			}
		}

		return Optional.of(c1);
	}*/

	@Override
	public ResponseEntity<List<CustomerDTO>> postCustomers(List<CustomerDTO> dtos) {
		System.err.println("POST /customers");
		System.out.println("post aufgerufen");
		if (dtos == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		for(CustomerDTO dto : dtos) {
			Optional<Customer> o_customer = dto.create();
			if(o_customer.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			Customer customer = o_customer.get();
			if(customerRepository.existsById(customer.getId())) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			customerRepository.save(customer);
		}
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<CustomerDTO>> putCustomers(List<CustomerDTO> dtos) {
		
		// TODO Auto-generated method stub
		System.err.println(request.getMethod() + " "+request.getRequestURI());
		ArrayList<CustomerDTO> rejected = new ArrayList<CustomerDTO>();
		dtos.stream().forEach(dto -> {
			dto.print();
			
			Optional<Customer> customerOpt = dto.create();
			if(customerOpt.isPresent()) {
				if(customerRepository.existsById(customerOpt.get().getId())) {
					CustomerDTO.print(customerOpt);
					customerRepository.deleteById(customerOpt.get().getId());
					customerRepository.save(customerOpt.get());
				}else {
					rejected.add(dto);
				}
				
			} else {
				rejected.add(dto);
			}
		});
		return new ResponseEntity<>(rejected, rejected.size()==0? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> deleteCustomer(long id) {
		System.out.println("delete aufgerufen");
		if (!customerRepository.existsById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		customerRepository.deleteById(id);
		System.err.println("DELETE /customers/" + id);
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}
}
