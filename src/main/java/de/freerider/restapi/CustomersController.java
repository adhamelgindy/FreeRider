//5.) Entfernen Sie im REST‐Interface in CustomersController.java die Mock‐Daten (class Person{ } usw. ), 
//so dass nur die Implementierungsmethoden aus CustomersAPI.java übrig sind. 
//Fügen Sie eine @Autowired Variable: customerRepository ein und ersetzen Sie die JSON Datenrückgabe 
//in getCustomers() und getCustomer( long id ), so dass Daten aus dem Repository ausgeliefert werden. 

package de.freerider.restapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.freerider.datamodel.Customer;
import de.freerider.repository.CustomerRepository;
import de.freerider.restapi.ServiceController.Person;

//@RestController
public class CustomersController implements CustomersAPI {  //was soll entfernt sein?

	private final ObjectMapper objectMapper;
	//Customer cus = new Customer();
	//private final List<Customer> cus = new ArrayList<Customer>();
	private final HttpServletRequest request;

	public CustomersController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Autowired
	CustomerRepository customerRepository; 

	//	create customers like people from service Controller
	@Override
	public ResponseEntity<List<?>> getCustomers() {


		customerRepository.findAll();
		ResponseEntity<List<?>> re = null;
		System.err.println( request.getMethod() + " " + request.getRequestURI() );   
		try {
			ArrayNode arrayNode = serializeCustomersAsJSON();
			ObjectReader reader = objectMapper.readerFor( new TypeReference<List<ObjectNode>>() { } );
			List<String> list = reader.readValue( arrayNode );
			//
			re = new ResponseEntity<List<?>>( list, HttpStatus.OK );

		} catch( IOException e ) {
			re = new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return re;

	}

	//person arraylist 



	@Override
	public ResponseEntity<ObjectNode> getCustomer(long id) {

		ResponseEntity<ObjectNode> re = null;

		System.err.println( request.getMethod() + " " + request.getRequestURI() );   
		re = new ResponseEntity<ObjectNode>(HttpStatus.NOT_FOUND);

		Optional<Customer> customer = customerRepository.findById(id);


		if(customer.isPresent()) {
			ObjectNode jsonNode = serializeCustomerAsJSON(customer.get());
			//					ObjectReader reader = objectMapper.readerFor( new TypeReference<List<ObjectNode>>() { } );
			//					List<String> list = reader.readValue( jsonNode );
			//}
			re = new ResponseEntity<ObjectNode>( jsonNode, HttpStatus.OK );


		} else{
			re = new ResponseEntity<ObjectNode>(HttpStatus.NOT_FOUND);
		}

		return re;

	}


	private ArrayNode serializeCustomersAsJSON() { //changes the java list to json object
		//
		ArrayNode arrayNode = objectMapper.createArrayNode();
		//
		customerRepository.findAll().forEach(c -> {
			StringBuffer sb = new StringBuffer();
			c.getContacts().forEach(contact -> sb.append(sb.length() == 0 ? "" : "; ").append(contact));
			arrayNode.add(objectMapper.createObjectNode().put("id", c.getId()).put("name", c.getLastName())
					.put("first", c.getFirstName()).put("contacts", sb.toString()));
		});
		return arrayNode;
	}

	private ObjectNode serializeCustomerAsJSON(Customer c) { //changes the java list to json object
		//
		ObjectNode jsonNode = objectMapper.createObjectNode();
		//StringBuffer sb = new StringBuffer();
		//
		//		customer.forEach( c -> {
		StringBuffer sb = new StringBuffer();
		c.getContacts().forEach( contact -> sb.append( sb.length()==0? "" : "; " ).append( contact ) );
		//			((ObjectNode) jsonNode)(
		//				objectMapper.createObjectNode()
		jsonNode.put( "id", c.getId());
		jsonNode.put( "name", c.getLastName());
		jsonNode.put("first", c.getFirstName());
		jsonNode.put( "contacts", sb.toString());
		
		//);
		//		});


		return jsonNode;
	}
	
	@Override
	public ResponseEntity<List<?>> postCustomers(Map<String, Object>[] jsonMap) {
		System.err.println( "POST /customers" );
		if( jsonMap == null ) //map is empty
		    return new ResponseEntity<>( null, HttpStatus.BAD_REQUEST ); //return error
		//
		Map<String, Object> jsonMapData = new HashMap<>();
		
		System.out.println( "[{" );
		for( Map<String, Object> kvpairs : jsonMap ) { //for each map object
		    kvpairs.keySet().forEach( key -> { //for each key
		    Object value = kvpairs.get( key );
		    System.out.println( "  [ " + key + ", " + value + " ]" );
		    jsonMapData.put(key, value); //put key and value paires in map
		    });
		}
		System.out.println( "}]" );
		
		ResponseEntity<List<?>> re = null;
		
		Optional<Customer> customerToSave = accept(jsonMapData);
		
		if(!customerToSave.isEmpty()) { //no customer object
			Customer custObject = customerToSave.get();
			
			if(customerRepository.existsById(custObject.getId())) { //get id if customer object exists
				try {
					ObjectNode arrayNode = serializeCustomerAsJSON(custObject); 
					ObjectReader reader = objectMapper.readerFor(new TypeReference<List<ObjectNode>>() {
					});
					List<String> list = reader.readValue(arrayNode);
					
					re = new ResponseEntity<List<?>>( list, HttpStatus.CONFLICT ); // status 409
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			else {
				customerRepository.save(custObject); //save when no conflicts
			}
		}
		else {
			re = new ResponseEntity<>( null, HttpStatus.CONFLICT ); // status 409
		}
		
		return re;
	}

	@Override
	public ResponseEntity<List<?>> putCustomers(Map<String, Object>[] jsonMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteCustomer(long id) {
		// TODO Auto‐generated method stub
		System.err.println( "DELETE /customers/" + id );
		if(customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			return new ResponseEntity<>( null, HttpStatus.ACCEPTED ); // status 20
		} else {
			//nicht gefunden
			return new ResponseEntity<>( null, HttpStatus.NOT_FOUND );
		}
	}

	@SuppressWarnings("static-access")
	private Optional<Customer> accept( Map<String, Object> kvpairs) { //liefert ein Optional mit einem Customer‐Objekt
		if(kvpairs == null) {
			throw new IllegalArgumentException();
		}
		
		Optional<Customer> customerOptional = null;
		long kvID = 0L;
		if(kvpairs.get("id") != null) { 
			kvID = (long) Long.parseLong( String.valueOf(kvpairs.get("id"))) ;
		}
		// long kvID = (long) Long.parseLong( String.valueOf(kvpairs.get("id"))) ;
		//. die Attribute ‘name‘ und ‘first‘ (name) 
		String kvName = (String) kvpairs.get("name");
		String kvFirst = (String) kvpairs.get("first");
		String kvContacts = (String) kvpairs.get("contacts");
		//new() ein neues Customer‐Objekt 
		//erzeugt und dessen Attribute auf Werte aus kvpairs (aus dem JSON) gesetzt
		Customer customer = new Customer();
		
		if((Long) kvID == null || kvID <= 0L) { //‘id‘ – Wert mit einem Wert > 0
			kvID = Long.valueOf((long) (Math.random() * 10000.0));
			while(customerRepository.existsById(kvID)) {
				kvID = Long.valueOf((long) (Math.random() * 10000.0));
			}
		}
		
		if(kvName == null || kvFirst == null) {
			return customerOptional.empty();
		}
		
		String[] kvContactsArray = null;
		if(kvContacts != null) {
			kvContactsArray = kvContacts.split(";");
		}
		
		customer.setId(kvID);
		customer.setName(kvFirst, kvName);
		if(kvContactsArray != null) {
			for(String contact : kvContactsArray) {
				customer.addContact(contact);
			}
		}
		
		return customerOptional.of(customer);
	}
	
	//	class Customer {
	//		String firstName = "";
	//		String lastName = "";
	//		final List<String> contacts = new ArrayList<String>();
	//		int id = 0;
	//
	//		Customer setName( String firstName, String lastName ) {
	//			this.firstName = firstName;
	//			this.lastName = lastName;
	//			return this;
	//		}
	//
	//		public long getId() {
	//			// TODO Auto-generated method stub
	//			return id;
	//		}
	//
	//		Customer setFirstName( String firstName) {
	//			this.firstName = firstName;
	//
	//			return this;
	//		}
	//
	//		Customer addContact( String contact ) {
	//			this.contacts.add( contact );
	//			return this;
	//		}
	//
	//		Customer setID(int id) {
	//			this.id = id;
	//			return this;
	//		}
	//	}
	//


	//	private final Customer eric = new Customer()
	//			.setName( "Eric", "Meyer" )
	//			.addContact( "eric98@yahoo.com" )
	//			.addContact( "(030) 3945-642298" )
	//			.setID(1);
	//	//
	//	private final Customer anne = new Customer()
	//			.setName( "Anne", "Bayer" )
	//			.addContact( "anne24@yahoo.de" )
	//			.addContact( "(030) 3481-23352" )
	//			.setID(2);
	//	//
	//	private final Customer tim = new Customer()
	//			.setName( "Tim", "Schulz-Mueller" )
	//			.addContact( "tim2346@gmx.de" )
	//			.setID(3);


}
