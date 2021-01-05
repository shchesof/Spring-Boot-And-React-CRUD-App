package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.entity.Book;
import cz.cvut.fel.sin.entity.PublishingHouse;
import cz.cvut.fel.sin.repository.BookRepository;
import cz.cvut.fel.sin.repository.PublishingHouseRepository;
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
public class ReleaseBookByPublishingHouseTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Autowired
    private PublishingHouseServiceImpl publishingHouseService;

    @Test
    @Transactional
    public void test() {
        Book book = new Book("Test book", "Horror", "1111", new Date("22/01/1999"));
        bookRepository.save(book);

        PublishingHouse publishingHouse = new PublishingHouse("house1", "CVUT FEL");
        publishingHouseRepository.save(publishingHouse);

        publishingHouseService.releaseBook(book.getId(), publishingHouse.getId());
        Assert.assertTrue(publishingHouse.getBooks().size() == 1);
    }
}
