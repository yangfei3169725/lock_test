package com.yang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCAlternate {
	public static void main(String[] args) {
		AlernateDemo alernateDemo = new AlernateDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					try {
						alernateDemo.loopA(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "A").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					try {
						alernateDemo.loopB(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "B").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					try {
						alernateDemo.loopC(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "C").start();
	}
}

class AlernateDemo {
	Lock lock = new ReentrantLock();
	Condition conditionA = lock.newCondition();
	Condition conditionB = lock.newCondition();
	Condition conditionC = lock.newCondition();
	Condition conditionD = lock.newCondition();
	int i = 1;

	public void loopA(int totl) throws InterruptedException {
		lock.lock();
		try {
			if (i != 1) {
				conditionA.await();
			}

			if (i == 1) {
				for (int i = 0; i < 1; i++) {
					System.out.println("A" + "\t" + totl);
				}
				i = 2;
				conditionB.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	public void loopB(int totl) throws InterruptedException {
		lock.lock();
		try {
			if (i != 2) {
				conditionB.await();
			}

			if (i == 2) {
				for (int i = 0; i < 1; i++) {
					System.out.println("B" + "\t" + totl);
				}
				i = 3;
				conditionC.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	public void loopC(int totl) throws InterruptedException {
		lock.lock();
		try {
			if (i != 3) {
				conditionC.await();
			}
			if (i == 3) {
				for (int i = 0; i < 1; i++) {
					System.out.println("C" + "\t" + totl);
				}
				System.out.println("--------------------------------------");
				i = 1;
				conditionA.signal();
			}
		} finally {
			lock.unlock();
		}
	}
}