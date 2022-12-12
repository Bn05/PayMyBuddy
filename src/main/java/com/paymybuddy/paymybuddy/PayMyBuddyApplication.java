package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.configuration.SpringSecurityConfig;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    SpringSecurityConfig springSecurityConfig;

    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // TODO :  Wallet int -> double ou float
        // TODO : Ajouter commission de 5%

        // TODO: erreur si mauvaise information page d'inscription & page modification profil

        // TODO: page home
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
}
