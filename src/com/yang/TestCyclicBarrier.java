package com.yang;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.sound.midi.Soundbank;

public class TestCyclicBarrier {
	public static void main(String[] args) throws InterruptedException {
		int n = 4;
		CyclicBarrier barrier = new CyclicBarrier(n);
		
		for(int i=0;i<n;i++) {
			new Writer(barrier).start();
		}
		
		Thread.sleep(6000);
		System.out.println("����....");
		
		for(int i=0;i<n;i++) {
			new Writer(barrier).start();
		}
		
	}
	
	static class Writer extends Thread{
		private CyclicBarrier cyclicBarrier;
		public Writer(CyclicBarrier barrier) {
			this.cyclicBarrier = barrier;
		}
		@Override
		public void run() {
			System.out.println("�̣߳�"+Thread.currentThread().getName()+"����д������");
			try {
				Thread.sleep(5000);
				System.out.println("�̣߳�"+Thread.currentThread().getName()+"д��������ϣ��ȴ������߳�д�����");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("�����߳�д����ϣ�����������������");
		}
	}

}
