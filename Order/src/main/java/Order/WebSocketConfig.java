package Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Konfigurationsklasse für den Sockjs Webserver
 */
@EnableAsync
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${RESTConfiguration.broadcast.out}")
    private String out;
    @Value("${RESTConfiguration.broadcast.in}")
    private String in;
    @Value("${RESTConfiguration.broadcast.name}")
    private String name;

    /**
     * Konfiguriert die Webserver in und out Pfade
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(out);
        config.setApplicationDestinationPrefixes(in);
    }

    /**
     * Konfiguriert von wo aus auf die Websockets zugegriffen werden kann
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint(name).setAllowedOrigins("*").withSockJS();
    }

}

