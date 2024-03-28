package com.elotech.process.crud.controller;

import com.elotech.process.crud.model.dto.PersonDto;
import com.elotech.process.crud.model.domain.entities.Person;
import com.elotech.process.crud.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.*;

@RestController
@RequestMapping("/person")
@CrossOrigin("http://localhost:4200")
public class PersonController {
    @Autowired
    PersonService service;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
        PersonDto person = this.service.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> findByName(@RequestParam(value = "name") String name, Pageable pageable) {
        Page<PersonDto> personDtos = this.service.findByNameContains(name, pageable);
        return new ResponseEntity<>(personDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody @Validated PersonDto person) {
        Person newPerson = this.service.craetePerson(person);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody @Validated Person updatePerson) {
        Person resultUpdatePerson = this.service.update(id, updatePerson);
        return new ResponseEntity<>(resultUpdatePerson, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
