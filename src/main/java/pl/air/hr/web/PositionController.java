package pl.air.hr.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.air.hr.exception.NoDataFoundException;
import pl.air.hr.model.Position;
import pl.air.hr.repo.PositionRepository;

@Controller
@RequestMapping(value = "/positions")
public class PositionController {
	
	@Autowired private PositionRepository posRepo;
	
	/* -------------------------------------------------------- */
	/* READ */
	
	@GetMapping(value = "/{id}")
	public String displayOne(@PathVariable Long id, Model model) {
		Optional<Position> opt = posRepo.findById(id);
		
		if (opt.isPresent()) {
			Position position = opt.get();
			model.addAttribute("position", position);
		}
		else {
			throw new NoDataFoundException("Nie znaleziono stanowiska o id = " + id);
		}
		
		return "position";
	}
	
	@GetMapping()
	public String displayAll(Model model) {
		List<Position> all = posRepo.findAll();
		model.addAttribute("positions", all);
		return "positions";
	}

}
