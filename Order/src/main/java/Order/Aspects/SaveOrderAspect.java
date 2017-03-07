package Order.Aspects;

import Order.Controller.BroadcastNewOrdersController;
import Order.DTOs.ItemSetStubBroadcastDto;
import Order.DTOs.OrderBroadcastDto;
import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * Aspect der nach dem Speichern einer Bestellung
 * diesen an die dafür vorgesehene vorgesehene
 * Websocketschnittstelle sendet
 * Created by Martin Petzold on 31.01.2017.
 */
@Component
@Aspect
public class SaveOrderAspect {
    @Autowired
    BroadcastNewOrdersController broadcastNewOrdersController;
    @Pointcut("execution(* Order.Repositories.OrderConcepts.OrderRepository.saveAndFlush(..))")
    public void orderHasSaved(){
    }

    /**
     * wandelt die Bestellung in ein DTO um und
     * Sendet dises an den Websocket
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "orderHasSaved()",returning = "returnValue")
    public void broadcast(JoinPoint joinPoint, Object returnValue) {
        if(returnValue instanceof Order){
            ArrayList<ItemSetStubBroadcastDto> itemSetStubDtos=new ArrayList<>();
            for (ItemSet is :((Order) returnValue).getItemSets().getAll()) {
                ItemSetStubBroadcastDto i=new ItemSetStubBroadcastDto(is.getCount(),is.getId(),is.getItem().getOne().getName(),is.getItem().getOne().getCurrency());
                itemSetStubDtos.add(i);
            }
            broadcastNewOrdersController.broadcastOrder(
                    new OrderBroadcastDto(
                            ((Order) returnValue).getId(),
                            ((Order) returnValue).getAccount().getOne().getId(),
                            true,
                            ((Order) returnValue).getDate(),
                            itemSetStubDtos
                    ));
        }


    }
}
