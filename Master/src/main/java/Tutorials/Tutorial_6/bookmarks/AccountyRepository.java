package Tutorials.Tutorial_6.bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface AccountyRepository extends JpaRepository<Accounty, Long> {
    Optional<Accounty> findByUsername(String username);
}