package Tutorials.Tutorial_6.bookmarks;

/**
 * Created by Aismael on 04.12.2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Greg Turnquist
 */
@RestController
@RequestMapping("/2/{userId}/bookmarks")
class Bookmark2RestController {

    private final Bookmark2Repository bookmarkRepository;

    private final Account2Repository accountRepository;

    @Autowired
    Bookmark2RestController(Bookmark2Repository bookmarkRepository,
                           Account2Repository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<Bookmark2Resource> readBookmarks(@PathVariable String userId) {

        this.validateUser(userId);

        List<Bookmark2Resource> bookmarkResourceList = bookmarkRepository
                .findByAccountUsername(userId).stream().map(Bookmark2Resource::new)
                .collect(Collectors.toList());

        return new Resources<>(bookmarkResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark2 input) {

        this.validateUser(userId);

        return accountRepository.findByUsername(userId)
                .map(account -> {
                    Bookmark2 bookmark = bookmarkRepository
                            .save(new Bookmark2(account, input.uri, input.description));

                    Link forOneBookmark = new Bookmark2Resource(bookmark).getLink("self");

                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    Bookmark2Resource readBookmark(@PathVariable String userId,
                                   @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return new Bookmark2Resource(this.bookmarkRepository.findOne(bookmarkId));
    }

    private void validateUser(String userId) {
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}