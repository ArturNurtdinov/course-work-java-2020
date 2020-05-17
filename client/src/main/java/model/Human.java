package model;

import java.util.Objects;

public class Human {
    private long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private Group group;
    private char type;

    public Human() {
    }

    public Human(String firstName, String lastName, String fatherName, Group group, char type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.group = group;
        this.type = type;
    }

    public Human(long id, String firstName, String lastName, String fatherName, Group group, char type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.group = group;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Group getGroup() {
        return group;
    }

    public char getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " " + lastName + " " + firstName + " " + fatherName + ", group=" + group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return id == human.id &&
                type == human.type &&
                Objects.equals(firstName, human.firstName) &&
                Objects.equals(lastName, human.lastName) &&
                Objects.equals(fatherName, human.fatherName) &&
                Objects.equals(group, human.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, fatherName, group, type);
    }
}
