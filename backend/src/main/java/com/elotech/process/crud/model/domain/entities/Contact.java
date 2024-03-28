package com.elotech.process.crud.model.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "tb_contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tb_contact_generator")
    @SequenceGenerator(name = "tb_contact_generator", sequenceName = "contact_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "name_contact", nullable = false)
    private String nameContact;

    @NotBlank
    @Column(length = 11, nullable = false)
    private String telephone;

    @NotBlank
    @Email(message = "Invalid email, please put a valid email")
    private String email;

}
