package com.github.szsascha.p2p.message;

public class MessageFactory {
   
    public static NodeStatus createNodeStatus() {
        return new NodeStatus();
    }

    public static JoinNetwork createJoinNetwork() {
        return new JoinNetwork();
    }

    public static NodeList createNodeList() {
        return new NodeList();
    }

    public static Transaction createTransaction() {
        return new Transaction();
    }

}
