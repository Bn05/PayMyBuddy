package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByFirstName(String firstName);


}
