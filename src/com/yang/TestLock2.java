package com.yang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock2 {

	public static void main(String[] args) {
		sale2 s = new sale2();
		Thread s1 = new Thread(s, "1--");
		Thread s2 = new Thread(s, "2--");
		Thread s3 = new Thread(s, "3--");
		Thread s4 = new Thread(s, "4--");
		Thread s5 = new Thread(s, "5--");
		Thread s6 = new Thread(s, "6--");
		s1.start();
		s2.start();
		s3.start();
		s4.start();
		s5.start();
		s6.start();
		
		
	}
}

class sale2 implements Runnable {
	int i = 100;
	ReentrantLock lock = new ReentrantLock(true);
	@Override
	public void run() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + "尝试获取锁：" + lock.isHeldByCurrentThread());
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + "获取到锁：" + lock.isHeldByCurrentThread());
				try {
					if (i > 0) {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName() + "正在卖票，余票：" + --i);
						lock.wait();
						System.out.println("准备下一次卖票：");
					} else {
						System.out.println(Thread.currentThread().getName() + "票已经卖完退出：");
						return;
					}
				} catch (Exception e) {
					System.out.println(Thread.currentThread().getName()+":"+e.getMessage() );
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
//				}else {
//					try {
//						lock.lockInterruptibly();
//					} catch (InterruptedException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
//					System.out.println(Thread.currentThread().getName() + "未获取到锁：" + lock.isHeldByCurrentThread());
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}