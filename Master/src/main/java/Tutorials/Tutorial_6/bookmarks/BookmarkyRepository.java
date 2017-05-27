package Tutorials.Tutorial_6.bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Martin Petzold on 04.12.2016.
 */
public interface BookmarkyRepository extends JpaRepository<Bookmarky, Long> {
    Collection<Bookmarky> findByAccountyUsername(String username);
}