package com.so4it.domain;

import java.util.Objects;

public class Account {

    //
    private Long id;

    private Double balance;

    public Account(Builder builder) {
        this.id = Objects.requireNonNull(builder.id);
        this.balance = Objects.requireNonNull(builder.balance);
    }

    public Long getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private Long id;

        private Double balance;


        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withBalance(Double balance){
            this.balance = balance;
            return this;
        }


        public Account build(){
            return new Account(this);
        }
    }
}
