package com.elotech.process.crud.model.adapter;

import com.elotech.process.crud.model.dto.PersonDto;
import com.elotech.process.crud.model.domain.entities.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonAdapter implements Adapter<PersonDto, Person> {
    @Override
    public PersonDto toDto(Person person) {
        return new PersonDto(person.getId(), person.getName(), person.getCpf(), person.getBirthDate(), person.getContactList());
    }

    @Override
    public Person toEntity(PersonDto personDto) {
        return new Person(personDto.getId(), personDto.getName(), personDto.getCpf(), personDto.getBirthDate(), personDto.getContactList());
    }
}
