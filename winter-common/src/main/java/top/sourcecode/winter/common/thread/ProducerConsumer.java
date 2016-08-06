package top.sourcecode.winter.common.thread;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumer {

    private final int itemsNo;
    private Stack<Integer> stack;
    private final int STACK_SIZE;

    public ProducerConsumer(int itemsNo, int stackSize) {
    	this.itemsNo = itemsNo;
    	this.STACK_SIZE = stackSize;
    	this.stack = new Stack<Integer>();
    }
    
    private class Producer implements Runnable {

    	private int count;
    	
    	public Producer() {
    		this.count = 0;
    	}
		public void run() {
			while(true) {
				synchronized (stack) {
					while(stack.size() >= STACK_SIZE) {
						try {
							stack.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(count < itemsNo) {
						produce();
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						stack.notifyAll();
					} else {
						break;
					}
				}
			}
		}
    	
		private void produce() {
			stack.push(++count);
			System.out.println(Thread.currentThread().getName() + " is producing item " + count);
		}
    }
    
    private class Consumer implements Runnable {

    	private int count;
    	
    	public Consumer() {
    		this.count = 0;
    	}
    	
		public void run() {
			while(true) {
				synchronized (stack) {
					while(stack.empty() && !isFinished()) {
						try {
							stack.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					consume();
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stack.notifyAll();
					//执行notify后，依然要把下面的代码执行完才会真正解锁。
					if(isFinished()) {
						break;
					}
				}
			}
		}
    	
		public boolean isFinished() {
			return count == itemsNo;
		}
		
		private void consume() {
			if(isFinished()) {
				return;
			}
			System.out.println(Thread.currentThread().getName() + " is consuming item " + stack.pop());
			++count;
		}
    }
    
    public static void main(String[] args) {
    	ExecutorService exec = Executors.newCachedThreadPool();
		ProducerConsumer pc = new ProducerConsumer(10, 3);
		Producer producer = pc.new Producer();
		Consumer consumer = pc.new Consumer();
		int producerNo = 3;
		int consumerNo = 2;
		for(int i = 0; i < producerNo; ++i) {
			exec.execute(producer);
		}
		for(int i = 0; i < consumerNo; ++i) {
			exec.execute(consumer);
		}
		exec.shutdown();
	}
}
