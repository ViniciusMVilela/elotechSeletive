package com.elotech.process.crud.model.infra;

import com.elotech.process.crud.model.domain.entities.Contact;
import com.elotech.process.crud.model.domain.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GlobalConfigExceptionHandlerTest {
    @Autowired
     MockMvc mockMvc;

    @Autowired
     ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Should create person")
    void personPostTest() throws Exception {
        Person person = new Person(2L, "Vinicius", "82861900920",
                LocalDate.of(2023, 10, 10),
                List.of(new Contact(1L, "Vinicius contact", "44998007032", "vinicius@gmail.com")));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("CPF ConstraintViolationException")
    void personInvalidCpfTest() throws Exception {
        Person person = new Person(3L, "Vinicius", "82861900921", LocalDate.of(2023, 10, 10), List.of(new Contact(2L, "Vinicius contact", "44998007032", "vinicius@gmail.com")));


        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(is("Validation Error: Property: cpf, Message: Invalid CPF, please put a valid cpf with only numbers; ")));
    }

    @Test
    @DisplayName("BirthDate ConstraintViolationException")
    void personInvalidFutureBirthDate() throws Exception {
        Person person = new Person(3L, "Vinicius", "82861900920", LocalDate.of(2024, 10, 10), List.of(new Contact(2L, "Vinicius contact", "44998007032", "vinicius@gmail.com")));


        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(is("Validation Error: Property: birthDate, Message: Birth Date need be a past date; ")));
    }

    @Test
    @DisplayName("Email ConstraintViolationException")
    void personInvalidEmail() throws Exception {
        Person person = new Person(3L, "Vinicius", "07454185975", LocalDate.of(2023, 10, 10), List.of(new Contact(2L, "Vinicius contact", "44998007032", "vinicius.com")));


        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(is("Validation Error: Property: email, Message: Invalid email, please put a valid email; ")));
    }

    @Test
    @DisplayName("EntityNotFoundException")
    void entityNotFoundTest() throws Exception {
        Long id = 10L;
        mockMvc.perform(get("/person/{id}", id))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(is("Entity not found")));
    }

    @Test
    @DisplayName("CPF already exists ConstraintViolationException ")
    void createPersonWithCpfAlreadyExists() throws Exception {
        Person person = new Person(2L, "Marcos", "82861900920",
                LocalDate.of(2000, 12, 10),
                List.of(new Contact(1L, "Marcos contact", "44998007052", "marcos@gmail.com")));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(is("Please confirm fields, datas can already exists")));
    }

    @Test
    @DisplayName("ContactList empty ConstraintViolationException ")
    void tryRemoveContactPerson() throws Exception {
        Long id = 1L;

        String requestBody = "{\"name\": \"Vinicius Vilela\", " +
                "\"cpf\": \"82861900920\", " +
                "\"birthDate\": \"2023-10-10\"," +
                "\"contactList\": []}";

        mockMvc.perform(put("/person/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(is("Validation Error: Field: contactList, Message: must not be empty")));
    }
}