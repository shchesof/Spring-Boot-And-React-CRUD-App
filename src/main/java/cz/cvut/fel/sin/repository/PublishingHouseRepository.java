package cz.cvut.fel.sin.repository;

import cz.cvut.fel.sin.entity.PublishingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {

    List<PublishingHouse> findByName(String name);
}
