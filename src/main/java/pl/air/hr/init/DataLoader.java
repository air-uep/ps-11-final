package pl.air.hr.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.air.hr.model.Department;
import pl.air.hr.model.Employee;
import pl.air.hr.model.Position;
import pl.air.hr.repo.DepartmentRepository;
import pl.air.hr.repo.EmployeeRepository;
import pl.air.hr.repo.PositionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class DataLoader {

	@Autowired private DepartmentRepository depRepo;
	@Autowired private EmployeeRepository empRepo;
	@Autowired private PositionRepository posRepo;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	@Transactional
	public void insertData() {
		if (! Objects.equals(ddlAuto, "create")) {
			return;
		}

		/* działy */
		Department marketing =  createDepartment("Marketing",  "Pokój 2.3");
		Department sales =      createDepartment("Sprzedaż",   "Budynek A");
		Department accounting = createDepartment("Księgowość", "Budynek B, piętro 2");
		
		/* stanowiska */
		Position manager =    createPosition("Kierownik");
		Position expert =     createPosition("Ekspert");
		Position specialist = createPosition("Specjalista");
		
		/* pracownicy */
		Employee zk = createEmployee("Zofia",   "Kowalska",  "2019-02-01", "6900", marketing, manager);
		Employee jn = createEmployee("Jan",     "Nowak",     "2009-07-15", "4900", marketing, expert);
		Employee uk = createEmployee("Urszula", "Kowalczyk", "2005-01-01", "3500", marketing, specialist);
		Employee ap = createEmployee("Anna",    "Petros",    "2017-08-20", "2900", marketing, specialist);

		Employee em = createEmployee("Ewa",      "Mucha",    "1997-01-01", "9200", sales,     manager);
		Employee js = createEmployee("Jacek",    "Szymczak", "1999-06-01", "7200", sales,     expert);
		Employee wt = createEmployee("Wojciech", "Trzaska",  "2012-10-01", "4500", sales,     expert);
		Employee mk = createEmployee("Marek",    "Kania",    "2015-05-01", "2900", sales,     specialist);
		
		Employee md = createEmployee("Monika",   "Daszyńska", "2001-12-01", "8800", accounting, manager);
		Employee wf = createEmployee("Weronika", "Filipiak",  "2008-09-01", "4900", accounting, specialist);
		Employee kt = createEmployee("Kornelia", "Trafas",    "2011-04-01", "5100", accounting, specialist);
		
		// depRepo.save(marketing);
		// depRepo.save(sales);
		// depRepo.save(accounting);		
		depRepo.saveAll(
				List.of(marketing, sales, accounting)
		);
		
		posRepo.saveAll(
				List.of(manager, expert, specialist)
		);
		
		empRepo.saveAll(
				List.of(zk, jn, uk, ap, em, js, wt, mk, md, wf, kt)
		);

	}

	private Employee createEmployee(String firstName, String lastName, String hireDate, String salary, Department department, Position position) {
		Employee one = new Employee();
		one.setFirstName(firstName);
		one.setLastName(lastName);
		one.setHireDate(LocalDate.parse(hireDate));
		one.setSalary(new BigDecimal(salary));
		one.setDepartment(department);
		one.setPosition(position);
		return one;
	}

	private Position createPosition(String name) {
		Position one = new Position();
		one.setName(name);
		return one;
	}

	private Department createDepartment(String name, String location) {
		Department one = new Department();
		one.setName(name);
		one.setLocation(location);
		return one;
	}

}
