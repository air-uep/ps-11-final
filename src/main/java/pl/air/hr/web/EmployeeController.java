package pl.air.hr.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.air.hr.exception.NoDataFoundException;
import pl.air.hr.model.Department;
import pl.air.hr.model.Employee;
import pl.air.hr.model.Position;
import pl.air.hr.repo.DepartmentRepository;
import pl.air.hr.repo.EmployeeRepository;
import pl.air.hr.repo.PositionRepository;

@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {
	
	@Autowired private EmployeeRepository empRepo;
	@Autowired private DepartmentRepository depRepo;
	@Autowired private PositionRepository posRepo;
	
	
	/* -------------------------------------------------------- */
	/* READ */
	
	@GetMapping(value = "/{id}")
	public String displayOne(@PathVariable Long id, Model model) {
		Optional<Employee> opt = empRepo.findById(id);
		
		if (opt.isPresent()) {
			Employee employee = opt.get();
			model.addAttribute("employee", employee);
		}
		else {
			throw new NoDataFoundException("Nie znaleziono pracownika o id = " + id);
		}			
		
		return "employee";
	}
	
	@GetMapping()
	public String displayAll(Model model) {
		List<Employee> all = empRepo.findAll();
		model.addAttribute("employees", all);
		return "employees";
	}
	
	
	/* -------------------------------------------------------- */
	/* CREATE */
	
	@GetMapping(value = "/form")
	public String displayForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		addEmployeeDataToModel(model);
		
		return "employee-form";
	}

	private void addEmployeeDataToModel(Model model) {
		List<Department> departments = depRepo.findAll();
		List<Position> positions = posRepo.findAll();
		model.addAttribute("departments", departments);
		model.addAttribute("positions", positions);		
	}
	
	
	/* -------------------------------------------------------- */
	/* UPDATE */
	
	@GetMapping(value = "/form/{id}")
	public String displayForm(@PathVariable Long id, Model model) {
		Optional<Employee> opt = empRepo.findById(id);
		
		if (opt.isPresent()) {
			Employee employee = opt.get();
			model.addAttribute("employee", employee);
			addEmployeeDataToModel(model);
		}
		else {
			throw new NoDataFoundException("Nie znaleziono pracownika o id = " + id);
		}			

		return "employee-form";
	}

	
	/* -------------------------------------------------------- */
	/* SAVE Form (CREATE or UPDATE) */
	
	@PostMapping(value = "/save")
	public String saveOne(@Valid Employee employee, Errors errors, Model model) {
		if (errors.hasErrors()) {
			addEmployeeDataToModel(model);
			return "employee-form";
		}
			
		empRepo.save(employee);
		
		return "redirect:/employees";
	}
	
	
	/* -------------------------------------------------------- */
	/* DELETE */
	
	@GetMapping(value = "/delete/{id}")
	public String deleteOne(@PathVariable Long id) {
		if (! empRepo.existsById(id))
			throw new NoDataFoundException("Nie znaleziono pracownika o id = " + id);
		
		empRepo.deleteById(id);
		
		return "redirect:/employees";
	}
}






