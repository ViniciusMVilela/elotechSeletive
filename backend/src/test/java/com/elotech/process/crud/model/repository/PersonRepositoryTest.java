package com.elotech.process.crud.model.repository;

import com.elotech.process.crud.model.domain.entities.Contact;
import com.elotech.process.crud.model.domain.entities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@Profile("test")
@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    @Autowired
     PersonRepository personRepository;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person(1L, "Vinicius", "82861900920",
                LocalDate.of(2023, 10, 10),
                List.of(new Contact(1L, "Vinicius contact", "44998007032", "vinicius@gmail.com")));
    }

    @Test
    @DisplayName("Should get Person filter by name from database")
    void findByNameContains() {

        this.personRepository.save(person);

        String searchName = "Vini";
        Pageable pageable = Pageable.unpaged();
        Page<Person> expectedPage = personRepository.findByNameContains(searchName, pageable);

        assertThat(expectedPage.isEmpty()).isFalse();
        assertThat(expectedPage.getTotalElements()).isEqualTo(1);
        assertThat(expectedPage).extracting(Person::getName).hasSize(1).contains("Vinicius");
        assertThat(expectedPage).extracting(Person::getCpf).hasSize(1).contains("82861900920");
    }

    @Test
    @DisplayName("Should get empty page")
    void findByNameContainsEmptyPage() {

        String searchName = "Vini";
        Pageable pageable = Pageable.unpaged();
        Page<Person> expectedPage = personRepository.findByNameContains(searchName, pageable);

        assertThat(expectedPage.isEmpty()).isTrue();
    }
}
