package cz.cvut.fel.sin.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LIBRARY")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="library_id")
    private Long id;

    @Column(name="library_name")
    @NotNull
    private String name;

    @Column(name="library_address")
    @NotNull
    private String address;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="library_book",
            joinColumns = {@JoinColumn(name="library_id")},
            inverseJoinColumns = {@JoinColumn(name="book_id")})
    private Set<Book> books = new HashSet<>();

    public Library() {
    }

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Library{" +
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
}
