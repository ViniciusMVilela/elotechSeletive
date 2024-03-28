package com.elotech.process.crud.model.service;

import com.elotech.process.crud.model.adapter.PersonAdapter;
import com.elotech.process.crud.model.dto.PersonDto;
import com.elotech.process.crud.model.domain.entities.Person;
import com.elotech.process.crud.model.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.elotech.process.crud.model.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    PersonRepository repository;

    @Autowired
    PersonAdapter adapter;

    public PersonDto findById(Long id) {
        return this.adapter.toDto(this.repository.findById(id).orElseThrow(NotFoundException::new));
    }

    public Page<PersonDto> findByNameContains(String name, Pageable pageable) {
        return this.repository.findByNameContains(name, pageable).map(person -> this.adapter.toDto(person));
    }

    public Person savePerson(Person person) {
        return this.repository.save(person);
    }

    public Person craetePerson(PersonDto newPerson) {
            Person person = this.adapter.toEntity(newPerson);
            return this.savePerson(person);

    }

    public Person update(Long id, Person updatePerson) {
        Person person = this.repository.findById(id).orElseThrow(NotFoundException::new);

        person.setName(updatePerson.getName());
        person.setCpf(updatePerson.getCpf());
        person.setBirthDate(updatePerson.getBirthDate());
        person.getContactList().clear();
        person.getContactList().addAll(updatePerson.getContactList());

        return this.savePerson(person);
    }

    public void deleteById(Long id){
            this.repository.deleteById(id);
    }
}
