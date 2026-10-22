package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {
    boolean existsByCode(String code);
}