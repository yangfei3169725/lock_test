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
			System.out.println(Thread.currentThread().getName() + "���Ի�ȡ����" + lock.isHeldByCurrentThread());
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + "��ȡ������" + lock.isHeldByCurrentThread());
				try {
					if (i > 0) {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName() + "������Ʊ����Ʊ��" + --i);
						lock.wait();
						System.out.println("׼����һ����Ʊ��");
					} else {
						System.out.println(Thread.currentThread().getName() + "Ʊ�Ѿ������˳���");
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
//					System.out.println(Thread.currentThread().getName() + "δ��ȡ������" + lock.isHeldByCurrentThread());
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}