package com.elotech.process.crud.model.service;

import com.elotech.process.crud.model.adapter.PersonAdapter;
import com.elotech.process.crud.model.dto.PersonDto;
import com.elotech.process.crud.model.domain.entities.Person;
import com.elotech.process.crud.model.exception.NotFoundException;
import com.elotech.process.crud.model.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonAdapter adapter;

    @InjectMocks
    private PersonService service;

    @Test
    @DisplayName("Should get Person by Id")
    void testFindById_WhenPersonExists_ReturnsPersonDto() {
        Long id = 1L;
        Person person = new Person();
        PersonDto expectedDto = new PersonDto();
        when(repository.findById(id)).thenReturn(Optional.of(person));
        when(adapter.toDto(person)).thenReturn(expectedDto);

        PersonDto result = service.findById(id);

        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(repository, times(1)).findById(id);
        verify(adapter, times(1)).toDto(person);
    }

    @Test
    @DisplayName("Should return NotFoundException when Person is not found by Id")
    void testFindById_WhenPersonNotExists_ThrowsNotFoundException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Should save Person")
    void testSavePerson() {
        Person person = new Person();
        when(repository.save(person)).thenReturn(person);

        Person result = service.savePerson(person);

        assertNotNull(result);
        assertEquals(person, result);
        verify(repository, times(1)).save(person);
    }

    @Test
    @DisplayName("Should create Person")
    void testCreatePerson() {
        PersonDto newPersonDto = new PersonDto();
        Person person = new Person();
        when(adapter.toEntity(newPersonDto)).thenReturn(person);
        when(repository.save(person)).thenReturn(person);

        Person result = service.craetePerson(newPersonDto);

        assertNotNull(result);
        verify(adapter, times(1)).toEntity(newPersonDto);
        verify(repository, times(1)).save(person);
    }

    @Test
    @DisplayName("Should delete Person by Id")
    void testDeleteById() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository, times(1)).deleteById(id);
    }
}
