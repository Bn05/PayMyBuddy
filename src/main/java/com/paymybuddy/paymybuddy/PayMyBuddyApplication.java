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

        //TODO :  Wallet int -> double ou float

        // TODO: ajout de l'argent depuis la banque // RESTE la logique
        // TODO: transferer de l'argent à la banque // RESTE la logique

        // TODO: gestion ajout contact _deja en contact _pas client  _moi meme

        // TODO: ajout commentaire transaction
        //TODO : Transaction IN et OUT
        // TODO: ajout de la liste de connection

        // TODO: page home
        // TODO: erreur si mauvaise information page d'inscription & page modification profil


        // TODO: test unitaire
        // TODO: test d'integration

        // TODO: javadoc
        // TODO: readme
        // TODO: spotbug
        // TODO: jacoco
        // TODO: pom.xml


        // TODO: gestion de la deconnexion
        // TODO: test end to end
        // TODO : affiner VARCHAR
        // TODO : Creation page Admin // VUe d'ensemble, creation nouvel admin

        // TODO: oath2
    }
}
