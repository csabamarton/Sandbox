package com.csmarton.load.balancer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {

    private String ipAddress;
    private List<Task> tasks = new ArrayList<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Server(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getNumOfTasks() {
        lock.readLock().lock();
        try {
            return tasks.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void delegateTask(Task task) {
        lock.writeLock().lock();
        try {
            tasks.add(task);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clean() {
        lock.writeLock().lock();

        try {
            Iterator<Task> iterator = tasks.iterator();

            while (iterator.hasNext()) {
                if (iterator.next().check() <= 0) {
                    iterator.remove();
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
