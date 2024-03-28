package com.elotech.process.crud.model.repository;

import com.elotech.process.crud.model.domain.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByNameContains(String name, Pageable pageable);
}
