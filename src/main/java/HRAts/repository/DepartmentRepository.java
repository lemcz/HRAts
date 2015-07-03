package HRAts.repository;

import HRAts.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Department findById(@Param("id") int id);

    Department findByName(@Param("name") Department name);

    Iterable<Department> findByCompany_Id(int id);

    Iterable<Department> findByCompany_IdAndManagerIsNull(@Param("id") int id);
}