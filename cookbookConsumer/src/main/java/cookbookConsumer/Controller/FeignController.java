package cookbookConsumer.Controller;

import cookbookConsumer.DTOs.AccountInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Spencer Gibb
 */
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class FeignController {
	@Autowired
	CookBookProducer client;
	@RequestMapping(value = "/discoverF")
	LinkedHashMap datasFromClient(){
	    LinkedHashMap returnMap=new LinkedHashMap();
	    returnMap.putAll(client.oneAccountById(1L));
        returnMap.putAll(client.allAccounts());
        return returnMap;
	}

    @RequestMapping(value = "/sendF")
    LinkedHashMap datasToClient(){
	    String nmbr= Double.toString(Math.random());
        return client.writeNewAccount(new AccountInDTO("A@b.de"+nmbr,"mister"+nmbr,"dale"+nmbr));
    }

    @FeignClient("CookBook")
	interface CookBookProducer {
		@RequestMapping(value = "/account/one/{id}", method = GET)
		LinkedHashMap oneAccountById(@PathVariable(value = "id") Long id);
        @RequestMapping(value = "/account/all", method = GET)
        LinkedHashMap allAccounts();
		@RequestMapping(value = "/account/one", method = POST)
		LinkedHashMap writeNewAccount(@RequestBody AccountInDTO accountInDTO);

	}
}
