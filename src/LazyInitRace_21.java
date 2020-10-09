public class LazyInitRace_21 {
	
	public static void main(String... args) {
		final LazyInitRace holder = null;
		
		new Thread(() -> {
			holder.getInstance();
		}).start();
		new Thread(() -> {
			holder.getInstance();
		}).start();
	}
	
	static class MyObject {
		
		
		MyObject() {
			System.out.println("hello");
		}
	}
	
	public enum LazyInitRace {
		INSTANCE(new MyObject());
		
		private final MyObject myObj;
		
		LazyInitRace(MyObject o) {
			this.myObj = o;
		}
		
		public MyObject getInstance() {
			return INSTANCE.myObj;
		}
	}
}
