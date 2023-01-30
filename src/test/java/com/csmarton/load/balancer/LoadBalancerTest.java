package com.csmarton.load.balancer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

class LoadBalancerTest {
    private LoadBalancer loadBalancer;

    @BeforeEach
    void setUp() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        loadBalancer = new LoadBalancer(executorService);
    }

    @Test
    void testAdding100Task() throws ExecutionException, InterruptedException {
        int numOfServers = 10;

        Collection<Server> servers = Collections.synchronizedCollection(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            servers.add(new Server("192.168.1." + i));
        }
        //init
        loadBalancer.initLoadBalancer(servers);

        List<Future<String>> futures = new ArrayList<>();
        IntStream.range(0,100).forEach(i -> {
            Future<String> future = loadBalancer.assignTaskParallel(new Task());
            futures.add(future);
        });

        boolean thereIsUndone = true;
        while (thereIsUndone)
        {
            thereIsUndone = false;
            Iterator<Future<String>> iter = futures.iterator();
            while (iter.hasNext())
            {
                Future<String> future = iter.next();

                if (future.isDone())
                {
                    iter.remove();
                    // Calling get to see if there was an exception
                    System.out.println("Server was used: " + future.get());
                }
                else
                {
                    thereIsUndone = true;
                }
            }

            if (thereIsUndone)
            {
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }

        System.out.println("End");
    }
}