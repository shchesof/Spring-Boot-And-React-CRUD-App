package cz.cvut.fel.sin.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BookDto {

    private Long id;
    private String name;
    private String genre;
    private String ISBN;
    private Date publicationDate;
    private Long publishingHouseId;
    private Set<Long> authorsIds = new HashSet<>();

    // necessary for model mapper
    public BookDto() {
    }

    public BookDto(String name, String genre, String ISBN, Date publicationDate) {
        this.name = name;
        this.genre = genre;
        this.ISBN = ISBN;
        this.publicationDate = publicationDate;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getPublishingHouseId() {
        return publishingHouseId;
    }

    public void setPublishingHouseId(Long publishingHouseId) {
        this.publishingHouseId = publishingHouseId;
    }

    public Set<Long> getAuthorsIds() {
        return authorsIds;
    }

    public void setAuthorsIds(Set<Long> authorsIds) {
        this.authorsIds = authorsIds;
    }
}
