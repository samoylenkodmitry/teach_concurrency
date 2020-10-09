import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Atomicity_19 {
	
	public static void main(String... args) {
		final UnsafeCountingFactorizer f = new UnsafeCountingFactorizer();
		CyclicBarrier br = new CyclicBarrier(3);
		new Thread(()->{
			for (int i = 0; i < 10000; i++) {
				f.service();
			}
			try {
				br.await();
			} catch (Throwable e) {
			}
		}).start();
		new Thread(()->{
			for (int i = 0; i < 10000; i++) {
				f.service();
			}
			try {
				br.await();
			} catch (Throwable e) {
			}
		}).start();
		try {
			br.await();
		} catch (Throwable e) {
		}
		System.out.println(f.getCount());
	}
	void main1(String... args) {
		final UnsafeCountingFactorizer factorizer = new UnsafeCountingFactorizer();
		
		int N = 1000;
		CountDownLatch latch = new CountDownLatch(2);
		CyclicBarrier barrier = new CyclicBarrier(2);
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				try {
					barrier.await();
				} catch (Throwable e) {
				}
				for (int j = 0; j < N / 2; j++) {
					factorizer.service();
				}
				latch.countDown();
			}).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(factorizer.count);
		
	}
	static class Parent{
		public synchronized  void x(){
			x();
		}
	}
	static class Child extends Parent{
		
		@Override
		public synchronized void x() {
			super.x();
		}
	}
	
	
	public static class UnsafeCountingFactorizer {
		
		private volatile int count = 0;
		
		public long getCount() {
			return count;
		}
		
		Object x = new Object();
		public void service() {
			synchronized (x) {
				++count;
			}
		}
	}
}
