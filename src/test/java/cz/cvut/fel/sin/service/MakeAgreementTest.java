package cz.cvut.fel.sin.service;

import cz.cvut.fel.sin.entity.Author;
import cz.cvut.fel.sin.entity.PublishingHouse;
import cz.cvut.fel.sin.repository.AuthorRepository;
import cz.cvut.fel.sin.repository.PublishingHouseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakeAgreementTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Autowired
    private AuthorServiceImpl authorService;

    @Test
    @Transactional
    public void test() {
        Author author = new Author("Sofia", "Shchepetova", "email.com");
        authorRepository.save(author);

        PublishingHouse publishingHouse = new PublishingHouse("HouseName", "FEL CVUT");
        publishingHouseRepository.save(publishingHouse);

        authorService.makeAgreementWithPublishingHouse(author.getId(), publishingHouse.getId());
        Assert.assertTrue(author.getPublishingHouses().size() == 1);
    }
}
