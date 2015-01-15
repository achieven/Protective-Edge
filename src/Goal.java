import lejos.nxt.Button;
import lejos.util.PilotProps;


public class Goal {
	public  final int RED = 0;
	public  final int BLUE = 2;


	/**
	 * Declaring victory, cause we found the right goal
	 * @param data
	 */
	private void victory(Data data) {
		System.out.println("IS NEEDED COLOR");
		data.getPilot().backward();
		try {Thread.sleep(2000);} 
		catch (InterruptedException e) {e.printStackTrace();}
		data.getPilot().rotate(720);
		System.exit(0);

	}
	
	/**
	 * If we wound not the right goal, go to the other goal
	 * @param data
	 * @param blue_or_red - color of goal we need
	 */
	private void gotoOtherGoal(Data data,String blue_or_red) {
		System.out.println("NOT NEEDED COLOR");
		data.getPilot().backward();
		try {Thread.sleep(2500);}
		catch (InterruptedException e) {System.out.println("exception in sleep");}

		data.getPilot().setTravelSpeed(26.797);

		data.getPilot().rotate(95);
		alignToMaxLightSource(data, 19);
		goToMaxLightSource(data, 28,80);
		fineTuning(data, blue_or_red);//NOTICE IT IS OPPOSITE! getBlue and getRed switched!!
		
	}

	/**
	 * When very close, checking the color of the goal, and decides if it's what we want or not, and bhaves according to that
	 * @param data
	 * @param blue_or_red - color of goal needed
	 */
	public  void checkGoalColor(Data data,  String blue_or_red) {
		data.getPilot().setTravelSpeed(10);
		if(blue_or_red.compareTo("BLUE")==0){
			int i=0;
			while(i<100){
				data.getPilot().forward();
				String curColor=data.colors_list[data.getCs1().getColor().getColor()];
				if(curColor.compareTo("blue")==0 ||curColor.compareTo("magenta")==0	){
					victory(data);

					break;
				}
				else{
					if(curColor.compareTo("red")==0){
						gotoOtherGoal(data,blue_or_red);
						
						break;
					}
					
				}
				i++;
			}
			if(i==100){
				data.getPilot().backward();
				try {Thread.sleep(3000);}
				catch (InterruptedException e) {e.printStackTrace();}
				fineTuning(data, blue_or_red);
			}
		}
		else{
			if(blue_or_red.compareTo("RED")==0){
				int i=0;
				while(i<100){
					data.getPilot().forward();
					String curColor=data.colors_list[data.getCs1().getColor().getColor()];
					if(curColor.compareTo("red")==0){
						victory(data);
						break;
					}
					else{
						if(curColor.compareTo("blue")==0 ||curColor.compareTo("magenta")==0	){
							gotoOtherGoal(data,blue_or_red);
							break;
						}
					}
					i++;
				}
			}
		}



		/**
		int curRed = getRed;
		int curBlue = getBlue;

		while (curRed <getRed && curBlue < getBlue) {
			data.getMa().forward();
			data.getMb().forward();
			curRed = data.getCs1().getColor().getRed();
			curBlue = data.getCs1().getColor().getBlue();
			System.out.println("curRed: "+curRed);
			System.out.println("curBlue: "+curBlue);
		}
		//Found needed color
		if ((blue_or_red.compareTo("RED")==0 && curRed >= getRed) || (blue_or_red.compareTo("BLUE")==0 && curBlue >= getBlue)) {
			System.out.println("IS NEEDED COLOR");
			data.getMc().rotate(100);
			data.getPilot().backward();
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {System.out.println("exception in sleep");}
			data.getMc().rotate(-100);
			data.getPilot().rotate(-170);
			Button.waitForAnyPress();
		}
		//Didn't find needed color
		else if ((curRed < getRed && blue_or_red.compareTo("RED") ==0 ) || (curBlue < getBlue && blue_or_red.compareTo("BLUE") ==0 )){// dound the other goal, need to go to the other goal
			System.out.println("NOT NEEDED COLOR");
			data.getPilot().backward();
			try {
				Thread.sleep(2500);
			}
			catch (InterruptedException e) {System.out.println("exception in sleep");}

			data.getMa().setSpeed(250);
			data.getMb().setSpeed(250);

			data.getPilot().rotate(95);
			alignToMaxLightSource(data, 19);
			goToMaxLightSource(data, 28);
			fineTuning(data, getBlue, getRed, blue_or_red);//NOTICE IT IS OPPOSITE! getBlue and getRed switched!!

		}
		else {
			System.out.println("Prob finding goal");
			data.getPilot().travel(-30);
			fineTuning(data, getRed, getBlue, blue_or_red);
		}
		 */
	}	

