package com.example.patientmvc.web;

import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/user/index")

    public String patients(ModelMap model,
                           @RequestParam(name = "page", defaultValue = "0")
                                   int page,
                           @RequestParam(name = "size", defaultValue = "5")
                                   int size,
                           @RequestParam(name = "keyword", defaultValue = "")
                                   String keyword) {
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "Patient/patients";
    }

    @GetMapping("/admin/delete")
    public String delete(Long id) {
        patientRepository.deleteById(id);
        return "redirect:/user/index";

    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }



    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> lisPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model) {
        model.addAttribute("patient", new Patient());
        return "Patient/formPatients";

    }

    @PostMapping(path = "/admin/save")
    public String save(Model model,
                       @Valid Patient patient,
                       BindingResult bindingResult
                       ) {
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index";

    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,
                              Long id
                              ) {
        Patient patient =patientRepository.findById(id).orElse(null) ;
        if(patient==null) throw  new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);

        return "Patient/editPatient";

    }

}