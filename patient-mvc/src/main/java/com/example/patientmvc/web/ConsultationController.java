package com.example.patientmvc.web;

import com.example.patientmvc.entities.Consultation;
import com.example.patientmvc.entities.Medecin;
import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.entities.RendezVous;
import com.example.patientmvc.repositories.ConsultationRepository;
import com.example.patientmvc.repositories.MedecinRepository;
import com.example.patientmvc.repositories.RendezVousRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ConsultationController {
    //dropdown list
    static  List<String> designationList = null;
    static {
        designationList = new ArrayList<>();
        designationList.add("Medecin géneral");
        designationList.add("Medecin ophtamologue");
        designationList.add("Medecin génichologue");
        designationList.add("Medecin cardiologue");
    }

    private ConsultationRepository consultationRepository;
    private RendezVousRepository rendezVousRepository;

    @GetMapping(path = "/user/indexConsultation")

    public String consultations(ModelMap model,
                           @RequestParam(name = "page", defaultValue = "0")
                                   int page,
                           @RequestParam(name = "size", defaultValue = "5")
                                   int size,
                           @RequestParam(name = "keyword", defaultValue = "")
                                   String keyword) {
        Page<Consultation> pageConsultations = consultationRepository.findByRapportContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listConsultations", pageConsultations.getContent());
        model.addAttribute("pages", new int[pageConsultations.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "Consultation/consultations";
    }

    @GetMapping("/admin/deleteConsultation")
    public String delete(Long id) {
        consultationRepository.deleteById(id);
        return "redirect:/user/indexConsultation";

    }





    @GetMapping("/user/consultations")
    @ResponseBody
    public List<Consultation> listConsultations() {

        return consultationRepository.findAll();
    }

    @GetMapping("/admin/formConsultations")
    public String formConsultations(Model model,
                                    Long id) {

        RendezVous rendezVous =rendezVousRepository.findById(id).orElse(null) ;
        if(rendezVous==null) throw  new RuntimeException("Rendez vous  introuvable");
        model.addAttribute("rendezvous",rendezVous);
        model.addAttribute("rendezvous", new RendezVous());

        return "Consultation/formConsultations";

    }

    @PostMapping(path = "/admin/saveConsultation")
    public String save(Model model,
                       @Valid Consultation consultation,
                       BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) return "formConsultations";
        consultationRepository.save(consultation);

        return "redirect:/user/indexConsultation";

    }

    @GetMapping("/admin/editConsultation")
    public String editConsultation(Model model,
                              Long id
    ) {
        Consultation consultation =consultationRepository.findById(id).orElse(null) ;
        if(consultation==null) throw  new RuntimeException("Consultation introuvable");
        model.addAttribute("consultation",consultation);


        return "Consultation/editConsultation";

    }

}
