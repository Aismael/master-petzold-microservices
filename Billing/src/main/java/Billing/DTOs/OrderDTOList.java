package Billing.DTOs;


import Billing.Entities.XOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 19.04.2017.
 */

public class OrderDTOList {
    public ArrayList<OrderDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderDTO> orderList) {
        this.orderList = orderList;
    }

    ArrayList<OrderDTO> orderList = new ArrayList<>();

    @Override
    public String toString() {
        return "OrderDTOList{" +
                "orderList=" + orderList.toString() +
                '}';
    }

    public OrderDTOList() {
    }



    public void setOrderList(List<XOrder> orderList) {
        for (XOrder order : orderList) {
            this.orderList.add(new OrderDTO(order));
        }
    }
}