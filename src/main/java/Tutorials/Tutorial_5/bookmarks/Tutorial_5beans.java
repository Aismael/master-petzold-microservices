package Tutorials.Tutorial_5.bookmarks;

/**
 * Created by Aismael on 04.12.2016.
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Tutorial_5beans {

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                            BookmarkRepository bookmarkRepository) {
        return (args) ->
                Arrays.asList(
                        "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                        .forEach(a -> {
                            Account account = accountRepository.save(new Account(a, "password"));
                            bookmarkRepository.save(new Bookmark(account,
                                    "http://bookmark.com/1/" + a, "A description"));
                            bookmarkRepository.save(new Bookmark(account,
                                    "http://bookmark.com/2/" + a, "A description"));
                        });
    }
}