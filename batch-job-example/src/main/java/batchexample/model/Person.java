package batchexample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * The main domain object used in the 'people' job. This represents a person, each person
 * having a name and a password. The goal of the 'people' job is to generate persons (in the reader),
 * generate random passwords for them (in the processor) and store them into a database
 * using JPA (in the writer).
 * @author Jan Martiska
 *
 */
@Entity
public class Person {

    public static final String PERSONS_GENERATOR = "persons-generator";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PERSONS_GENERATOR)
    @SequenceGenerator(name = PERSONS_GENERATOR, sequenceName = "PERSONS_SEQ")
    private Long id;

    @NotNull
    private String name;

    private String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }

        Person person = (Person)o;

        if (id != null ? !id.equals(person.id) : person.id != null) {
            return false;
        }
        if (name != null ? !name.equals(person.name) : person.name != null) {
            return false;
        }
        return !(password != null ? !password.equals(person.password) : person.password != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
