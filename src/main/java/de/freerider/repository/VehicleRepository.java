package de.freerider.repository;
import org.springframework.stereotype.Repository;
import de.freerider.datamodel.Vehicle;

@Repository
public interface VehicleRepository extends org.springframework.data.repository.CrudRepository<Vehicle, Long>{

}
