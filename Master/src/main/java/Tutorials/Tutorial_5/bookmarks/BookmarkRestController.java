package Tutorials.Tutorial_5.bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/1/{userId}/bookmarks")
class BookmarkRestController {

    private final BookmarkxRepository bookmarkxRepository;

    private final AccountxRepository accountxRepository;

    @Autowired
    BookmarkRestController(BookmarkxRepository bookmarkxRepository,
                           AccountxRepository accountxRepository) {
        this.bookmarkxRepository = bookmarkxRepository;
        this.accountxRepository = accountxRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmarkx> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);
        return this.bookmarkxRepository.findByAccountxUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmarkx input) {
        this.validateUser(userId);

        return this.accountxRepository
                .findByUsername(userId)
                .map(account -> {
                    Bookmarkx result = bookmarkxRepository.save(new Bookmarkx(account,
                            input.uri, input.description));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    Bookmarkx readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return this.bookmarkxRepository.findOne(bookmarkId);
    }

    private void validateUser(String userId) {
        this.accountxRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
