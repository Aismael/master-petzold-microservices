package Tutorials.Tutorial_6.bookmarks;

import Tutorials.Tutorial_6.bookmarks.Bookmark2;
import Tutorials.Tutorial_6.bookmarks.Bookmark2RestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import static sun.plugin.javascript.navig.JSType.Link;

/**
 * @author Greg Turnquist
 */
class Bookmark2Resource extends ResourceSupport {

    private final Bookmark2 bookmark;

    public Bookmark2Resource(Bookmark2 bookmark) {
        String username = bookmark.getAccount().getUsername();
        this.bookmark = bookmark;
        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
        this.add(linkTo(Bookmark2RestController.class, username).withRel("bookmarks"));
        this.add(linkTo(methodOn(Bookmark2RestController.class, username)
                .readBookmark(username, bookmark.getId())).withSelfRel());
    }

    public Bookmark2 getBookmark() {
        return bookmark;
    }
}