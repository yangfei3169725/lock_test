package com.yang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class FutureTaskTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTaskTest inst = new FutureTaskTest();
		
		List<FutureTask<Integer>> ftList = new ArrayList<>();
		ExecutorService exec = Executors.newFixedThreadPool(5);
		for(int i = 0;i<10;i++) {
			FutureTask<Integer> ft = new FutureTask<Integer>( inst.new ComputeTask(0, "线程"));
			ftList.add(ft);
			exec.submit(ft);
			Object num = ft.get();
			System.out.println(num);
			
		}

		System.out.println("所有计算任务提交完毕，主线程接着干其他事");
		Integer totalResult = 0;
		for(FutureTask<Integer> ft : ftList) {
			totalResult+=ft.get();
		}
		exec.shutdown();
		System.out.println("多任务计算总结果："+totalResult);
	}

	private class ComputeTask implements Callable<Integer> {
		private Integer result = 0;
		private String taskName = "";
		   public ComputeTask(Integer iniResult, String taskName){
	            result = iniResult;
	            this.taskName = taskName;
	            System.out.println("生成子线程计算任务: "+taskName);
	        }

		@Override
		public Integer call() throws Exception {
			for (int i = 0; i < 100; i++) {
				result =+ i;
			}
			Thread.sleep(5000);
			System.out.println("子线程计算任务: " + taskName + " 执行完成!");
			return result;

		}

	}
}
