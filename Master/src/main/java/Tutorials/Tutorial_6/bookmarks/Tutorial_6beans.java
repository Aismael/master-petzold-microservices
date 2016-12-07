package Tutorials.Tutorial_6.bookmarks;

/**
 * Created by Aismael on 04.12.2016.
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Tutorial_6beans {

    @Bean
    CommandLineRunner init2(AccountyRepository accountyRepository,
                            BookmarkyRepository bookmarkyRepository) {
        return (args) ->
                Arrays.asList(
                        "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                        .forEach(a -> {
                            Accounty account = accountyRepository.save(new Accounty(a, "password"));
                            bookmarkyRepository.save(new Bookmarky(account,
                                    "http://bookmark.com/1/" + a, "A description"));
                            bookmarkyRepository.save(new Bookmarky(account,
                                    "http://bookmark.com/2/" + a, "A description"));
                        });
    }
}