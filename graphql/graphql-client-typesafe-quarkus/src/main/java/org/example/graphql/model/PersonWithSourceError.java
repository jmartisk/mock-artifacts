package org.example.graphql.model;

import io.smallrye.graphql.client.typesafe.api.ErrorOr;

public class PersonWithSourceError {

    private String name;

    private Gender gender;

    private ErrorOr<String> error;

    public PersonWithSourceError() {
    }

    public PersonWithSourceError(String name, Gender gender, ErrorOr<String> failure) {
        this.name = name;
        this.gender = gender;
        this.error = failure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ErrorOr<String> getError() {
        return error;
    }

    public void setError(ErrorOr<String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PersonWithSourceError{" +
            "name='" + name + '\'' +
            ", gender=" + gender +
            ", error='" + error + '\'' +
            '}';
    }
}
