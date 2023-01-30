package com.csmarton.load.balancer;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LoadBalancer {

    private ExecutorService executorService;
    private Collection<Server> servers = Collections.synchronizedCollection(new ArrayList<>());

    public LoadBalancer(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Future<String> assignTaskParallel(Task task) {
        Callable<String> callable = () -> assignTask(task);

        return executorService.submit(callable);
    }

    public  class ServerComparator implements Comparator<Server> {
        @Override
        public int compare(Server o1, Server o2) {
            if (o1.getNumOfTasks() < o2.getNumOfTasks())
                return -1;
            else if (o1.getNumOfTasks() > o2.getNumOfTasks())
                return 1;
            return 0;
        }
    }

    public void initLoadBalancer(Collection<Server> serverList) {
        this.servers = Collections.synchronizedCollection(serverList);
    }

    public String assignTask(Task task) {
        cleanServers();
        Server server = findTheLeastBusyServer();
        server.delegateTask(task);

        return server.getIpAddress();
    }

    private void cleanServers() {
        servers.stream().forEach(server -> server.clean());
    }

    private Server findTheLeastBusyServer() {
        return servers.stream().parallel().min(new ServerComparator()).get();
    }

}
