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
 * Aspect der nach dem Speichern einer Bestellung eine Nachricht
 * an den Rocketchat sendet
 * Created by Martin Petzold on 31.01.2017.
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

    /**
     * Pointcut für die Save und Flush Methode
     * einer Bestellung
     */
    @Pointcut("execution(* Order.Repositories.OrderConcepts.OrderRepository.saveAndFlush(..))")
    public void orderHasSaved(){
    }

    /**
     * Methode zum senden der rocketchat Nachricht nach der
     * Speicherung einer Bestellung
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "orderHasSaved()",returning = "returnValue")
    public void broadcast(JoinPoint joinPoint, Object returnValue) {
        if(returnValue instanceof Order){
            Account account=accountRepository.getOne(((Order) returnValue).getAccount().getOne().getId());
            orderRepository.flush();
            chatController.sendMsg(account.getMail(),"You Ordered"+makeOrderToMsg(orderRepository.getOne(((Order) returnValue).getId())));
        }
    }


    /**
     * Wandelt Bestellung in einen JSON String um
     * @param order
     * @return fertiger String
     */
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
