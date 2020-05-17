package server.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "People")
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50)
    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name cant'be longer than 50 characters")
    private String firstName;

    @Column(length = 50)
    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name cant'be longer than 50 characters")
    private String lastName;

    @Column(length = 50)
    @NotBlank(message = "Father name is mandatory")
    @Size(max = 50, message = "Father name cant'be longer than 50 characters")
    private String fatherName;

    @ManyToOne(targetEntity = Group.class)
    private Group group;

    private char type;
}
