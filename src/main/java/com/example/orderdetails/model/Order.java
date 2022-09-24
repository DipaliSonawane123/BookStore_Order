package com.example.orderdetails.model;

import com.example.orderdetails.dto.OrderDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


    @Entity
    @Data
    @NoArgsConstructor
    @Table(name="Book_Order")
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "orderId", nullable = false)
        int orderID;
        LocalDate date = LocalDate.now();
        int price;
        int quantity;
        String address;
        //        @OneToOne
//        @JoinColumn(name="User_id")
        int userid;
        //        @ManyToOne
//        @JoinColumn(name="book_Id")
        int bookid;
        boolean cancel;





            public Order(LocalDate date, int price, int quantity, String address, int userid, int bookId, boolean cancel) {
                this.date=date;
                this.price=price;
                this.quantity=quantity;
                this.address=address;
                this.userid=userid;
                this.bookid=bookId;
                this.cancel=cancel;
            }

        public Order(OrderDto orderdto) {
            this.date=orderdto.getDate();
            this.price=orderdto.getPrice();
            this.quantity=orderdto.getQuantity();
            this.address=orderdto.getAddress();
            this.userid=orderdto.getUserid();
            this.bookid=orderdto.getBookid();
            this.cancel= orderdto.isCancel();

        }
        }


