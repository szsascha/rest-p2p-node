package com.github.szsascha.p2p.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.szsascha.p2p.message.JoinNetwork;
import com.github.szsascha.p2p.message.NodeList;
import com.github.szsascha.p2p.message.NodeStatus;
import com.github.szsascha.p2p.message.Transaction;
import com.github.szsascha.p2p.service.P2PService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v1")
public class P2PController {   

    @Autowired
    private P2PService p2pservice;

    @PostConstruct
    private void init() {
        this.p2pservice.startup();
    }

    @GetMapping("/node/status")
    public NodeStatus getNodeStatus(HttpServletRequest request) {
        return this.p2pservice.status();
    }

    @GetMapping("/node/nodelist")
    public NodeList getNodeNodelist() {
        return this.p2pservice.getNodeNodelist();
    }

    @PostMapping("/node/transaction")
    public void postTransaction(@RequestBody Transaction transaction, HttpServletResponse response) {
        if (this.p2pservice.addTransaction(transaction)) {
            response.setStatus(201);
            return;
        }
        response.setStatus(500);
    }

    @GetMapping("/node/transactions")
    public List<Transaction> getNodeTransactions() {
        return this.p2pservice.getTransactions();
    }

    @PostMapping("/network/join")
    public void postNetworkJoin(@RequestBody JoinNetwork joinNetwork, HttpServletResponse response) {
        if (this.p2pservice.joinNetwork(joinNetwork)) {
            response.setStatus(201);
            return;
        }
        response.setStatus(500);
    }

}