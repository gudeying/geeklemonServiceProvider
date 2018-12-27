package cn.geeklemon;

public class ThreadTest {
	class run1 implements Runnable{
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		run2 runnable = new ThreadTest().new run2();
		Thread thread = new Thread(runnable);
		thread.start();
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		runnable.alive=false;
	}
	class run2 implements Runnable{
		public  boolean alive = true;
		@Override
		public void run() {
			while (alive) {
				while(true) {
					System.out.println("");
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}
}
