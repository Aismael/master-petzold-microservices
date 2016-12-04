package Tutorials.Tutorial_6.bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface Bookmark2Repository extends JpaRepository<Bookmark2, Long> {
    Collection<Bookmark2> findByAccountUsername(String username);
}