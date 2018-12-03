package com.yang;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
	
	public static void main(String[] args) {
		
		
		TestReadWriteLock tReadWriteLock = new TestReadWriteLock();
		for(int i = 0;i<10;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					tReadWriteLock.write();
				}
			},"Ð´"+i).start();
		}
		
		for(int i = 0;i<50;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					tReadWriteLock.read();
				}
			},"¶Á"+i).start();
		}
		
		
	}

	int number =0;
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	public void read() {
		readWriteLock.readLock().lock();;
		try {
		System.out.println(Thread.currentThread().getName()+":------------"+number+"-------"+System.currentTimeMillis());
		}finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	public void write() {
		readWriteLock.writeLock().lock();
		try {
			number = (int)(Math.random()*101);
			System.out.println(Thread.currentThread().getName()+"---------"+System.currentTimeMillis());
		}finally {
			readWriteLock.writeLock().unlock();
		}
	}
}


