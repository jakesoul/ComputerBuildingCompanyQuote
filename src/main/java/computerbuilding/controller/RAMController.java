package computerbuilding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import computerbuilding.beans.RAM;
import computerbuilding.service.RAMServiceInterface;

@Controller
public class RAMController {
	@Autowired
	RAMServiceInterface rAMService;

	@GetMapping("/ram")
	public String viewRAMPage(Model model) {
		return findPaginated(1, "name", "asc", model);
	}

	@GetMapping("/ram/add_ram")
	public String addRAM(Model model) {
		RAM rAM  = new RAM();
		model.addAttribute("rAM", rAM);
		return "new_ram";
	}

	@PostMapping("/ram/update_ram")
	public String updateRAM(@ModelAttribute("rAM") RAM rAM) {
		rAMService.updateRAM(rAM);
		return "redirect:/ram";
	}

	@GetMapping("/ram/edit_ram/{id}")
	public String editRAM(@PathVariable(value="id") long id, Model model) {
		RAM rAM = rAMService.getRAMById(id);
		model.addAttribute("rAM", rAM);
		return "edit_ram";
	}

	@GetMapping("/ram/delete_ram/{id}")
	public String deleteRAM(@PathVariable(value="id") long id) {
		this.rAMService.deleteRAMById(id);
		return "redirect:/ram";
	}

	@GetMapping("/ram/page/{pageNumber}")
	public String findPaginated(@PathVariable(value="pageNumber") int pageNumber, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
		int pageSize = 5;

		Page<RAM> rAMPage = rAMService.findPaginated(pageNumber, pageSize, sortField, sortDirection);
		List<RAM> rAM = rAMPage.getContent();

		model.addAttribute("rAMPage", pageNumber);
		model.addAttribute("totalRAMPages", rAMPage.getTotalPages());
		model.addAttribute("totalRAMItems", rAMPage.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

		model.addAttribute("rAM", rAM);
		return "ram";
	}
}
