package com.example.bohaiservicedome.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadManager {

	private static ThreadPoolExecutor mThreadPool;
	
	/**
	 * 获取线程池
	 * @return
	 */
	private static synchronized ThreadPoolExecutor getThreadPool() {

		if (mThreadPool == null) {
			mThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		}
		return mThreadPool;
	}

	public static void execute(Runnable runnable) {
		if (runnable == null) {
			return;
		}
		getThreadPool().execute(runnable);
	}
	
	/** 关闭线程池 **/
	public static synchronized void cancelTask() {
		if (mThreadPool != null) {
			mThreadPool.shutdownNow();
			mThreadPool = null;
		}
	}
}
