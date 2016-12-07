package Tutorials.Tutorial_5.bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Aismael on 04.12.2016.
 */
public interface AccountxRepository extends JpaRepository<Accountx, Long> {
    Optional<Accountx> findByUsername(String username);
}