package Hcsr04;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class LED {
	private GpioController controller;
	private GpioPinDigitalOutput output;
	
	public LED(int outputPin) {
		controller = GpioFactory.getInstance();
		output = controller.provisionDigitalOutputPin(RaspiPin.getPinByAddress(outputPin));
	}
	public void blink(long delay, long duration) {
		output.blink(delay, duration);
	}
	public void light() {
		output.low();
	}
	public void dark() {
		output.high();
	}
}
