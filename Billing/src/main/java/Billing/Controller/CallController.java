package Billing.Controller;

import Billing.DTOs.CallIdsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URL;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by Aismael on 21.02.2017.
 */
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class CallController {
    @Autowired
    DiscoveryClient client;

    CallIdsDto callIdsDto=new CallIdsDto(0L,0L);

    @RequestMapping(value = "${RESTConfiguration.call.path}",method = GET)
    public String getURI() {
        URI localInstance = client.getLocalServiceInstance().getUri();
        return localInstance.toString();
    }

    @RequestMapping(value = "${RESTConfiguration.call.path}",method = POST, consumes = "application/json")
    public String setURI(@RequestBody CallIdsDto callIdsDto) {
        this.callIdsDto=callIdsDto;
        System.out.println(this.callIdsDto);
        System.out.println(callIdsDto);
        URI localInstance = client.getLocalServiceInstance().getUri();
        return localInstance.toString();
    }


    @RequestMapping(value = "${RESTConfiguration.call.path}"+"${RESTConfiguration.call.data.path}",method = GET)
    public CallIdsDto getCallIdsDto() {
        CallIdsDto ret=this.callIdsDto;
        this.callIdsDto=new CallIdsDto();
        return ret;
    }
}
