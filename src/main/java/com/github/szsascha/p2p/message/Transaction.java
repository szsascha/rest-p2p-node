package com.github.szsascha.p2p.message;

public class Transaction implements Message {

    private String data;

    Transaction() { }

    @Override
    public String getType() {
        return "Transaction";
    }

    @Override
    public Long getVersion() {
        return 100l;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
