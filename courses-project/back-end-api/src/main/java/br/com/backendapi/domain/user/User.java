package br.com.backendapi.domain.user;

import br.com.backendapi.domain.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public Date lockoutTime;

    @Column(name = "last_failed_login_time")
    public Date lastFailedLoginTime;

}
