package com.github.szsascha.p2p.service;

import java.util.List;

import com.github.szsascha.p2p.message.JoinNetwork;
import com.github.szsascha.p2p.message.NodeList;
import com.github.szsascha.p2p.message.NodeStatus;
import com.github.szsascha.p2p.message.Transaction;

public interface P2PService {
    
    void startup();

    NodeStatus status();

    boolean joinNetwork(JoinNetwork joinNetwork);

    void cleanupNodelist();

    void nodeScan();

    boolean addTransaction(Transaction transaction);

    String[] getSeedNodes();

    List<Transaction> getTransactions();

    NodeList getNodeNodelist();

}
