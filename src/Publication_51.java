import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Publication_51 {
	
	public static void main(String... args) {
		int N = 1000000;
		Holder[] holders = new Holder[N];
		for (int i = 0; i < N; i++) {
			holders[i] = new Holder();
		}
		CountDownLatch ended = new CountDownLatch(2);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
		new Thread(() -> {
			try {
				cyclicBarrier.await();
			} catch (Throwable e) {
			}
			for (int i = 0; i < N; i++) {
				holders[i].setN(i);
			}
			ended.countDown();
		}).start();
		new Thread(() -> {
			try {
				cyclicBarrier.await();
			} catch (Throwable e) {
			}
			for (int i = 0; i < N; i++) {
				holders[i].assertSanity();
			}
			ended.countDown();
		}).start();
		try {
			ended.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static class Holder {
		
		private int n;
		
		public void setN(final int n) {
			this.n = n;
		}
		
		public void assertSanity() {
			final int n1 = this.n;
			if (n1 != n1) {
				throw new AssertionError("This statement is false.");
			}
		}
	}
}
