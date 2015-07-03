package HRAts.repository;

import HRAts.model.Sector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectorRepository extends CrudRepository<Sector, Integer> {
    Sector findById(@Param("id") int id);

    Sector findByName(@Param("name") String name);

    @Query("SELECT s.name FROM Sector s")
    List<Sector> getSector();
}