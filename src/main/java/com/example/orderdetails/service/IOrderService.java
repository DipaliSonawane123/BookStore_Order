package com.example.orderdetails.service;

import com.example.orderdetails.dto.OrderDto;
import com.example.orderdetails.model.Order;

import java.util.List;

public interface IOrderService {
    String addOrder(OrderDto orderdto);

    List<Order> getall();

    Order FindById(int id);

    void deleteById(int id);

   String editById(int id, OrderDto orderDto);
}

