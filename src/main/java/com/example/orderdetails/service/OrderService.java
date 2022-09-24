package com.example.orderdetails.service;

import com.example.orderdetails.dto.OrderDto;
import com.example.orderdetails.exception.OrderException;
import com.example.orderdetails.model.BookData;
import com.example.orderdetails.model.Order;
import com.example.orderdetails.model.UserData;
import com.example.orderdetails.repo.OrderRepo;
import com.example.orderdetails.util.EmailSenderService;
import com.example.orderdetails.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService
{
    @Autowired
    OrderRepo orderRepo;

    @Autowired
TokenUtil tokenUtil;
    @Autowired
    EmailSenderService emailSender;

@Autowired
    RestTemplate restTemplate;

    @Override
    public String addOrder(OrderDto orderdto) {
        UserData userDetails = restTemplate.getForObject("http://localhost:8081/user/gets/"+orderdto.getUserid(), UserData.class);
        System.out.println(userDetails.toString());
        BookData bookDetails = restTemplate.getForObject("http://localhost:8082/book/gets/"+orderdto.getBookid(), BookData.class);
        System.out.println(bookDetails.toString());
        if (userDetails.equals(null) &&bookDetails.equals(null))
            throw new OrderException(" userid and bookid is invalid");
        else {
            Order orderDetails = new Order(orderdto);
            orderRepo.save(orderDetails);
            String token = tokenUtil.createToken(userDetails.getUserId());
            emailSender.sendEmail(userDetails.getEmail(), "Added Your Details", "http://localhost:8081/user/retrieve/"+token);
            return token;
        }
    }

    @Override
    public List<Order> getall() {
        List<Order> order = orderRepo.findAll();
        return order;
    }

    @Override
    public Order FindById(int id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.get();
    }

    @Override
    public void deleteById(int id) {
        Optional<Order> findById = orderRepo.findById(id);
        if (findById.isPresent()){
            orderRepo.deleteById(id);
        } else throw new OrderException("Id:"+id+" not present");
    }

    @Override
    public String editById(int id, OrderDto orderDto) {
        UserData userDetails = restTemplate.getForObject("http://localhost:8081/user/gets/"+orderDto.getUserid(), UserData.class);
        System.out.println(userDetails);
        BookData bookDetails = restTemplate.getForObject("http://localhost:8082/book/get/"+orderDto.getBookid(), BookData.class);
        System.out.println(bookDetails);
        Order editorder = orderRepo.findById(id).orElse(null);
        if (userDetails.equals(null)&&bookDetails.equals(null)&&editorder.equals(null))
            throw new OrderException(" id is invalid");
            else{
            editorder.setPrice(orderDto.getPrice());
            editorder.setQuantity(orderDto.getQuantity());
            editorder.setAddress(orderDto.getAddress());
            editorder.setUserid(orderDto.getUserid());
            editorder.setBookid(orderDto.getBookid());
            editorder.setCancel(orderDto.isCancel());
            orderRepo.save(editorder);
            String token = tokenUtil.createToken(editorder.getOrderID());
            emailSender.sendEmail(userDetails.getEmail(), "Added Your Details", "http://localhost:8080/user/retrieve/" + token);
            return token;
        }
    }
}


