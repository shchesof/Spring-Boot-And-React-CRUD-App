package cz.cvut.fel.sin.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="author_id")
    private Long id;

    @Column(name="author_first_name")
    @NotNull
    private String firstName;

    @Column(name="author_last_name")
    @NotNull
    private String lastName;

    @Column(name="author_email")
    @NotNull
    private String email;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="author_publishing_house",
            joinColumns = {@JoinColumn(name="author_id")},
            inverseJoinColumns = {@JoinColumn(name="publishing_house_id")})
    private Set<PublishingHouse> publishingHouses = new HashSet<>();

    @ManyToMany(mappedBy = "authors", fetch=FetchType.EAGER)
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name=" + firstName +
                ", last name=" + lastName +
                "}";
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PublishingHouse> getPublishingHouses() {
        return publishingHouses;
    }

    public void setPublishingHouses(Set<PublishingHouse> publishingHouses) {
        this.publishingHouses = publishingHouses;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
