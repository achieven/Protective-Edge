import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;


public class Catch {


	

	/**
	 * Closes the arm of the robot
	 * @param data
	 */
	public  void closeArm(Data data) {
		data.getMc().forward();
		//		while(!data.getMc().isStalled()) {
		//			// waiting to to finish operation
		//		}
		try {Thread.sleep(1500);}
		catch (InterruptedException e) {e.printStackTrace();}
		data.getMc().stop();
	}
	
	/**
	 * Opens the arm of the robot
	 * @param data
	 */
	public  void openArm(Data data) {
		data.getMc().backward();
		//		while(!data.getMc().isStalled()) {
		//			// waiting to to finish operation
		//		}
		try {Thread.sleep(1500);}
		catch (InterruptedException e) {e.printStackTrace();}
		data.getMc().stop();
	}

	/**
	 * Decides which goal we should go to, according to the square and the object found colors
	 * @param data
	 * @param object_color - the color of the object we found
	 * @return
	 */
	private String decideWhichGoal(Data data, String object_color) {
		System.out.println("object: "+object_color+" square: "+data.square_color);

		if(object_color.compareTo("blue")==0 || object_color.compareTo("magneta")==0 ||  object_color.compareTo("green")==0){// blue or green
			if(data.square_color.compareTo("RED")==0){
				data.goal_color="RED";
			}
			if(data.square_color.compareTo("BLUE")==0){
				data.goal_color="BLUE";
			}
		}

		if(object_color.compareTo("white")==0 || object_color.compareTo("pink")==0 ||  object_color.compareTo("yellow")==0// white dice
				||  object_color.compareTo("black")==0 ||  object_color.compareTo("red")==0){// black or red
			if(data.square_color.compareTo("RED")==0){
				data.goal_color="BLUE";
			}
			if(data.square_color.compareTo("BLUE")==0){
				data.goal_color="RED";
			}
		}

		return data.goal_color;
	}
	/**
	 * Checks the color in front of the HT sensor. If matches one of the objects then returns the volor
	 * @param c - the color
	 * @return object of the color found
	 */
	public  int getRealColor(Color c) {
		System.out.println("BLUE "+c.getBlue()+ " RED "+c.getRed()+" GREEN "+c.getGreen());

		if(c.getRed()>7 && c.getBlue() <3 && c.getGreen() < 10){//red ball
			System.out.println("found red ball");
			return c.getColor();
		}
		if(c.getRed()>3 && c.getRed() < 10 && c.getBlue() >3 && c.getBlue() < 10 && c.getGreen()>7 && c.getGreen() < 20){//green dice
			System.out.println("found green dice");
			return c.getColor();
		}

		if(((c.getBlue()+ c.getRed()+c.getGreen())/3 > 7) && c.getBlue()> 4 && c.getRed() > 4 && c.getGreen() > 4){ //white dice
			System.out.println("found white dice");
			return c.getColor();
		}
		if(c.getRed() > 0 && c.getRed() < 10 && c.getBlue() > 20 && c.getGreen() > 5 && c.getGreen() <20 ){//blue ball
			System.out.println("found blue ball");
			return c.getColor();
		}
		if(c.getBlue() > 40 && c.getGreen() > 10 && c.getGreen() < 20 && c.getRed() >5 && c.getRed() < 15){// magneta dice
			System.out.println("found magneta dice");
			return c.getColor();

		}
		if(c.getBlue()>3 && c.getBlue()<8 && c.getRed()>3 && c.getRed()<8 && c.getGreen()>3 && c.getGreen()<8){//black dice
			System.out.println("found black dice");
			return c.getColor();
		}
		return -1;


	}

	/**
	 * Directing the robot towards the object
	 * @param data
	 */
	public  void alignToNearestObject(Data data) {
		
		/**
		float min=Float.MAX_VALUE;
		int deg = 45;
		float curr;
		boolean found = false;
		DifferentialPilot pilot = data.getPilot();

		// look around and get min distance
		pilot.rotate(deg, true);
		while(pilot.isMoving()){
			curr = data.getUs().getRange();
			min=Math.min(min, curr);
			System.out.println("min: " + min);
			Color t = data.getCs1().getColor();
			if(getRealColor(t) !=-1){
				pilot.rotate(-0.01);
				pilot.quickStop();
				return;
			}
		}
		pilot.rotate(-(deg*2), true);
		while(pilot.isMoving()){
			curr = data.getUs().getRange();
			min=Math.min(min, curr);
			System.out.println("min: " + min);
			Color t = data.getCs1().getColor();
			if(getRealColor(t) !=-1){
				pilot.rotate(-0.01);
				pilot.quickStop();
				return;
			}
		}


		pilot.rotate(deg*2, true);
		while(pilot.isMoving()) {
			curr = data.getUs().getRange();
			System.out.println("min: " + min + ",curr:" + curr);
			if( curr<= min ){
				System.out.println("Stop");
				pilot.stop();
				pilot.rotate(-10);
				found=true;
				Color t = data.getCs1().getColor();
				if(getRealColor(t) !=-1){
					pilot.rotate(-0.01);
					pilot.quickStop();
					return;
				}
			}

		}

		if(!found) {
			//return original position
			pilot.rotate(-deg);
		}
		*/
	}
	
