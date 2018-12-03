package com.yang;

public class TestLock {
	public static void main(String[] args) throws InterruptedException {
		sale s = new sale();
		Thread s1 = new Thread(s, "1--");
		Thread s2 = new Thread(s, "2--");
		Thread s3 = new Thread(s, "3--");
		s1.start();
		s2.start();
		s3.start();
	}

}

class sale implements Runnable {
	int i = 100;

	@Override
	public void run() {
		while (i > 0) {
			System.out.println(Thread.currentThread().getName()+"��ȡ������" + Thread.holdsLock(this));
			synchronized(this) {
				if(!Thread.currentThread().isInterrupted()) {
					System.out.println(Thread.currentThread().getName() + "��ȡ������" + Thread.holdsLock(this));
					System.out.println(Thread.currentThread().getName() + "������Ʊ����Ʊ��" + --i);
				}else {
					System.out.println(Thread.currentThread().getName()+"�߳��Ѿ��ж�");
					break;
				}
				
			}
		}
	}
}