package com.elotech.process.crud.model.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tb_person_generator")
    @SequenceGenerator(name = "tb_person_generator", sequenceName = "person_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(length = 255, nullable = false)
    private String name;
    @NotBlank
    @CPF(message = "Invalid CPF, please put a valid cpf with only numbers")
    @Column(length = 11, nullable = false)
    private String cpf;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
    @Column(name = "birth_date", nullable = false)
    @PastOrPresent(message = "Birth Date need be a past date")
    private LocalDate birthDate;

    @NotNull
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private List<Contact> contactList;


}
