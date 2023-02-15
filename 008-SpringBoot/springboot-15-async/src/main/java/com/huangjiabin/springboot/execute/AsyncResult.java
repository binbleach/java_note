package com.huangjiabin.springboot.execute;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
    线程执行结果类
*/
public class AsyncResult<V> implements Future {
    private final V value;

    public AsyncResult(V value) {
        this.value = value;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() {
        return this.value;
    }

    @Override
    public V get(long timeout, TimeUnit unit){
        return this.value;
    }
}
