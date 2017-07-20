package Billing.Aspects;

import Billing.DTOs.BankAccountDTO;
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
 * Created by Martin Petzold on 31.01.2017.
 * Aspect zur Sendung einer Nachricht bei Ageschlossener Bezahlung
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

    PayDTO payDTO = new PayDTO();

    /**
     *
     */
    @Pointcut("execution(* Billing.Controller.MainPageController.pay(..))")
    public void orderIsMade() {
    }

    /**
     *
     * @param payDTO
     */
    @Pointcut("execution(* Billing.Controller.MainPageController.pay(..))&& args(payDTO,..)")
    public void orderWillMade(PayDTO payDTO) {
    }

    /**
     * Methode die beim Aufruf der Methode die Parameter
     * für den Aspect nutzbar macht
     * @param payDTO
     */
    @Before("orderWillMade(payDTO)")
    public void getData(PayDTO payDTO) {
        this.payDTO = payDTO;
    }

    /**
     * Sendet die Nachricht zum Rockatchat
     *
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "orderIsMade()", returning = "returnValue")
    public void broadcast(JoinPoint joinPoint, Object returnValue) {
        System.out.println("postchat");
        System.out.println(returnValue.getClass());
        if (returnValue instanceof BankAccountDTO) {
            String mail =bankAccountRepository.getOne(payDTO.getBankAccountId()).getAccount().getOne().getMail();
            XOrder order = orderRepository.getOne(payDTO.getOrderId());
            System.out.println(mail);
            chatController.sendMsg(mail,"You Payed: " + makeOrderToMsg(order));
        }
    }

    /**
     * wandelt die Order in einen String
     *
     * @param order
     * @return
     */
    private String makeOrderToMsg(XOrder order) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
