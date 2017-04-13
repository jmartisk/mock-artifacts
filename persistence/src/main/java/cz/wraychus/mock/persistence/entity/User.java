package cz.wraychus.mock.persistence.entity;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Transient;
import java.util.logging.Logger;

/**
 * @author jmartisk
 */
@Entity
public class User {

    @Inject @Transient @NamedAfterClass
    Logger logger;

    @Id
    private String username;
    private int numberOfEyes;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getNumberOfEyes() {
        return numberOfEyes;
    }

    public void setNumberOfEyes(int numberOfEyes) {
        this.numberOfEyes = numberOfEyes;
    }

    @PostPersist
    void postPersist() {
        System.out.println("Hi, I am User's PostPersist callback. And this user is named " + username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", numberOfEyes=" + numberOfEyes +
                '}';
    }
}
