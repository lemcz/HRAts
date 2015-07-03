package HRAts.repository;

import HRAts.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    Company findById(@Param("id") int id);

}