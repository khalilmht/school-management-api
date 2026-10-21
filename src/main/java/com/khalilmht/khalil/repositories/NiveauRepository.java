package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {
    Optional<Niveau> findByCode(String code);
}