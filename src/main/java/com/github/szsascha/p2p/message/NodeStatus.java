package com.github.szsascha.p2p.message;

public class NodeStatus implements Message {

    private String address;

    NodeStatus() { }

    @Override
    public String getType() {
        return "NodeStatus";
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
