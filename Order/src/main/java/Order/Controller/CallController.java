package Order.Controller;

import Order.DTOs.CallIdsDto;
import feign.Headers;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Aismael on 21.02.2017.
 */
@RestController
@EnableFeignClients
public class CallController {
    @Autowired
    BillingCall client;

    private static URI callURI;
    CallIdsDto callIdsDto=new CallIdsDto(0L,0L);

    @RequestMapping(path = "${RESTConfiguration.call.path}",method = RequestMethod.GET)
    public int getIds() throws URISyntaxException {
        System.out.println("get");
        System.out.println(client.getURI().toString());
        System.out.println(callIdsDto);
        client.setURI(callIdsDto);
        return  new URI(client.getURI()).getPort();
    }

    @RequestMapping(path = "${RESTConfiguration.call.path}",method = RequestMethod.POST)
    public int setIds(@RequestBody CallIdsDto callIdsDto) throws URISyntaxException {
        System.out.println("post");
        System.out.println(callIdsDto);
        client.setURI(callIdsDto);
        return   new URI(client.getURI()).getPort();
    }

    @FeignClient("Billing")
    interface BillingCall {
        @RequestMapping(value = "${RESTConfiguration.call.path}", method = GET)
        String getURI();
        @RequestMapping(value = "${RESTConfiguration.call.path}", method = POST, consumes = "application/json")
        String setURI(CallIdsDto callIdsDto);
    }


}
