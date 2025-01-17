package it.gianmarco.demo.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String surname;

    private String email;

    @JsonIgnore // Nasconde la password nelle risposte API
    private String password;

    private LocalDate dateOfBirth;
}
