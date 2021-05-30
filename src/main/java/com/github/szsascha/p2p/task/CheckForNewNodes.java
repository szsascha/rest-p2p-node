package com.github.szsascha.p2p.task;

import com.github.szsascha.p2p.service.P2PService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckForNewNodes {

    @Autowired
    private P2PService p2pservice;

    @Scheduled(fixedDelay = 300000)
    public void schedule() {
        this.p2pservice.nodeScan();
    }
    
}
