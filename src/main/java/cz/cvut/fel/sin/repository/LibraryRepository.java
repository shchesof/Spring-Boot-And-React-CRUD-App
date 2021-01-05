package cz.cvut.fel.sin.repository;

import cz.cvut.fel.sin.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> findByName(String name);
}
