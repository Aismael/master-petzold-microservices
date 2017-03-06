package Billing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * controller für die Übergabe der UI von einem Anderen Service an diesen Service
 * Created by Aismael on 21.02.2017.
 * TODO rework UI Call von Einem Anderen Service
 */
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class CallController {
    @Autowired
    DiscoveryClient client;


    /**
     * Ausgabe der Eigenen URI
     * @return
     */
    @RequestMapping(value = "${RESTConfiguration.call.path}",method = GET)
    public String getURI() {
        URI localInstance = client.getLocalServiceInstance().getUri();
        return localInstance.toString();
    }
}
