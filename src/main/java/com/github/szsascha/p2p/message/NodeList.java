package com.github.szsascha.p2p.message;

import java.util.ArrayList;
import java.util.List;

public class NodeList implements Message {
 
    private List<String> nodes = new ArrayList<String>();

    NodeList() { }

    @Override
    public String getType() {
        return "NodeList";
    }

    @Override
    public Long getVersion() {
        return 100l;
    }

    public List<String> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

}
