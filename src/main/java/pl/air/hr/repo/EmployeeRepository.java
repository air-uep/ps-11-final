package pl.air.hr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.air.hr.model.Department;
import pl.air.hr.model.Employee;
import pl.air.hr.model.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findAllByHireDateAfter(LocalDate hireDate);

    List<Employee> findAllBySalaryGreaterThan(BigDecimal salary);

    List<Employee> findAllByDepartment(Department department);

    List<Employee> findAllByPosition(Position position);


    @Query( "select count(e) " +
            "from Employee e " +
            "where e.department = :department "
    )
    long countByDepartment(Department department);

}
