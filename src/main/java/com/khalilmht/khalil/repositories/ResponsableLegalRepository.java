package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.ResponsableLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsableLegalRepository extends JpaRepository<ResponsableLegal, Long> {
}