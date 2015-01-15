import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.navigation.DifferentialPilot;

import lejos.nxt.*;
import lejos.robotics.Color;

public class FinalProg {

	
	//public double minimalDistance=1000;

	

	/**
	 * travel until finds an object in front of him and checks if its a ball and not a wall
	 * @param data gives the sensors and motors info
	 * 
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		NXTRegulatedMotor ma = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor mb = new NXTRegulatedMotor(MotorPort.C);
		NXTRegulatedMotor mc = new NXTRegulatedMotor(MotorPort.B);

		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		LightSensor ls = new LightSensor(SensorPort.S1);
		ColorHTSensor cs1 = new ColorHTSensor(SensorPort.S3);
		ColorSensor cs2 = new ColorSensor(SensorPort.S4);
		DifferentialPilot pilot = new DifferentialPilot(5.5, 11, ma, mb);
		Data data = new Data(pilot, ma, mb, mc, us, ls, cs1, cs2);
		
		data.getMc().setSpeed(100);
		data.getPilot().setTravelSpeed(26.797);
		data.getPilot().setRotateSpeed(90);

		Catch c=new Catch();
		c.openArm(data);
		data.getMc().resetTachoCount();
		c.closeArm(data);
		data.arm_max = data.getMc().getTachoCount();
		data.arm_min = data.arm_max *0.75;
		System.out.println("arm_max: "+data.arm_max);
		c.openArm(data);
		
		DoEverything d=new DoEverything();
		d.searchCatchAndGoal(data);
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	


	//------------------------------------------IRRELEVANT-------------------------------------------------------------//
	/**
	 * checks the ball color and takes it if its the correct color of ball
	 * @param data gives the sensors and motors info
	 * @param color - the color of the asking ball
	 * @return true iff the ball color is color (and also take it)
	 */
	public static boolean takeBall(Data data, int color) {//NOT IMPLEMENTED RIGHT NOW!!
		/**
		NXTRegulatedMotor mc = data.getMc();
		DifferentialPilot pilot = data.getPilot();
		ColorHTSensor cs1 = data.getCs1();
		mc.rotate(100);
		pilot.setTravelSpeed(5);
		int ball = cs1.getColorID();
		long currTime = System.currentTimeMillis();
		while(ball != RED && ball != BLUE && ball != -1 && ((System.currentTimeMillis() - currTime)/1000 < 10)){
			pilot.forward();
			ball = cs1.getColorID();
		}
		pilot.stop();

		if(ball == color || (color == -1 && (ball == RED || ball == BLUE))){
			mc.rotate(-110);
			pilot.setTravelSpeed(10);
			ballsFound[ball] = true;
			if (ball == BLUE)
				ballsFound[RED] = false;
			else
				ballsFound[BLUE] = false;
			finish++;
			return true;
		}
		else{
			pilot.setTravelSpeed(10);
			pilot.travel(-20);
			pilot.rotate(120);
			mc.rotate(-110);
			return false;
		}
		 */
		return false;
	}

	//************************************************************************************************************
	/** NOT USED AT THE MOMENT, PREVIOUS CODE*/
	/**
	 * runs the scan function
	 * @param data gives the sensors and motors info
	 * @return true iff found ball.
	 */
	public static boolean isBall(Data data){
		if (scan(data, 20 , 2) && scan(data, 20 , -2))			// scan left
			return true;
		return false;
	}

	//************************************************************************************************************

	/** NOT USED AT THE MOMENT, PREVIOUS CODE*/ 
	/**
	 * after close enough to an object checks if its a ball or a wall
	 * @param data gives the sensors and motors info
	 * @param times - number of times the robot rotate
	 * @param deg - the degree of the rotation 
	 * @return true iff found ball.
	 */
	private static boolean scan(Data data, int times, int deg) {
		data.getPilot().rotate(deg*(-3));
		int minDistance = Integer.MAX_VALUE;
		int maxDistance = 0;
		int distance;
		for (int i = 0; i < times; i++) {
			data.getPilot().rotate(deg);
			distance = data.getUs().getDistance();
			System.out.println(distance);
			if(distance < minDistance) 		//set min
				minDistance = distance;
			if(distance > maxDistance)		//set max
				maxDistance = distance;
		}
		if(maxDistance - minDistance > 25){			//this is a ball!
			System.out.println("max: " + maxDistance);
			System.out.println("min: " + minDistance);
			data.getPilot().rotate(-1 * deg * (times-3));
			return true;
		}
		//didn't find the ball...
		data.getPilot().rotate(-1 * deg * (times-3));
		return false;
	}
	

	
	public static void findFloor(Data data) {
		DifferentialPilot pilot = data.getPilot();
		boolean found_floor = true;
		int lookup_radius = 20;
		int lookup_angel = 245;
		int floor_color;
		Sound.beep();
		pilot.setRotateSpeed(10);
		// find floor
	
		System.out.println("Search for a floor");
		floor_color = data.getCs2().getColorID();
		
		pilot.setRotateSpeed(90);
		pilot.setTravelSpeed(15);
		while (!found_floor) {
			
			pilot.arc(lookup_radius, lookup_angel,true);
			while(pilot.isMoving() && floor_color != Color.RED &&  floor_color  != Color.BLUE) {
				floor_color = data.getCs2().getColorID();
			}
			pilot.stop();

			floor_color = data.getCs2().getColorID();
			if(!(floor_color != Color.RED &&  floor_color  != Color.BLUE) ) {
				found_floor = true;
			}
			else{
				lookup_radius *= 1.1;
				pilot.rotate(-180);
			}
			
			
		}
		
		System.out.println("Found floor colored in " + data.colors_list[floor_color]);
		Sound.beepSequenceUp();
		pilot.travel(-5);
		
	}

	

}


