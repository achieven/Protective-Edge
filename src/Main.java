import javax.microedition.sensor.ColorIndexChannelInfo;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.SoundSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.navigation.DifferentialPilot;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("Hey, I'm Amzaleg");

		NXTRegulatedMotor ma = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor mb = new NXTRegulatedMotor(MotorPort.C);
		NXTRegulatedMotor mc = new NXTRegulatedMotor(MotorPort.B);
		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		LightSensor ls = new LightSensor(SensorPort.S1);
		ColorHTSensor cs1 = new ColorHTSensor(SensorPort.S3);
		ColorSensor cs2 = new ColorSensor(SensorPort.S4);
		DifferentialPilot pilot = new DifferentialPilot(5.5, 11, ma, mb);
		Data data = new Data(pilot, ma, mb, mc, us, ls, cs1, cs2);

		
		while( !Button.ESCAPE.isDown() ) {
//			if(data.getUs().getRange(<10) {
//				
//				System.out.println(data.getUs().getDistance());
//				data.getMa().rotate(120);
//				data.getMc().rotate(90);
//				Button.waitForAnyPress();
//				data.getMc().rotate(-90);
//			}
			//LCD.drawString("Color1: " + data.getCs1().getColor()., 0,0);	
			//LCD.drawString("gerRed: " + data.getCs2().getColor().getRed(), 0,0);	
			//LCD.drawString("getGreen: " + data.getCs2().getColor().getGreen(), 0,2);
			//LCD.drawString("getBlue: " + data.getCs2().getColor().getBlue(), 0,4);
			//LCD.drawString("Light: " + data.getLs().getLightValue(), 0,4);
			//LCD.drawString("Sonic: " + data.getUs().getRange(), 0,6);
			//LCD.drawString("Sonic: " + data.getUs().getDistance(), 0,2);
			//System.out.println("BLUE "+data.getCs1().getColor().getBlue()+ " RED "+data.getCs1().getColor().getRed()+" GREEN "+data.getCs1().getColor().getGreen());
			LCD.drawString("htclolorid "+data.colors_list[data.getCs1().getColor().getColor()],0,2);
			//data.getMc().backward();
//			data.getMc().setSpeed(50);
//			data.getMc().backward();
//			while(data.getMc().isMoving())
//				System.out.println("TACHO COUNT "+data.getMc().getTachoCount());
//			data.getMc().forward();
//			while(data.getMc().isMoving())
//				System.out.println("TACHO COUNT "+data.getMc().getTachoCount());
			
			
		}
		


	}

}