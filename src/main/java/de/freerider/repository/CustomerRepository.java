package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.freerider.datamodel.Customer;

@Component
public class CustomerRepository implements CrudRepository<Customer, Long> {
	

	private HashMap<Long, Customer> repository = new HashMap<Long, Customer>();

	

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		if(entities == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<S> returnValues = new ArrayList<>();
		for(S currentEntity : entities) {
			returnValues.add(currentEntity);
			save(currentEntity);
		}
		return returnValues;

	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Customer> S save(S entity) {
		
		if(entity != null) {
			 Long id = entity.getId();
			 
			 if(id < 0L) {
					id = Long.valueOf((long) (Math.random() * 10000.0));
					while(repository.containsKey(id)) {
						id = Long.valueOf((long) (Math.random() * 10000.0));
					}
					entity.setId((long) id);
			 }
			 
			if(!repository.containsKey(id)) {

				
				repository.put(id, entity);
			}
			else {
				Customer oldEntity = repository.remove(id);
				repository.put(id, entity);
				return (S) oldEntity;
			}
		}
		else {
			throw new IllegalArgumentException();
		}
		return entity;

	}

	@Override
	public boolean existsById(Long id) {
		if(id == null) {
			throw new IllegalArgumentException();
		}
		return repository.containsKey(id);

	}

	@Override
	public Optional<Customer> findById(Long id) {
		if(id == null) {
			throw new IllegalArgumentException();
		}
		return Optional.ofNullable(repository.get(id));

	}

	@Override
	public Iterable<Customer> findAll() {

		return repository.values();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<Long> ids) {
		if(ids == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Customer> returnValues = new ArrayList<>();
		for(Object currentId : ids) {
			if(currentId != null && repository.get(currentId) != null)
				returnValues.add(repository.get(currentId));
		}
		return returnValues;

	}

	@Override
	public long count() {
		return repository.size();
	}

	@Override
	public void deleteById(Long id) {
		if(id == null) {
			throw new IllegalArgumentException();
		}
		repository.remove(id);


	}

	@Override
	public void delete(Customer entity) {
		// TODO Auto-generated method stub
		if(entity != null) {
			if(entity.getId() <= 0) {
				throw new IllegalArgumentException();
			}
			repository.remove(entity.getId());
		}
		else {
			throw new IllegalArgumentException();
		}


	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		if(ids == null) {
			throw new IllegalArgumentException();
		}
		for(Object currentId : ids) {
			if(currentId != null) {
				repository.remove(currentId);
			}
		}


	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		// TODO Auto-generated method stub
		if(entities == null) {
			throw new IllegalArgumentException();
		}
		for(Customer currentCustomer : entities) {
			if(currentCustomer != null) {
				if(currentCustomer.getId() <= 0) {
					throw new IllegalArgumentException();
				}
				repository.remove(currentCustomer.getId());
			}
			
		}


	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		repository.clear();

	}
	//...

}  
