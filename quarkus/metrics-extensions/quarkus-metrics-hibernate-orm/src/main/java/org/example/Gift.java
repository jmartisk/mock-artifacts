package org.example;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Gift {

    private Long id;
    private String description;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
