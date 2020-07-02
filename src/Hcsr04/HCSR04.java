package Hcsr04;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class HCSR04 {
	private final long REJ_TIME;
	private GpioController controller;
	private GpioPinDigitalInput echo;
	private GpioPinDigitalOutput trig;
	
	
	public HCSR04(int trig, int echo, long rejectTime) {
		REJ_TIME = rejectTime;
		controller = GpioFactory.getInstance();
		this.trig = controller.provisionDigitalOutputPin(RaspiPin.getPinByAddress(trig));
		this.echo = controller.provisionDigitalInputPin(RaspiPin.getPinByAddress(echo));
		this.trig.setShutdownOptions(true, PinState.LOW);
	}
	
	public double getDistance() {
		long rejCount = 0, startTime = 0, endTime = 0;
		trig.isLow();
		waitMicros(2);
		trig.high();
		waitMicros(10);
		trig.low();
		
		while(echo.isLow()) {
			waitMicros(1);
			rejCount++;
			if(rejCount == REJ_TIME) 
				return -1;
		}
		
		rejCount = 0;
		startTime = System.nanoTime();
		while(echo.isHigh()) {
			waitMicros(1);
			rejCount++;
			if(rejCount == REJ_TIME * 2)
				return -1;
		}
		endTime = System.nanoTime();
		return (endTime - startTime) / 29.1 / 2l;
	}
	
	public void waitMicros(long micros) {
		long stop = System.nanoTime() + (micros * 1000);
		while(System.nanoTime() < stop) {
		}
		return;
	}
	
	
}
