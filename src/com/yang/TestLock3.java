package com.yang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock3 {
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		Productor productor = new Productor(clerk);
		Consumer consumer = new Consumer(clerk);
		new Thread(productor, "生产者A").start();
		new Thread(consumer, "消费者B").start();
		
		new Thread(productor, "生产者A").start();
		new Thread(consumer, "消费者B").start();
	}
}

class Clerk {
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	private int i = 0;

	public void get() {
		try {
			lock.lock();
			while (i >= 1) {
				System.out.println("产品已满");
				try {
					condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ":" + ++i);
			condition.signalAll();

		} finally {
			lock.unlock();
		}
	}

	public void sale() {
		try {
			lock.lock();
			while (i <= 0) {
				System.out.println("缺货");
				try {
					condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ":" + --i);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
}

class Productor implements Runnable {
	private Clerk clerk;

	public Productor(Clerk clerk) {
		super();
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.get();
		}
	}
}

class Consumer implements Runnable {
	private Clerk clerk;

	public Consumer(Clerk clerk) {
		super();
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}

}
