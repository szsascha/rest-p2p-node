package com.github.szsascha.p2p.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.github.szsascha.p2p.controller.P2PController;
import com.github.szsascha.p2p.message.JoinNetwork;
import com.github.szsascha.p2p.message.MessageFactory;
import com.github.szsascha.p2p.message.NodeList;
import com.github.szsascha.p2p.message.NodeStatus;
import com.github.szsascha.p2p.message.Transaction;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class P2PServiceImpl implements P2PService {

    @Autowired
    private Environment environment;

    private List<String> nodes = new ArrayList<String>();

    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Override
    public void startup() {
        // Join network
        JoinNetwork joinNetwork = MessageFactory.createJoinNetwork();
        joinNetwork.setAddress(this.getServerUrl());

        RestTemplate restTemplate = new RestTemplate();
        for (String node : this.getSeedNodes()) {
            String url = node;
            if (!url.endsWith("/")) url += '/';
            url += "network/join";
 
            try {
                ResponseEntity<String> result = restTemplate.postForEntity(url, joinNetwork, String.class);
                this.nodes.add(url.replaceAll("/network/join", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public NodeStatus status() {
        NodeStatus nodeStatus = MessageFactory.createNodeStatus();
        nodeStatus.setAddress(this.getServerUrl());
        return nodeStatus;
    }

    @Override
    public boolean joinNetwork(JoinNetwork joinNetwork) {
        if (joinNetwork.getAddress().equalsIgnoreCase(getServerUrl())) return false;

        for (String node : nodes) {
            if (joinNetwork.getAddress().equalsIgnoreCase(node)) return false;
        }
        
        this.nodes.add(joinNetwork.getAddress());

        return true;
    }

    @Override
    public void cleanupNodelist() {
        List<String> nodesToRemove = new ArrayList<String>();  
        RestTemplate restTemplate = new RestTemplate();
        for (String node : this.nodes) {
            String url = node;
            if (!url.endsWith("/")) url += '/';
            url += "node/status";
 
            try {
                ResponseEntity<NodeStatus> result = restTemplate.getForEntity(url, NodeStatus.class);
            } catch (Exception e) {
                nodesToRemove.add(node);
                e.printStackTrace();
            }
        }

        for (String nodeToRemove : nodesToRemove) {
            this.nodes.remove(nodeToRemove);
            System.out.println("Node removed! " + nodeToRemove);
        }
    }

    @Override
    public void nodeScan() {
        List<String> nodesToAdd = new ArrayList<String>();
        RestTemplate restTemplate = new RestTemplate();
        for (String node : this.nodes) {
            String url = node;
            if (!url.endsWith("/")) url += '/';
            url += "node/nodelist";
 
            try {
                ResponseEntity<NodeList> result = restTemplate.getForEntity(url, NodeList.class);
                nodesToAdd.addAll(result.getBody().getNodes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        outer: for (String nodeToAdd : nodesToAdd) {
            if (nodeToAdd.equalsIgnoreCase(getServerUrl())) continue;
            for (String node : nodes) {
                if (nodeToAdd.equalsIgnoreCase(node)) continue outer;
            }

            String url = nodeToAdd;
            if (!url.endsWith("/")) url += '/';
            url += "node/status";

            try {
                ResponseEntity<NodeStatus> result = restTemplate.getForEntity(url, NodeStatus.class);
                this.nodes.add(nodeToAdd);
                System.out.println("Node found! " + nodeToAdd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        for (Transaction t : transactions) {
            if (t.getData().equalsIgnoreCase(transaction.getData())) return false;
        }
        
        this.transactions.add(transaction);

        // Propagate transaction
        RestTemplate restTemplate = new RestTemplate();
        for (String node : nodes) {
            String url = node;
            if (!url.endsWith("/")) url += '/';
            url += "node/transaction";
 
            try {
                ResponseEntity<String> result = restTemplate.postForEntity(url, transaction, String.class);
            } catch (Exception e) {

            }
        }

        return true;
    }

    @Override
    public String[] getSeedNodes() {
        return new String[] { "http://localhost:8080/v1", "http://localhost:8081/v1" };
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public NodeList getNodeNodelist() {
        NodeList nodeList = MessageFactory.createNodeList();
        nodeList.getNodes().addAll(this.nodes);
        return nodeList;
    }

    private String getServerUrl() {
        String hostAddress = "";
        
        String port = this.environment.getProperty("server.port");
        if (port == null) port = this.environment.getProperty("local.server.port");
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // No https support atm
        return "http://" + hostAddress + ":" + port + P2PController.class.getAnnotation(RequestMapping.class).value()[0];
    }

}
