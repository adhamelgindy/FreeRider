package de.freerider.repository;
import org.springframework.stereotype.Repository;
import de.freerider.datamodel.Customer;
@Repository
public interface CustomerRepository extends
org.springframework.data.repository.CrudRepository<Customer, Long>
{
//	Hibernate:  
//		create table customer (
//		  id bigint not null,
//		  first_name varchar(255),
//		  name varchar(255),
//		  status integer,
//		  primary key (id)
//		)
}