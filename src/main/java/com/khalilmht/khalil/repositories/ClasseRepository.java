package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    boolean existsByCode(String code);
}