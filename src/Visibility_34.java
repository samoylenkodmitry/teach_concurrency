import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Visibility_34 {
	
	
	public static class NoVisibility {
		
		
		private static class Holder {
			
			private volatile boolean ready;
			private int number;
			
			public void run() {
				while (!ready) {
					Thread.yield();
				}
				if (number != 42) {
					System.out.println("not 42!");
					throw new AssertionError("not 42!");
				}
			}
		}
		
		
		public static void main(String[] args) {
			ExecutorService executorService = Executors.newCachedThreadPool();
			while (true) {
				final Holder holder = new Holder();
				executorService.execute(() -> {
					holder.run();
				});
				executorService.execute(() -> {
					holder.number = 42;
					holder.ready = true;
				});
			}
		}
	}
}
