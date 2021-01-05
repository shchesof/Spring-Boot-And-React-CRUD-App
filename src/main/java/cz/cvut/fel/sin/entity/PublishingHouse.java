package cz.cvut.fel.sin.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PUBLISHING_HOUSE")
public class PublishingHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="publishing_house_id")
    private Long id;

    @Column(name="publishing_house_name")
    @NotNull
    private String name;

    @Column(name="publishing_house_address")
    @NotNull
    private String address;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "publishingHouse")
    private Set<Book> books = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "publishingHouses")
    private Set<Author> authors = new HashSet<>();

    public PublishingHouse() {
    }

    public PublishingHouse(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "PublishingHouse{" +
                "id=" + id +
                ", name=" + name +
                ", address=" + address +
                "}";
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

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
