package Order.Aspects;

import Order.Controller.BroadcastNewOrdersController;
import Order.DTOs.ItemSetStubBroadcastDto;
import Order.DTOs.OrderBroadcastDto;
import Order.Entities.ItemSet;
import Order.Entities.OrderConcepts.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aismael on 31.01.2017.
 */
public class SaveOrderAspect {
    @Autowired
    BroadcastNewOrdersController broadcastNewOrdersController;
    @Pointcut("execution(* Order.Repositories.OrderConcepts.OrderRepository.saveAndFlush(..))")
    public void orderHasSaved(){
    }

    @AfterReturning(pointcut = "orderHasSaved()",returning = "returnValue")
    public void broadcast(JoinPoint joinPoint, Object returnValue) {
        if(returnValue instanceof Order){
            ArrayList<ItemSetStubBroadcastDto> itemSetStubDtos=new ArrayList<>();
            for (ItemSet is :((Order) returnValue).getItemSets().getAll()) {
                ItemSetStubBroadcastDto i=new ItemSetStubBroadcastDto(is.getCount(),is.getId(),is.getItem().getOne().getName());
                itemSetStubDtos.add(i);
            }
            broadcastNewOrdersController.broadcastOrder(
                    new OrderBroadcastDto(
                            ((Order) returnValue).getId(),
                            ((Order) returnValue).getAccount().getOne().getId(),
                            true,((Order) returnValue).getDate(),
                            itemSetStubDtos
                    ));
        }


    }
}
