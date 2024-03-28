package com.elotech.process.crud.model.adapter;

import com.elotech.process.crud.model.domain.entities.Contact;
import com.elotech.process.crud.model.domain.entities.Person;
import com.elotech.process.crud.model.dto.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonAdapterTest {

    @InjectMocks
    PersonAdapter personAdapter;
    private Person personSetUp;
    private PersonDto personDtoSetUp;

    @BeforeEach
    public void setUp() {
        personSetUp = new Person(1L, "Vinicius", "07454185975", LocalDate.of(2023, 10, 10),
                List.of(new Contact(1L, "vinicius contact", "44998007032", "vinicius@gmail.com")));

        personDtoSetUp = new PersonDto(2L, "Marcos", "82861900920", LocalDate.of(2023, 12, 10),
                List.of(new Contact(2L, "marcos contact", "44998007033", "marcos@gmail.com")));
    }

    @Test
    @DisplayName("Should convert person to personDto")
    void toDtoTest() {
        PersonDto personDto = personAdapter.toDto(personSetUp);

        assertThat(personDto.getId()).isEqualTo(1L);
        assertThat(personDto.getName()).isEqualTo("Vinicius");
    }

    @Test
    @DisplayName("Should convert personDto to person")
    void toEntityTest() {
        Person person = personAdapter.toEntity(personDtoSetUp);

        assertThat(person.getId()).isEqualTo(2L);
        assertThat(person.getName()).isEqualTo("Marcos");
    }
}
