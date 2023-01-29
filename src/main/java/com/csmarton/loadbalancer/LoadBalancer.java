package com.csmarton.loadbalancer;

import java.util.List;

public abstract class LoadBalancer {
    final List<String> ipList;


    protected LoadBalancer(List<String> ipList) {
        this.ipList = ipList;
    }

    abstract String getIp();
}
