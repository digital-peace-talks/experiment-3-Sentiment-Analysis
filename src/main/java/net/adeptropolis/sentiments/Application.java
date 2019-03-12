package net.adeptropolis.sentiments;
import com.google.common.collect.ImmutableMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setDefaultProperties(ImmutableMap.of("server.port", "6868"));
    app.run(args);
  }

}
