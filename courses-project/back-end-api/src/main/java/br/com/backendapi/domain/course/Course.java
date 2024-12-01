package br.com.backendapi.domain.course;

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
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
public class Course extends DefaultEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "avaiable_subscribes")
    private Integer avaiableSubscribes;

}
