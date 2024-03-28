package com.elotech.process.crud.model.dto;

import com.elotech.process.crud.model.domain.entities.Contact;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PersonDto {
    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private List<Contact> contactList;
}
