package Order.DTOs;

import Order.Entities.OrderConcepts.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 19.04.2017.
 */

public class OrderDTOList {
    ArrayList<OrderDTO> list = new ArrayList<>();

    public OrderDTOList(List<Order> orderList) {
        this.setFavoriteList(orderList);
    }

    public ArrayList<OrderDTO> getList() {
        return list;
    }

    public void setFavoriteList(List<Order> orderList) {
        for (Order order : orderList) {
            list.add(new OrderDTO(order));
        }
    }
}