package com.csmarton.load.balancer;

public class Task {
    private int remaingCheck;

    public Task() {
        this.remaingCheck = 30;
    }

    public int check() {
        return  --remaingCheck;
    }
}
