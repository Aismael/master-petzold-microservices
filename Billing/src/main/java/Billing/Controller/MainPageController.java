package Billing.Controller;

import Billing.Entities.Bank;
import Billing.Entities.BankAccount;
import Billing.Entities.XOrder;
import Billing.Loader.WebSocketDataLoader;
import Billing.Repositories.BankAccountRepository;
import Billing.Repositories.BankRepository;
import Billing.Repositories.XOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spencer Gibb
 */
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class MainPageController {
	@Autowired
	XOrderRepository orderRepository;
	@Autowired
	BankAccountRepository bankAccountRepository;
	@Autowired
	BankRepository bankRepository;
	@RequestMapping(value="${RESTConfiguration.view.order.path}"+
			"${RESTConfiguration.view.order.one.path}"+
			"${RESTConfiguration.view.order.one.idAndAccount.path}"
	)
	public XOrder orderByAccount(@PathVariable Long id,@PathVariable Long accountId){
		return orderRepository.findByIdAndAccountId(id,accountId);
	}
	@RequestMapping(value="${RESTConfiguration.view.bankAccount.path}"+
			"${RESTConfiguration.view.bankAccount.all.path}"+
			"${RESTConfiguration.view.bankAccount.all.account.path}"
	)
	public List<BankAccount> bankAccountsByAccountId(@PathVariable Long accountId){
		return bankAccountRepository.findAllByAccountId(accountId);
	}

	@RequestMapping(value = "${RESTConfiguration.view.bank.path}"+
			"${RESTConfiguration.view.bank.all.path}")
	public List<Bank> getAllBanks(){
		return bankRepository.findAll();
	}
}





