package com.github.szsascha.p2p.message;

public class JoinNetwork implements Message {

    private String address;

    JoinNetwork() { }

    @Override
    public String getType() {
        return "JoinNetwork";
    }

    @Override
    public Long getVersion() {
        return 100l;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
