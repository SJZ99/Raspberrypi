package Hcsr04;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		HCSR04 sonic = new HCSR04(0, 6, 10000);
		System.out.println("Start!");
		while(true) {
			System.out.println(sonic.getDistance());
			Thread.sleep(1000);
		}
	}

}
