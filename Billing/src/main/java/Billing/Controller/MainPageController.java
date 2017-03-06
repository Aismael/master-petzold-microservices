package Billing.Controller;

import Billing.DTOs.MakeNewAccountDTO;
import Billing.DTOs.PayDTO;
import Billing.Entities.Bank;
import Billing.Entities.BankAccount;
import Billing.Entities.XOrder;
import Billing.Exception.payException;
import Billing.Loader.WebSocketDataLoader;
import Billing.Repositories.AccountRepository;
import Billing.Repositories.BankAccountRepository;
import Billing.Repositories.BankRepository;
import Billing.Repositories.XOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Rest Controller für die Abfragen der Angular JS UI für den Bezahl Use Case
 * @author Martin Petzold
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
	@Autowired
	AccountRepository accountRepository;

	/**
	 *
	 * @param id
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value="${RESTConfiguration.view.order.path}"+
			"${RESTConfiguration.view.order.one.path}"+
			"${RESTConfiguration.view.order.one.idAndAccount.path}"+
            "/{id}/{accountId}"
	)
	public XOrder orderByAccount(@PathVariable("id") Long id,
								 @PathVariable("accountId") Long accountId){
		System.out.println("oba");
		return orderRepository.findByIdAndAccountId(id,accountId);
	}

	/**
	 *
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value="${RESTConfiguration.view.bankAccount.path}"+
			"${RESTConfiguration.view.bankAccount.all.path}"+
			"${RESTConfiguration.view.bankAccount.all.account.path}"+"/{accountId}"
	)
	public List<BankAccount> bankAccountsByAccountId(@PathVariable("accountId") Long accountId){
		return bankAccountRepository.findAllByAccountId(accountId);
	}

	/**
	 *
	 * @param makeNewAccountDTO
	 * @return
	 */
	@RequestMapping(value="${RESTConfiguration.view.bankAccount.path}"+
			"${RESTConfiguration.view.bankAccount.one.path}"+
			"${RESTConfiguration.view.bankAccount.one.account.path}"
			, method = RequestMethod.POST
	)
	public BankAccount makebankAccountsByAccountIdAndBankid(@RequestBody MakeNewAccountDTO makeNewAccountDTO){
		BankAccount bankAccount=new BankAccount();
		bankAccount.setAmmount(new BigDecimal("100"));
		bankAccount.getBank().add(bankRepository.getOne(makeNewAccountDTO.getBankId()));
		bankAccount.getAccount().add(accountRepository.getOne(makeNewAccountDTO.getAccountId()));
		bankAccountRepository.flush();
		return bankAccountRepository.saveAndFlush(bankAccount);
	}

	/**
	 *
	 * @param payDTO
	 * @return
	 */
	@RequestMapping(value="${RESTConfiguration.view.bankAccount.path}"+
			"${RESTConfiguration.view.bankAccount.one.path}"+
			"${RESTConfiguration.view.bankAccount.one.pay.path}"
			, method = RequestMethod.POST
	)
	public BankAccount pay(@RequestBody PayDTO payDTO){
		XOrder order=orderRepository.getOne(payDTO.getOrderId());
		BankAccount bankAccount=bankAccountRepository.findOne(payDTO.getBankAccountId());
		System.out.println("xxx"+order.getSum().toString());
		System.out.println("yyy"+bankAccount.getAmmount().toString());
		if(bankAccount.getAmmount().subtract(order.getSum()).longValue()>=0) {
			bankAccount.setAmmount(bankAccount.getAmmount().subtract(order.getSum()));
		}else{
			throw new payException(bankAccount.toString());
		}
		return bankAccountRepository.saveAndFlush(bankAccount);
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "${RESTConfiguration.view.bank.path}"+
			"${RESTConfiguration.view.bank.all.path}")
	public List<Bank> getAllBanks(){
		return bankRepository.findAll();
	}
}





