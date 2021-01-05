package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.entity.Book;
import cz.cvut.fel.sin.entity.Library;
import cz.cvut.fel.sin.repository.BookRepository;
import cz.cvut.fel.sin.repository.LibraryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddBookIntoLibraryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryServiceImpl libraryService;

    @Test
    @Transactional
    public void test() {
        Book book = new Book("Test book", "Horror", "1111", new Date("22/01/1999"));
        bookRepository.save(book);

        Library library = new Library("Test library", "CVUT FEL");
        libraryRepository.save(library);

        libraryService.addBookIntoLibrary(book.getId(), library.getId());
        Assert.assertTrue(library.getBooks().size() == 1);
    }
}
