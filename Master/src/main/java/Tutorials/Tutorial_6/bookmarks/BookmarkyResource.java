package Tutorials.Tutorial_6.bookmarks;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * @author Greg Turnquist
 */
class BookmarkyResource extends ResourceSupport {

    private final Bookmarky bookmark;

    public BookmarkyResource(Bookmarky bookmark) {
        String username = bookmark.getAccount().getUsername();
        this.bookmark = bookmark;
        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
        this.add(linkTo(BookmarkyRestController.class, username).withRel("bookmarks"));
        this.add(linkTo(methodOn(BookmarkyRestController.class, username)
                .readBookmark(username, bookmark.getId())).withSelfRel());
    }

    public Bookmarky getBookmark() {
        return bookmark;
    }
}