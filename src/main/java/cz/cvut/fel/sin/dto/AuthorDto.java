package cz.cvut.fel.sin.dto;

import java.util.HashSet;
import java.util.Set;

public class AuthorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Long> publishingHousesIds = new HashSet<>();

    // necessary for model mapper
    public AuthorDto() {
    }

    public AuthorDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Long> getPublishingHousesIds() {
        return publishingHousesIds;
    }

    public void setPublishingHousesIds(Set<Long> publishingHousesIds) {
        this.publishingHousesIds = publishingHousesIds;
    }
}
