package com.example.patientmvc.web;


import com.example.patientmvc.entities.Medecin;
import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.entities.RendezVous;

import com.example.patientmvc.entities.StatusRDV;
import com.example.patientmvc.repositories.MedecinRepository;
import com.example.patientmvc.repositories.PatientRepository;
import com.example.patientmvc.repositories.RendezVousRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;

@Controller
@AllArgsConstructor
public class RendezVousController {
    static EnumSet<StatusRDV> designationList = null;
    static {
        designationList = EnumSet.allOf(StatusRDV.class);

    }

    private RendezVousRepository rendezVousRepository;
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;


    @GetMapping(path = "/user/indexRDV")

    public String rendezvouse(ModelMap model,
                           @RequestParam(name = "page", defaultValue = "0")
                                   int page,
                           @RequestParam(name = "size", defaultValue = "5")
                                   int size,
                           @RequestParam(name = "keyword", defaultValue = "")
                                     String keyword) {

        Page<RendezVous> pageRDV = rendezVousRepository.findByNumRdvContains(keyword,PageRequest.of(page, size));
        model.addAttribute("rendezVousList", pageRDV.getContent());
        model.addAttribute("pages", new int[pageRDV.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "RDV/rendezvouse";
    }

    @GetMapping("/admin/deleteRDV")
    public String delete(Long id) {
        rendezVousRepository.deleteById(id);
        return "redirect:/user/indexRDV";

    }


    @GetMapping("/user/rendezvouse")
    @ResponseBody
    public List<RendezVous> rendezVousList() {

        return rendezVousRepository.findAll();
    }

    @GetMapping("/admin/formRDV")
    public String formRDV(Model model) {
        List<Patient> patientList = patientRepository.findAll();
        List<Medecin> medecinList = medecinRepository.findAll();
        model.addAttribute("rendezvous", new RendezVous());
        model.addAttribute("designationList",designationList);
        model.addAttribute("patientList",patientList);
        model.addAttribute("medecinList",medecinList);
        return "RDV/formRDV";

    }


    @PostMapping(path = "/admin/saveRDV")
    public String save(Model model,
                       @ModelAttribute("formRDV") RendezVous rendezVous,
                       BindingResult bindingResult
                       ) {
        if (bindingResult.hasErrors()) return "formRDV";
        rendezVousRepository.save(rendezVous);
        return "redirect:/user/indexRDV";

    }

    @GetMapping("/admin/editRDV")
    public String editRDV(Model model,
                              Long id
                              ) {

        RendezVous rendezVous =rendezVousRepository.findById(id).orElse(null) ;
        if(rendezVous==null) throw  new RuntimeException("Rendez vous  introuvable");
        model.addAttribute("rendezvous",rendezVous);
        model.addAttribute("designationList",designationList);



        return "RDV/editRDV";

    }

}