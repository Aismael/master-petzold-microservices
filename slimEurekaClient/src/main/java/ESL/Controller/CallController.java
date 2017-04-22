package ESL.Controller;

import ESL.DTOs.ServiceURIDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Controller um den den Billing Service zu Lokalisieren
 * Created by Martin Petzold on 21.02.2017.
 */
@RestController
@EnableFeignClients
public class CallController {
    @Autowired
    private DiscoveryClient discoveryClient;

    private static URI callURI;

    /**
     * Methode die den Port des Billingservice Ausgibt
     * @return Port
     * @throws URISyntaxException
     */
    @RequestMapping(path = "/call"+ "/{name}")
    public ServiceURIDTO getUrlByServiceName(@PathVariable("name") String name) {
        System.out.println("get");
        return  new ServiceURIDTO(getServiceUri(name));
    }


    URI getServiceUri(String name) {
        List<ServiceInstance> list = discoveryClient.getInstances(name);
        if (list != null && list.size() > 0) return list.get(0).getUri();
        else return null;
    }


}
