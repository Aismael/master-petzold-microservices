package hello

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
class ThisWillActuallyRun {

    @RequestMapping("/groovy")
    String home() {
        return "Hello World with spring vm on groovy!"
    }

}
