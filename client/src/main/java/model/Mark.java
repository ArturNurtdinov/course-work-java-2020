package model;

import java.util.Objects;

public class Mark {
    private long id;
    private Human student;
    private Human teacher;
    private Subject subject;
    private int value;

    public Mark() {
    }

    public Mark(Human student, Human teacher, Subject subject, int value) {
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.value = value;
    }

    public Mark(long id, Human student, Human teacher, Subject subject, int value) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public Human getStudent() {
        return student;
    }

    public Human getTeacher() {
        return teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "mark = " + value + ", P = " + teacher + ", S = " + student + ", subject = " + subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return id == mark.id &&
                value == mark.value &&
                Objects.equals(student, mark.student) &&
                Objects.equals(teacher, mark.teacher) &&
                Objects.equals(subject, mark.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, teacher, subject, value);
    }
}
