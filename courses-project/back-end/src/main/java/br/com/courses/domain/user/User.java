package br.com.courses.domain.user;

import br.com.courses.domain.DefaultEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends DefaultEntity {

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "name")
    private String name;

    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "lockout_time")
    private LocalDateTime lockoutTime;

    @Column(name = "last_failed_login_time")
    private LocalDateTime lastFailedLoginTime;

}
