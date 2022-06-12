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
import pl.air.hr.repo.DepartmentRepository;
import pl.air.hr.repo.EmployeeRepository;

@Controller
@RequestMapping(value = "/departments")
public class DepartmentController {
	
	@Autowired private DepartmentRepository depRepo;
	@Autowired private EmployeeRepository empRepo;
	
	
	/* -------------------------------------------------------- */
	/* READ */
	
	@GetMapping(value = "/{id}")
	public String displayOne(@PathVariable Long id, Model model) {
		Optional<Department> opt = depRepo.findById(id);
		
		if (opt.isPresent()) {
			Department department = opt.get();
			model.addAttribute("department", department);
			
			List<Employee> employees = empRepo.findAllByDepartment(department);
			model.addAttribute("employees", employees);
		}
		else {
			throw new NoDataFoundException("Nie znaleziono działu o id = " + id);
		}			
		
		return "department";
	}
	
	@GetMapping()
	public String displayAll(Model model) {
		List<Department> all = depRepo.findAll();
		model.addAttribute("departments", all);
		return "departments";
	}
	
	
	/* -------------------------------------------------------- */
	/* CREATE */
	
	@GetMapping(value = "/form")
	public String displayForm(Model model) {
		Department department = new Department();
		model.addAttribute("department", department);
		return "department-form";
	}

	
	/* -------------------------------------------------------- */
	/* UPDATE */
	
	@GetMapping(value = "/form/{id}")
	public String displayForm(@PathVariable Long id, Model model) {
		Optional<Department> opt = depRepo.findById(id);
		
		if (opt.isPresent()) {
			Department department = opt.get();
			model.addAttribute("department", department);
		}
		else {
			throw new NoDataFoundException("Nie znaleziono działu o id = " + id);
		}			

		return "department-form";
	}

	
	/* -------------------------------------------------------- */
	/* SAVE Form (CREATE or UPDATE) */
	
	@PostMapping(value = "/save")
	public String saveOne(@Valid Department department, Errors errors) {
		if (errors.hasErrors())
			return "department-form";
			
		depRepo.save(department);
		
		return "redirect:/departments";
	}
	
	
	/* -------------------------------------------------------- */
	/* DELETE */
	
	@GetMapping(value = "/delete/{id}")
	public String deleteOne(@PathVariable Long id) {
		if (! depRepo.existsById(id))
			throw new NoDataFoundException("Nie znaleziono działu o id = " + id);
		
		Department department = new Department();
		department.setId(id);
		long empCount = empRepo.countByDepartment(department);
		if (empCount == 0)
			depRepo.deleteById(id);
		
		return "redirect:/departments";
	}
}






