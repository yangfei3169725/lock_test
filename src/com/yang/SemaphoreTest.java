package com.yang;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	public static void main(String[] args) {
		int n = 8;
		Semaphore semaphore = new Semaphore(5);
		
		for(int i =0;i<n;i++) {
			new Worker(semaphore,i).start();
		}
	}
	static class Worker extends Thread{
		private Semaphore semaphore;
		private int num;
		public Worker(Semaphore semaphore,int num) {
			this.semaphore = semaphore;
			this.num = num; 
		}
		
		
		@Override
		public void run() {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(num+"号工人:"+Thread.currentThread().getName()+":"+"正在操作资源");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(num+"号工人:"+Thread.currentThread().getName()+":释放资源");
			semaphore.release();
		}
	}

}
