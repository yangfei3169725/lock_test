package com.yang;

public class ThreadLocalTest {
	public static void main(String[] args) {
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		int i = 0;
		new Thread(new Runnable() {
			@Override
			public void run() {
				String string = threadLocal.get();
				if(string==null) {
					threadLocal.set(Thread.currentThread().getName());
				}
				System.out.println(threadLocal.get());
			}
		}).start();
		
		
	}
}
