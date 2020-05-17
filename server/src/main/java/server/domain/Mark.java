package server.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "Marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Human.class)
    private Human student;

    @ManyToOne(targetEntity = Human.class)
    private Human teacher;

    @ManyToOne(targetEntity = Subject.class)
    private Subject subject;

    private int value;
}
