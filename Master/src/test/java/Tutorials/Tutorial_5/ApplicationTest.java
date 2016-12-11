package Tutorials.Tutorial_5;
import Tutorials.Tutorial_1.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationTest {


   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

}

// docker run -d -p 8080:8080 --name webserver test