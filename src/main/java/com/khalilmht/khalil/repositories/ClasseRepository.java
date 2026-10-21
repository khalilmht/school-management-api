package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    boolean existsByCode(String code);

    Optional<Classe> findByCode(String code);

    List<Classe> findByNiveauId(Long niveauId);
}