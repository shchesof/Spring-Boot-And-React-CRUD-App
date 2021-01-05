package cz.cvut.fel.sin.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="book_id")
    private Long id;

    @Column(name="book_name")
    @NotNull
    private String name;

    @Column(name="book_genre")
    @NotNull
    private String genre;

    @Column(name="book_isbn")
    @NotNull
    private String ISBN;

    @Column(name="book_publication_date")
    @NotNull
    private Date publicationDate;

    @ManyToOne(fetch=FetchType.EAGER)
    private PublishingHouse publishingHouse;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="book_author",
            joinColumns = {@JoinColumn(name="book_id")},
            inverseJoinColumns = {@JoinColumn(name="author_id")})
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "books", fetch=FetchType.EAGER)
    private Set<Library> libraries = new HashSet<>();

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(String name, String genre, String ISBN, Date publicationDate) {
        this.name = name;
        this.genre = genre;
        this.ISBN = ISBN;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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

    public Set<Author> getAuthors() {
       return authors;
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

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(Set<Library> libraries) {
        this.libraries = libraries;
    }
}
