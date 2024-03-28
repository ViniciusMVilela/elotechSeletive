package com.elotech.process.crud.controller;

import com.elotech.process.crud.model.adapter.PersonAdapter;
import com.elotech.process.crud.model.domain.entities.Contact;
import com.elotech.process.crud.model.domain.entities.Person;
import com.elotech.process.crud.model.dto.PersonDto;
import com.elotech.process.crud.model.exception.NotFoundException;
import com.elotech.process.crud.model.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
     MockMvc mockMvc;
    @Autowired
     ObjectMapper objectMapper;

    @Autowired
    PersonAdapter adapter;

    @MockBean
     PersonService personService;

    @Test
    @DisplayName("Should create person")
    void createPersonTest() throws Exception {
        String json = """
                       {
                           "name": "Celia Monteiro",
                           "cpf": "82861900920",
                           "birthDate": "1971-07-21",
                           "contactList": [
                               {
                                   "nameContact": "Célia telephone",
                                   "telephone": "44998007033",
                                   "email": "celia@gmail.com"
                               }
                           ]
                       }
                """;

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException")
    void createPersonExceptionTest() throws Exception {
        Person person = new Person(2L, "Vinicius", "82861900920",
                LocalDate.of(2023, 10, 10), null);

        mockMvc.perform(post("/person")
                        .content(String.valueOf(person))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get person by id")
    void getPersonTest() throws Exception {
        Person person = new Person(2L, "Vinicius", "82861900920",
                LocalDate.of(2023, 10, 10), null);

        when(personService.findById(person.getId())).thenReturn(adapter.toDto(person));

        mockMvc.perform(get("/person/{id}", person.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpectAll(status().isOk(),
                        jsonPath("$.name").value("Vinicius"));
    }

    @Test
    @DisplayName("Should get person for name contains")
    void findByNameContainsTest() throws Exception {
        PersonDto person = new PersonDto(2L, "Vinicius Updated", "82861900920",
                LocalDate.of(2023, 10, 10), List.of(new Contact(2L, "Vinicius contact", "44998007032", "vinicius@gmail.com")));

        Pageable pageable = Pageable.unpaged();
        Page<PersonDto> personDtoPage = new PageImpl<>(List.of(person));

        when(personService.findByNameContains("Vinic", pageable)).thenReturn(personDtoPage);

        mockMvc.perform(get("/person")
                        .param("name", "TesteNome")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpectAll(status().isOk());
    }

    @Test
    @DisplayName("Should throw NotFoundException")
    void entityNotFoundTest() throws Exception {
        Long id = 10L;

        when(personService.findById(id)).thenThrow(new NotFoundException());

        mockMvc.perform(get("/person/{id}", id))
                .andExpectAll(status().isNotFound(), content().string(is("Entity not found")));
    }

    @Test
    @DisplayName("Should update person")
    void updatePersonTest() throws Exception {
        Person updatedPerson = new Person(2L, "Vinicius Updated", "82861900920",
                LocalDate.of(2023, 10, 10),
                List.of(new Contact(2L, "Vinicius contact", "44998007032", "vinicius@gmail.com")));

        when(personService.update(updatedPerson.getId(), updatedPerson)).thenReturn(updatedPerson);

        mockMvc.perform(put("/person/{id}", updatedPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should delete person by id")
    void deletePersonTest() throws Exception {
        Long id = 2L;

        doNothing().when(personService).deleteById(id);

        mockMvc.perform(delete("/person/{id}", id))
                .andExpect(status().isNoContent());
    }
}