	/**
	 * Catches the object in front of the robot
	 * @param data
	 */
	public void catchObject(Data data) {
		DifferentialPilot pilot = data.getPilot();
float tmin = Float.MAX_VALUE;
		
		
		int rotate_move_speed = 360;
		int rotate_search_speed = 30;
		float dist;
		long start_time;
		long mintime;
		boolean close = false;
		int move_forward= 4;
		openArm(data);
		pilot.setTravelSpeed(10);
		while(!close){
			
			mintime=0;
			dist=-1;
			tmin = Float.MAX_VALUE;
			
			pilot.setRotateSpeed(rotate_move_speed);
			pilot.rotate(45);
			
			pilot.setRotateSpeed(rotate_search_speed);
			pilot.rotate(-90, true);
			
			start_time = System.currentTimeMillis();
			System.out.println(start_time);
			while(pilot.isMoving()) {
				dist = data.getUs().getRange();
				if (dist<tmin){
					tmin = dist;
					mintime = System.currentTimeMillis() - start_time;
					System.out.println(mintime);
				}
			}
			System.out.println("F:" + mintime);
			pilot.setRotateSpeed(rotate_move_speed);
//			pilot.rotate(-90);
			float deg =  (float) (rotate_search_speed * (mintime/1000.0)) -90;
			deg *= -1;
			System.out.println("Degree:" + deg);
			pilot.rotate(deg);
			if(data.getUs().getRange()<=8) {
				close = true; //get closer one more time
				move_forward *= 2;
			}
			pilot.travel(move_forward);
			
		}
		pilot.setRotateSpeed(10);
		pilot.rotate(-30, true);
		while(pilot.isMoving()){
			int c = getRealColor(data.getCs1().getColor());
			if( c!=-1) {
				pilot.stop();
				data.goal_color=decideWhichGoal(data,data.colors_list[c]);
			}
		}
	
		closeArm(data);
		pilot.rotate(-30);
		openArm(data);
		pilot.rotate(-25);
		closeArm(data);
		
		/**
		boolean found = false;
		boolean cough= false;

		pilot.setRotateSpeed(15);
		pilot.setTravelSpeed(10);



		System.out.println("Look for object");
		while(!found && !cough) {
			Color t = data.getCs1().getColor();

			alignToNearestObject(data);
			//pilot.rotate(15);
			int i=0;
			while(i<3){
				pilot.travel(3);
				pilot.quickStop();
				t = data.getCs1().getColor();
				if(getRealColor(t)>0){// it found an object or a wall
					// so it won't catch a wall instead of an object
					Search s= new Search();
					pilot.stop();
					if(s.object_not_wall(data,50,20)==false){// the Us sensor encountered a WALL 
						DoEverything d=new DoEverything();// 180 degrees rotating is done in object_not_wall function. start over again
						d.searchCatchAndGoal(data);
					}
					else{// the Us sensor encountered an OBJECT
						pilot.stop();
						System.out.println("Found object with color:" + data.colors_list[ t.getColor()]);
						Button.waitForAnyPress();
						Sound.beepSequenceUp();
						found = true;
						break;
					}
					break;
				}

				i++;
			}
			if(i==10){
				pilot.travel(-1);
			}
			if(!found){
				pilot.travel(-6);	
			}

			if(found) {
				
				//turn to the found object
				if(data.getUs().getDistance()<15){// then we are in front of an object, need to correct a little
					pilot.rotate(20);
				}
				pilot.rotate(-70);

				//try to catch object

				closeArm(data);
				System.out.println("TACHO COUNT "+data.getMc().getTachoCount());

				if ( data.getMc().getTachoCount() <= data.arm_min){
					System.out.println("Got nothing, try again");
					Sound.beepSequence();
					cough = false;
					found = false;
					openArm(data);
					Search s=new Search();
					Catch c=new Catch();
					Goal g=new Goal();
					s.alignClosest(data, 10, false);
					c.catchObject(data);
					g.findGoal(data, data.goal_color);
				}
				else {
					System.out.println("Cought the object.");
					Sound.beepSequenceUp();
					data.goal_color=decideWhichGoal(data,data.colors_list[ t.getColor()]);
					cough = true;
					return;
				}
			}
		}
		*/
	}








}
