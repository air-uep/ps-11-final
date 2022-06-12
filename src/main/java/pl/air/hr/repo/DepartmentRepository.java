package pl.air.hr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.air.hr.model.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

}
