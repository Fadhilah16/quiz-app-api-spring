package com.quiz.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.app.models.Role;
import com.quiz.app.models.enums.RoleTypes;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{
    public Optional<Role> findByName(RoleTypes name);
}
