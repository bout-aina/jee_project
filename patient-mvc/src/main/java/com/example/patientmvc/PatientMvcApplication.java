package com.example.patientmvc;

import com.example.patientmvc.entities.Medecin;
import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.entities.RendezVous;
import com.example.patientmvc.entities.StatusRDV;
import com.example.patientmvc.repositories.MedecinRepository;
import com.example.patientmvc.repositories.PatientRepository;
import com.example.patientmvc.repositories.RendezVousRepository;
import com.example.patientmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);


    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }
//@Bean
CommandLineRunner commandLineRunner(MedecinRepository medecinRepository) {
    return args -> {
        medecinRepository.save(
                new Medecin(null, "boutaina", "boutynaour8@gmail.com","Medecin Géneral",null));
        medecinRepository.save(
                new Medecin(null, "idriss", "idriss@gmail.com","Medecin Géneral",null));
        medecinRepository.save(
                new Medecin(null, "anasse", "anasse@gmail.com","Medecin Géneral",null));
        medecinRepository.save(
                new Medecin(null, "ismail", "ismail@gmail.com","Medecin Géneral",null));

        medecinRepository.findAll().forEach(p->{
            System.out.println(p.getNom());
        });

    };
}
//@Bean



//@Bean
CommandLineRunner saveUsers(SecurityService securityService)
{
    return args -> {
        securityService.saveNewUser(  "mohamed",  "1234",  "1234");
        securityService.saveNewUser(  "yasmine",  "1234",  "1234");
        securityService.saveNewUser(  "hassan",  "1234",  "1234");
        securityService.saveNewRole(  "USER",  "");
        securityService.saveNewRole(  "ADMIN",  "");
        securityService.addRoleToUser(  "mohamed",  "USER");
        securityService.addRoleToUser(  "mohamed",  "ADMIN");
        securityService.addRoleToUser(  "yasmine",  "USER");
        securityService.addRoleToUser (  "hassan",  "USER");



    };
}



}
