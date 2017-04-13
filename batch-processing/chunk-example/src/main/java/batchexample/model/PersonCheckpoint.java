package batchexample.model;

import java.io.Serializable;

/**
 * The object used to describe a checkpoint in the 'people' job. It merely contains
 * a number denoting how many persons were already successfully processed.
 *
 * @author Jan Martiska
 */
public class PersonCheckpoint implements Serializable {

    private final long personsCompleted;

    public PersonCheckpoint(long personsCompleted) {
        this.personsCompleted = personsCompleted;
    }

    public long getPersonsCompleted() {
        return personsCompleted;
    }

    @Override
    public String toString() {
        return "PersonCheckpoint{" +
                "personsCompleted=" + personsCompleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonCheckpoint that = (PersonCheckpoint)o;
        return personsCompleted == that.personsCompleted;
    }

    @Override
    public int hashCode() {
        return (int)(personsCompleted ^ (personsCompleted >>> 32));
    }
}
