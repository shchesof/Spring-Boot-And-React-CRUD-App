package cz.cvut.fel.sin.dto;

import java.util.HashSet;
import java.util.Set;

public class LibraryDto {

    private Long id;
    private String name;
    private String address;
    private Set<Long> booksIds = new HashSet<>();

    // necessary for model mapper
    public LibraryDto() {
    }

    public LibraryDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Long> getBooksIds() {
        return booksIds;
    }

    public void setBooksIds(Set<Long> booksIds) {
        this.booksIds = booksIds;
    }
}
