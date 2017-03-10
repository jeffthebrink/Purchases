package com.theironyard;

import javax.persistence.*;


@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    int id;

    @Column(nullable = false)
    String date;


    @Column(nullable = false)
    String creditCard;

    @Column(nullable = false)
    String cvv;

    @Column(nullable = false)
    String category;


    @ManyToOne
    Customer customer;

    public Purchase() {
    }

    public Purchase(String date, String creditCard, String cvv, String category, Customer customer) {
        this.category = category;
        this.creditCard = creditCard;
        this.cvv = cvv;
        this.date = date;
        this.customer = customer;
    }
}
