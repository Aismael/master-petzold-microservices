package Billing.Aspects;

import Billing.DTOs.PayDTO;
import Billing.Entities.BankAccount;
import Billing.Entities.XOrder;
import Billing.Repositories.BankAccountRepository;
import Billing.Repositories.XOrderRepository;
import OwnLibsGR.RocketChatSimpleChatController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Aismael on 31.01.2017.
 */
@Component
@Aspect
public class MsgAspect {
    @Autowired
    RocketChatSimpleChatController chatController;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    XOrderRepository orderRepository;

    PayDTO payDTO=new PayDTO();
    @Pointcut("execution(* Billing.Controller.MainPageController.pay(..))")
    public void orderIsMade(){
    }

    @Pointcut("execution(* Billing.Controller.MainPageController.pay(..))&& args(payDTO,..)")
    public void orderWillMade(PayDTO payDTO){}

    @Before("orderWillMade(payDTO)")
    public void getData(PayDTO payDTO){
        this.payDTO=payDTO;
    }

    @AfterReturning(pointcut = "orderIsMade()",returning = "returnValue")
    public void broadcast(JoinPoint joinPoint,Object returnValue) {
        System.out.println("postchat");
        System.out.println(returnValue.getClass());
        if(returnValue instanceof BankAccount) {
            XOrder order=orderRepository.getOne(payDTO.getOrderId());
            chatController.sendMsg(((BankAccount) returnValue).getAccount().getOne().getMail(),"You Payed: "+makeOrderToMsg(order));
        }
    }

    private String makeOrderToMsg(XOrder order){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(order);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return "error";
        }
    }
}
