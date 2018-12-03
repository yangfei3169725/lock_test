package com.yang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class CountDownLachTest implements Callable<Integer>{
//	private CountDownLatch countDownLatch = new CountDownLatch(6);
	private CyclicBarrier countDownLatch = new CyclicBarrier(6);
	public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {
		System.out.println("正式提交git123aa");
		CountDownLachTest countDownLachTest = new CountDownLachTest();
		ExecutorService executorService = Executors.newFixedThreadPool(6);
		List<FutureTask> futureTaskList = new ArrayList<>();
		for(int i=0;i<6;i++) {
			FutureTask<Integer> futureTask = new FutureTask<>(countDownLachTest);
			executorService.submit(futureTask);
			futureTaskList.add(futureTask);
			System.out.println("正在创建线程任务...");
//			countDownLachTest.countDownLatch.countDown();
//			countDownLachTest.countDownLatch.await();
		}
		System.out.println("所有线程创建完毕");
		executorService.shutdown();
		for (int i = 0; i < futureTaskList.size(); i++) {
			System.out.println(futureTaskList.get(i).get());
		}
	}
	@Override
	public Integer call() throws Exception {
		countDownLatch.await();
		System.out.println(Thread.currentThread().getName()+"线程开始执行....");
		int result = 0;
		for(int i=0;i<=100;i++) {
			result+=i;
		}
		Thread.sleep(2000);
		return result;
	}

}
