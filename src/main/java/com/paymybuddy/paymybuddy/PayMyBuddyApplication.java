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

    // TODO: erreur si mauvaise information page d'inscription & page modification profil
    // TODO : erreur info cb et iban

    // TODO : Creation page Admin // VUe d'ensemble, creation nouvel admin

    // TODO: test unitaire
    // TODO: test d'integration

    // TODO: javadoc
    // TODO: readme
    // TODO: spotbug
    // TODO: jacoco
    // TODO: pom.xml
    //TODO : supprimer AUTORWIRED
    //TODO : refactoriser code !!
    //TODO : Actualisation de la base de donnée


    // TODO: gestion de la deconnexion
    // TODO: test end to end
    // TODO : affiner VARCHAR


    // TODO: oath2

}
