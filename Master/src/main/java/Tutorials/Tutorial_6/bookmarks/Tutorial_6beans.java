package Tutorials.Tutorial_6.bookmarks;

/**
 * Created by Aismael on 04.12.2016.
 */

import Tutorials.Tutorial_5.bookmarks.Account;
import Tutorials.Tutorial_5.bookmarks.AccountRepository;
import Tutorials.Tutorial_5.bookmarks.Bookmark;
import Tutorials.Tutorial_5.bookmarks.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Tutorial_6beans {

    @Bean
    CommandLineRunner init2(Account2Repository account2Repository,
                            Bookmark2Repository bookmark2Repository) {
        return (args) ->
                Arrays.asList(
                        "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                        .forEach(a -> {
                            Account2 account = account2Repository.save(new Account2(a, "password"));
                            bookmark2Repository.save(new Bookmark2(account,
                                    "http://bookmark.com/1/" + a, "A description"));
                            bookmark2Repository.save(new Bookmark2(account,
                                    "http://bookmark.com/2/" + a, "A description"));
                        });
    }
}