package Order.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller um den den Billing Service zu Lokalisieren
 * Created by Martin Petzold on 21.02.2017.
 */
@RestController
@EnableFeignClients
public class CallController {
    @Autowired
    BillingCall client;

    private static URI callURI;

    /**
     * Methode die den Port des Billingservice Ausgibt
     * @return Port
     * @throws URISyntaxException
     */
    @RequestMapping(path = "${RESTConfiguration.call.path}",method = RequestMethod.GET)
    public int getIds() throws URISyntaxException {
        System.out.println("get");
        System.out.println(client.getURI().toString());
        return  new URI(client.getURI()).getPort();
    }


    /**
     * Methode welche die Rest Methoden des Billing Service Verbindet
     * und durch Feigne direkt im Sprincode Nutzbar macht
     */
    @FeignClient("Billing")
    interface BillingCall {
        @RequestMapping(value = "${RESTConfiguration.call.path}", method = GET)
        String getURI();
    }


}
