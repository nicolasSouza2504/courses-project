package br.com.backendapi.domain.subscribe;

import br.com.backendapi.domain.DefaultEntity;
import br.com.backendapi.domain.course.Course;
import br.com.backendapi.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "subscribe")
@NoArgsConstructor
@AllArgsConstructor
public class Subscribe extends DefaultEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_course", referencedColumnName = "id", insertable = false, updatable = false)
    private Course course;

    @Column(name = "id_course")
    private Long idCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "id_user")
    private Long idUser;

    public Subscribe(Course course, User user) {

        this.course = course;
        this.user = user;

    }

    @PreUpdate
    @PrePersist
    public void setOpttions() {

        if (this.course != null) {
            this.idCourse = this.course.getId();
        }


        if (this.user != null) {
            this.idUser = this.user.getId();
        }

    }

}
