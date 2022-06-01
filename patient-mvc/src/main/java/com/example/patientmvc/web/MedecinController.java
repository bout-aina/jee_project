package com.example.patientmvc.web;

import com.example.patientmvc.entities.Medecin;

import com.example.patientmvc.repositories.MedecinRepository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Controller
@AllArgsConstructor
public class MedecinController {
    //dropdown list
    static  List<String> designationList = null;
    static {
        designationList = new ArrayList<>();
        designationList.add("Medecin géneral");
        designationList.add("Medecin ophtamologue");
        designationList.add("Medecin génichologue");
        designationList.add("Medecin cardiologue");
    }

    private MedecinRepository medecinRepository;

    @GetMapping(path = "/user/indexMedecin")

    public String medecins(ModelMap model,
                           @RequestParam(name = "page", defaultValue = "0")
                                   int page,
                           @RequestParam(name = "size", defaultValue = "5")
                                   int size,
                           @RequestParam(name = "keyword", defaultValue = "")
                                   String keyword) {
        Page<Medecin> pageMedecins = medecinRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listMedecins", pageMedecins.getContent());
        model.addAttribute("pages", new int[pageMedecins.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "Medecin/medecins";
    }

    @GetMapping("/admin/deleteMedecin")
    public String delete(Long id) {
        medecinRepository.deleteById(id);
        return "redirect:/user/indexMedecin";

    }





    @GetMapping("/user/medecins")
    @ResponseBody
    public List<Medecin> lisMedecins() {

        return medecinRepository.findAll();
    }

    @GetMapping("/admin/formMedecins")
    public String formMedecins(Model model) {
        model.addAttribute("medecin", new Medecin());
        model.addAttribute("designationList",designationList);
        return "Medecin/formMedecins";

    }

    @PostMapping(path = "/admin/saveMedecin")
    public String save(Model model,
                       @ModelAttribute("formMedecin") Medecin medecin,
                       BindingResult bindingResult

    ) {

        if (bindingResult.hasErrors()) return "formMedecins";
        medecinRepository.save(medecin);

        return "redirect:/user/indexMedecin";

    }

    @GetMapping("/admin/editMedecin")
    public String editMedecin(Model model,
                              Long id
    ) {
        Medecin medecin =medecinRepository.findById(id).orElse(null) ;
        if(medecin==null) throw  new RuntimeException("Medecin introuvable");
        model.addAttribute("medecin",medecin);
        model.addAttribute("designationList",designationList);

        return "Medecin/editMedecin";

    }

}
