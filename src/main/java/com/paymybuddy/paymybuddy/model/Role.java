package com.paymybuddy.paymybuddy.model;


import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "role")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "role_id")
    private int roleId;

   @Column(name = "authorisation", unique = true)
   private String authorisation;

   public Role() {

   }

   public int getRoleId() {
      return roleId;
   }

   public void setRoleId(int roleId) {
      this.roleId = roleId;
   }

   public String getAuthorisation() {
      return authorisation;
   }

   public void setAuthorisation(String authorisation) {
      this.authorisation = authorisation;
   }
}
