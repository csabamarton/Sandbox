package com.csmarton.loadbalancer;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer {

    protected RandomLoadBalancer(List<String> ipList) {
        super(ipList);
    }

    @Override
    String getIp() {
        Random random = new Random();

        return ipList.get(random.nextInt(ipList.size()));
    }
}
