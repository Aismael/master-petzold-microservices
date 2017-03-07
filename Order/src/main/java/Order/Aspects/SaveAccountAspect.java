package Order.Aspects;

import Order.Controller.BroadCastNewAccountsController;
import Order.DTOs.AccountBroadcastDto;
import Order.Entities.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Aspect der nach dem Speichern eines Accounts
 * diesen an die dafür vorgesehene vorgesehene
 * Websocketschnittstelle sendet
 * Created by Martin Petzold on 31.01.2017.
 */
@Component
@Aspect
public class SaveAccountAspect {

    @Autowired
    BroadCastNewAccountsController broadCastNewAccountsController;
    @Pointcut("execution(* Order.Repositories.AccountRepository.saveAndFlush(..))")
    public void accountHasSaved(){
    }

    /**
     * Sendet den Account an den Websocket
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "accountHasSaved()",returning = "returnValue")
    public void broadcast(JoinPoint joinPoint,Object returnValue) {
        if(returnValue instanceof Account){
            broadCastNewAccountsController.broadcastAccount(new AccountBroadcastDto(((Account) returnValue).getId(),((Account) returnValue).getName(),((Account) returnValue).getMail()));
        }


    }


}