	/**
	 * When we are close, reducing speed, searching again for the goal, and approaching it.
	 * @param data
	 * @param blue_or_red - the color needed for the goal
	 */
	public  void fineTuning(Data data,  String blue_or_red) {
		System.out.println("FINE TUNE LIGHT");
		data.getPilot().setTravelSpeed(10);

		if (data.getLs().getLightValue() > 65) {
			System.out.println("LIGHT > 65, FINAL ALIGNMENT");
			data.getPilot().rotate(-50);
			alignToMaxLightSource(data, 10);
			while(data.getUs().getDistance() > 8){
				data.getPilot().forward();

			}
			checkGoalColor(data, blue_or_red);

		}
		else {
			data.getPilot().rotate(-90);
			alignToMaxLightSource(data, 18);
			goToMaxLightSource(data, 25,63);
			fineTuning(data, blue_or_red);

		}
	}


	/**
	 * Going to the max light source
	 * @param data
	 * @param distanceToCheck - the distance given, so that if Us sensor found something within the distance (object or wall) - should behave acoording to that
	 */
	public  void goToMaxLightSource(Data data, int distanceToCheck,int maxlight) {
		data.getPilot().setTravelSpeed(26.797);

		while (data.getUs().getDistance() > distanceToCheck) {
			data.getPilot().forward();
	
		}

		if (data.getLs().getLightValue() < maxlight) {

			data.getPilot().rotate(-40);
			alignToMaxLightSource(data, 8);
			data.getPilot().setTravelSpeed(4*26.797);
			data.getPilot().forward();
			try {Thread.sleep(800);}
			catch (InterruptedException e) {System.out.println("exception in sleep");}

			data.getPilot().stop();
			try {Thread.sleep(800);                                                         }
			catch (InterruptedException e) {System.out.println("exception in sleep");}
			goToMaxLightSource(data, 28,63);

		}
		System.out.println(data.getLs().getLightValue());
	}


	/**
	 * Searches for the max light source around
	 * @param data
	 * @param numberOfRotations - number of rotations (should be 36, to complete a full circle)
	 */
	public  void alignToMaxLightSource(Data data, int numberOfRotations) {
		data.getPilot().setRotateSpeed(150);
		System.out.println("ALIGN TO MAX LIGHT");
		int turnNum = 0;
		int maxLightSource = 0;

		for (int i = 0; i < numberOfRotations; i++) {
			if (data.getLs().getLightValue() > maxLightSource) {
				maxLightSource = data.getLs().getLightValue();
				turnNum = 1;
			}
			else {
				turnNum++;
			}
			data.getPilot().rotate(10);
		}
		System.out.println("Max Light:" + maxLightSource);
		data.getPilot().setAcceleration(250);
		data.getPilot().rotate(-10 * turnNum);
		data.getPilot().setAcceleration(1000);

	}
	/**
	 * Makes the robot go the goal needed
	 * @param data
	 * @param blue_or_red - the goal we need to go to
	 */
	public  void findGoal(Data data, String blue_or_red) {
		System.out.println("LOOKING FOR GOAL: "+ blue_or_red);
		data.getPilot().setAcceleration(1000);
		data.getPilot().setTravelSpeed(26.797);

		alignToMaxLightSource(data, 36);
		goToMaxLightSource(data, 50,80);
		if(blue_or_red.compareTo("RED")==0){
			fineTuning(data, "RED");
		}
		else
			if(blue_or_red.compareTo("BLUE")==0){
				fineTuning(data,"BLUE");
			}
	}
}



/**
float dist = data.getUs().getRange();
closeArm(data);
pilot.rotate(-10);
pilot.travel(dist, true);
Color t = data.getCs1().getColor();
while(pilot.isMoving() && (getRealColor(t) ==-1)){
	t = data.getCs1().getColor();
}
openArm(data);
System.out.println("Found object with color:" + data.colors_list[ t.getColor()]);
pilot.rotate(-15
		);

//try to catch object
closeArm(data);
 */