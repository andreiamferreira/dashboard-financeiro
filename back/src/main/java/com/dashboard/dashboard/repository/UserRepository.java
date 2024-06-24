package com.dashboard.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.dashboard.dashboard.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    Optional<User> findByNameIgnoreCase(String name);

    UserDetails findByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    List<User> findByIsDeletedFalse();

    List<User> findByNameContainingIgnoreCase(String name);
}
