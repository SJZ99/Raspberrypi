package raspberrypi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class First {
	public static void main(String[] args) throws InterruptedException {
		final GpioController controller = GpioFactory.getInstance();
		final GpioPinDigitalOutput led = controller.provisionDigitalOutputPin(RaspiPin.GPIO_00);
		for(int i = 0; i < 10; i++) {
			led.blink(1000, 1000);
			Thread.sleep(2000);
		}
		
	}
}
