package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    boolean existsByCode(String code);
}