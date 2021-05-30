package com.github.szsascha.p2p.message;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "version" })
public interface Message {
    
    String getType();

    Long getVersion();
}
