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
class BookmarkyRestController {

    private final BookmarkyRepository bookmarkyRepository;

    private final AccountyRepository accountRepository;

    @Autowired
    BookmarkyRestController(BookmarkyRepository bookmarkRepository,
                            AccountyRepository accountRepository) {
        this.bookmarkyRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<BookmarkyResource> readBookmarks(@PathVariable String userId) {

        this.validateUser(userId);

        List<BookmarkyResource> bookmarkResourceList = bookmarkyRepository
                .findByAccountyUsername(userId).stream().map(BookmarkyResource::new)
                .collect(Collectors.toList());

        return new Resources<>(bookmarkResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmarky input) {

        this.validateUser(userId);

        return accountRepository.findByUsername(userId)
                .map(account -> {
                    Bookmarky bookmark = bookmarkyRepository
                            .save(new Bookmarky(account, input.uri, input.description));

                    Link forOneBookmark = new BookmarkyResource(bookmark).getLink("self");

                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    BookmarkyResource readBookmark(@PathVariable String userId,
                                   @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return new BookmarkyResource(this.bookmarkyRepository.findOne(bookmarkId));
    }

    private void validateUser(String userId) {
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}