package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.configuration.SpringSecurityConfig;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PayMyBuddyApplication {


    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    // TODO : Gerer la modification de mot de passe
    // TODO : date d'anniversaire futur
    // TODO : Creation page Admin // VUe d'ensemble, creation nouvel admin

    // TODO : test unitaire
    // TODO : test d'integration

    // TODO : readme
    // TODO : diagramme uml

    // TODO : refactoriser code !!
    // TODO : javadoc
    // TODO : supprimer AUTORWIRED

    // TODO: spotbug
    // TODO: jacoco
    // TODO: pom.xml


    // TODO : gestion de la deconnexion
    // TODO : test end to end
    // TODO : affiner VARCHAR


    // TODO : oath2

}
