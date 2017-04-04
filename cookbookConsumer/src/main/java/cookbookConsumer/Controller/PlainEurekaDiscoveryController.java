package cookbookConsumer.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cookbookConsumer.DTOs.AccountInDTO;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Aismael on 04.04.2017.
 */
@RestController
public class PlainEurekaDiscoveryController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/discover")
    LinkedHashMap datasFromClient(){
        LinkedHashMap returnMap=new LinkedHashMap();
        returnMap.putAll(getAccount());
        returnMap.putAll(getAccounts());
        return returnMap;
    }

    @RequestMapping(value = "/send")
    LinkedHashMap datasToClient(){
        return postAccount();
    }

    private LinkedHashMap postAccount() {
        RestTemplate restTemplate = new RestTemplate();
        final String uri= getServiceUri().toString()+"/account/one";
        String nmbr= Double.toString(Math.random());
        AccountInDTO CAS=new AccountInDTO("A@b.de"+nmbr,"mister"+nmbr,"dale"+nmbr);
        return restTemplate.postForObject( uri, CAS, LinkedHashMap.class);
    }

    private LinkedHashMap getAccounts() {
        final String uri= getServiceUri().toString()+"/account/all";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, LinkedHashMap.class);
    }

    private LinkedHashMap getAccount() {
        final String uri= getServiceUri().toString()+"/account/one/1";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, LinkedHashMap.class);
    }

    URI getServiceUri() {
        List<ServiceInstance> list = discoveryClient.getInstances("CookBook");
        if (list != null && list.size() > 0) return list.get(0).getUri();
        else return null;
    }
}
