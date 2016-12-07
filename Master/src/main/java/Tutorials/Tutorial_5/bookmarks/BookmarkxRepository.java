package Tutorials.Tutorial_5.bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface BookmarkxRepository extends JpaRepository<Bookmarkx, Long> {
    Collection<Bookmarkx> findByAccountxUsername(String username);
}