package Order.Aspects;

import Order.Entities.Account;
import Order.Entities.OrderConcepts.Order;
import Order.Repositories.AccountRepository;
import Order.Repositories.OrderConcepts.OrderRepository;
import OwnLibsGR.RocketChatSimpleChatController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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
    AccountRepository accountRepository;
    @Autowired
    OrderRepository orderRepository;
    @Pointcut("execution(* Order.Repositories.OrderConcepts.OrderRepository.saveAndFlush(..))")
    public void orderHasSaved(){
    }

    @AfterReturning(pointcut = "orderHasSaved()",returning = "returnValue")
    public void broadcast(JoinPoint joinPoint, Object returnValue) {
        if(returnValue instanceof Order){
            Account account=accountRepository.getOne(((Order) returnValue).getAccount().getOne().getId());
            orderRepository.flush();
            chatController.sendMsg(account.getMail(),"You Ordered"+makeOrderToMsg(orderRepository.getOne(((Order) returnValue).getId())));
        }
    }


    private String makeOrderToMsg(Order order){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(order);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return "error";
        }
    }
}
