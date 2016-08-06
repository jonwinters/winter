package top.sourcecode.winter.common.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ReaderWriter {
	private int readerCount;
	private Semaphore wmutex;//优先写
	private Semaphore rwmutex;//读者与写者之间互斥
	private Semaphore readerCountMutex;//读者更新readerCount时互斥
	
	public ReaderWriter() {
		readerCount = 0;
		wmutex = new Semaphore(1);
		rwmutex = new Semaphore(1);
		readerCountMutex = new Semaphore(1);
	}
	
	private class Reader implements Runnable {

		public void read() {
			try {
				wmutex.acquire();
				readerCountMutex.acquire();
				if(readerCount == 0) {
					rwmutex.acquire();
				}
				++readerCount;
				readerCountMutex.release();
				wmutex.release();
				System.out.println(Thread.currentThread().getName() + " is reading.");
				TimeUnit.MICROSECONDS.sleep(new Random().nextInt(10) * 10);
				readerCountMutex.acquire();
				--readerCount;
				if(readerCount == 0) {
					rwmutex.release();
				}
				readerCountMutex.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			read();
		}
		
	}
	
	private class Writer implements Runnable {

		public void write() {
			try {
				wmutex.acquire();
				rwmutex.acquire();
				System.out.println(Thread.currentThread().getName() + " is writing.");
				TimeUnit.MICROSECONDS.sleep(new Random().nextInt(100) * 1000);
				rwmutex.release();
				wmutex.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			write();
		}
		
	}
	
	public static void main(String[] args) {
		int readerNum = 5;
		int writerNum = 2;
		ReaderWriter rw = new ReaderWriter();
		Reader reader = rw.new Reader();
		Writer writer = rw.new Writer();
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < writerNum; ++i) {
			exec.execute(writer);
		}
		for(int i = 0; i < readerNum; ++i) {
			exec.execute(reader);
		}
		exec.shutdown();
	}
}
