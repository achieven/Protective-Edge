
public class Search {


	/**
	 * returns the diversity between 3 numbers (gap between the highest and the smallest)
	 * @param red
	 * @param green
	 * @param blue
	 * @return the diversity
	 */
	public int diversity_between_getColors(int red, int green, int blue){
		return (Math.max(Math.max(Math.abs(red-green), Math.abs(red-blue)),Math.abs(green-blue) ));

	}
	/**
	 * return whether or not we are between 2 numbers
	 * @param number
	 * @param border1
	 * @param border2
	 * @return a boolean answer for it
	 */

	public boolean between( int number, int border1, int border2){
		if(border1 > border2){
			return number>=border2 && number<= border1;
		}
		else{
			return number>=border1 && number<= border2;
		}
	}


	/**
	 * Changes the color of the square in data if we found a square
	 * @param cond
	 * @param data
	 */

	private void change_dataSquareColor(boolean[] cond, Data data) {
		if(cond[0] || cond[1]|| cond[2]){
			data.square_color="RED";
		}
		else{
			if(cond[3] || cond[4]){
				data.square_color="BLUE";
			}
			else{
				System.out.println("PROBLEM!!!!!");

			}
		}
		//		for(int k1=0;k1<all_conditions.length;k1++){
		//			for(int k2=0;k2<all_conditions[0].length;k2++){
		//				System.out.print(all_conditions[k1][k2]+" ");
		//			}
		//			System.out.println();
		//		}

	}
	/**
	 * Gives information about the robot being on a square or not
	 * @param data
	 * @return an array of conditions which are the conditions for a square
	 */

	private boolean[] checkIfFoundSquareBoolArray(Data data) {
		//		System.out.println("getRed: " + data.getCs2().getColor().getRed());	
		//		System.out.println("getGreen: " + data.getCs2().getColor().getGreen());
		//		System.out.println("getBlue: " + data.getCs2().getColor().getBlue());
		int getRed=data.getCs2().getColor().getRed();
		int getGreen=data.getCs2().getColor().getGreen();
		int getBlue=data.getCs2().getColor().getBlue();




		boolean cond0=between (getRed,150,250) && between(getGreen,30,80) && between(getBlue,30,80);// the red one close to the computers
		boolean cond1=between (getRed,200,300) && between(getGreen,50,100) && between(getBlue,50,100);// the 2 red ones that are close to the side of the blue gate
		boolean cond2=between (getRed,100,200) && between(getGreen,0,50) && between(getBlue,0,50);// the red one close to the red gate
		boolean cond3=between (getRed,50,150) && between(getGreen,150,250) && between(getBlue,200,300);// the blue one close to the blue gate
		boolean cond4=between (getRed,100,200) && between(getGreen,200,300) && between(getBlue,200,300); // the blue one close to the closet

		boolean[] cond={cond0,cond1,cond2,cond3,cond4};
		return cond;
	}		

	/**
	 * 
	 * @param data
	 * @param square - 
	 * @param all_conditions - a 2 dimensional array that gives information about the conditions for being on a square (first dimension is number of checks, second is just the conditions
	 * @return square, which meand whether or not we are on a square or not (cound be boolean as well)
	 */
	private int checkIfFoundSquareInt(Data data, int square, boolean[][] all_conditions) {
		//		System.out.println("getRed: " + data.getCs2().getColor().getRed());	
		//		System.out.println("getGreen: " + data.getCs2().getColor().getGreen());
		//		System.out.println("getBlue: " + data.getCs2().getColor().getBlue());
		int getRed=data.getCs2().getColor().getRed();
		int getGreen=data.getCs2().getColor().getGreen();
		int getBlue=data.getCs2().getColor().getBlue();




		boolean cond0=between (getRed,150,250) && between(getGreen,30,80) && between(getBlue,30,80);// the red one close to the computers
		boolean cond1=between (getRed,200,300) && between(getGreen,50,100) && between(getBlue,50,100);// the 2 red ones that are close to the side of the blue gate
		boolean cond2=between (getRed,100,200) && between(getGreen,0,50) && between(getBlue,0,50);// the red one close to the red gate
		boolean cond3=between (getRed,50,150) && between(getGreen,150,250) && between(getBlue,200,300);// the blue one close to the blue gate
		boolean cond4=between (getRed,100,200) && between(getGreen,200,300) && between(getBlue,200,300); // the blue one close to the closet
		boolean[] square_conditions={cond0,cond1, cond2,cond3,cond4};
		int diversity=diversity_between_getColors (getRed,getGreen,getBlue);
		for(int cond=0;cond<5; cond++){
			if(square_conditions[cond]==true){

				//System.out.println("diversity "+diversity);
				if(diversity>60){
					all_conditions[square]=square_conditions;
					square++;


				}
			}

		}
		boolean[] cond={cond0,cond1,cond2,cond3,cond4};
		return square;
	}	

	/**
	 * 
	 * Basically the function looks in both directions and if Us sensor found something
	 * If found, it checks if it's object or wall and behaves accordingly
	 * @param data
	 * @param degrees_to_rotate gives the rotation that is done to scan for objects
	 * @param distanceToCheck is the distance we are checking for the wall
	 * @return
	 */
	public boolean object_not_wall(Data data, double degrees_to_rotate, int distanceToCheck) {
		//System.out.println("before rotating distance: "+data.getUs().getDistance());
		data.getPilot().stop();



		data.getPilot().rotate(-degrees_to_rotate);

		//	System.out.println("after 1st rotating distance: "+ data.getUs().getDistance());
		data.getPilot().stop();
		if(data.getUs().getDistance()<distanceToCheck){// then it is a wall, sometimes it falsely recognizes the object as a wall - NEED TO FIX!!!!
			//	System.out.println("WALL 1");

			if(data.getUs().getDistance()<8){// in a very close distance it could get stuck on the wall when trying to rotate, move backwords so it won't happen
				data.getPilot().backward();
				try {Thread.sleep(500);}
				catch (InterruptedException e) {e.printStackTrace();}
			}
			data.getPilot().rotate(180);
			return false;
		}
		else{
			data.getPilot().rotate(degrees_to_rotate*1.8);// after finished rotating once, check the other angle. it's 1.8 and not 2 because it was just more accurate this way when I checked it
			//	System.out.println("after 2nd rotating distance: "+data.getUs().getDistance());
			data.getPilot().stop();

			if(data.getUs().getDistance()<distanceToCheck){// then it is a wall, though sometimes object, NEED TO FIX!!!

				if(data.getUs().getDistance()<8){// in a very close distance it could get stuck on the wall when trying to rotate, move backwords so it won't happen                                 
					data.getPilot().backward();
					try {Thread.sleep(500);}
					catch (InterruptedException e) {e.printStackTrace();}
				}
				data.getPilot().rotate(Math.random()*30 +180);//between 180 and 210 
				return false;
			}
			else{//then it is an object
				return true;


			}
		}


	}

	/**
	 *Align the robot in front of the closest object to it 
	 * @param data gives the sensors and motors info
	 * @param numberOfIterations tells the number of rotations need to be don when aligning
	   @param object_not_square tells we are aligning because we found an object and not because we found a square, if true then go a little backward so you won't miss an object
	 */
	public void alignClosest(Data data, int numberOfIterations, boolean object_not_square) {
		int counter=0;
		if(object_not_square==true){// if we are aligning because we found an object and not because we found a square, then go a little backward so you won't miss an object
			data.getPilot().backward();
			try {Thread.sleep(200);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		locateObject(data,numberOfIterations);

		int i=0;
		boolean [][] all_conditions=new boolean[10][5];// conditions for the colors of the squares
		int square=0;
		if(data.square_color.compareTo("")!=0){
			while(data.getUs().getDistance() > 7 && counter<150){//approach object until very close to it or you couldn't find it

				data.getPilot().forward();
				if(data.square_color.compareTo("")==0){
					boolean[] cond=checkIfFoundSquareBoolArray(data);
					square=checkIfFoundSquareInt(data, square, all_conditions);
					//end of checking for square

					if(square>=5){// meaning we are on a square
						change_dataSquareColor(cond,data);// changing the data square_color field
					}
				}

				try {Thread.sleep(10);}
				catch (InterruptedException e) {e.printStackTrace();}
				counter++;

				if(counter%33==0){// once in a while - do some finetuning to the object
					i=0;
					all_conditions=new boolean[10][5];// conditions for the colors of the squares
					square=0;
					data.getPilot().rotate(-10);
					locateObject(data,10);
				}
			}
		}
		else{
			return;
		}
		data.getPilot().stop();

		if(counter>149){// then we didn't find the ball probably
			data.getPilot().setTravelSpeed(26.797);// just like setting motors speed to 200
			searchObject(data);
		}
		else{
			return;

		}
		return;


	}

	/**
	 * Directing towards the object.
	 * @param data
	 * @param numberOfIterations - number of rotations being done
	 */
	public void locateObject (Data data, int numberOfIterations) {

		int minDist = 1000;
		int turnNum = 0;
		int currDis;

		for (int i = 0; i < numberOfIterations; i++) {
			currDis = data.getUs().getDistance();
			//	System.out.println("distance: " + currDis);
			if (currDis < minDist) {
				minDist = currDis;
				turnNum = 1;
			}
			else {
				turnNum++;
			}
			data.getPilot().rotate(5);
		}

		if (minDist < 40)
			data.getPilot().rotate(-5 * (turnNum));//play with "turnum" according to what is needed
		else
			data.getPilot().rotate(-52);
		//}

		data.getPilot().setTravelSpeed(10);// lower travel speed for approaching the object

	}

	/**
	 * Searches for the square in the table.
	 * @param data
	 * @param foundObject - if found an object before found a square - the function is slightly different
	 */
	private void searchSquare(Data data,boolean foundObject) {

		while(true){
			int i=0;
			boolean [][] all_conditions=new boolean[10][5];// conditions for the colors of the squares
			int square=0;


			if(i%10 ==0){// every 10 - elapse it
				all_conditions=new boolean[6][5];
				square=0;
			}
			while(i<200){
				data.getPilot().forward();
				try {Thread.sleep((int)(Math.random()*10));}
				catch (InterruptedException e) {e.printStackTrace();}
				//square=checkSquare(data,square,all_conditions);

				// two functions that check if we are on a square
				boolean[] cond=checkIfFoundSquareBoolArray(data);
				square=checkIfFoundSquareInt(data, square, all_conditions);
				//end of checking for square

				if(square>=5){// meaning we are on a square
					change_dataSquareColor(cond,data);// changing the data square_color field

					data.getPilot().stop();
					data.getPilot().backward();// go back, so you can scan the neighbourhood
					try {Thread.sleep(200);}
					catch (InterruptedException e) {e.printStackTrace();}

					data.getPilot().rotate(-120);// so you can look for objects in the neighbourhood
					data.getPilot().stop();
					return;

				}
				else{// we are not on a square
					if(foundObject==false){
						if(data.getUs().getDistance()<35){// if Us sensor sensed something is close, need to ckeck if object or wall
							data.getPilot().stop();
							if(object_not_wall(data,50,40)==false){// the Us sensor encountered a WALL 
								break;// 180 degrees rotating is done in object_not_wall function. start over again
							}
							else{// the Us sensor encountered an OBJECT
								data.getPilot().rotate(-40);// return to original path
								data.getPilot().setTravelSpeed(15);
								searchSquare(data, true);
							}
							return;
						}
					}
					else{
						if(data.getUs().getDistance() < 8 && object_not_wall(data, 50,40)==false){//encountered a wall

							data.getPilot().setTravelSpeed(26.797);
							searchSquare(data, false);
						}
					}
				}
				i++;
			}


		}

	}

	/**
	 * Searches the object in the table and approaches to it until it is very close to it (distance 7)
	 * @param data
	 */
	public void searchObject(Data data) {
		data.getMc().setSpeed(100);
		data.getPilot().setTravelSpeed(26.797);
		data.getPilot().setRotateSpeed(90);

		boolean breaked=false;


		searchSquare(data,false);
		alignClosest(data, 40,false);


		/** DONT DELETE, JUST COMMENT FOR NOW!!!
				if(i%50==0){// once in a while, explore neighbourhood to find maybe there is an object just outside your path

					data.getPilot().rotate(30);// rotate to one direction and see if there is something
					System.out.println("1st rotating distance "+data.getUs().getDistance());
					if(data.getUs().getDistance()<50){//Us sensor found something
						while(data.getUs().getDistance()>35){// approach a little
							data.getPilot().forward();
						}

						if(object_not_wall(data, 50)){// if the Us sensor found an object
							break;
						}
						else{// found a wall, turn around (in object_not_wall funtion) and start over again
							break;
						}
					}
					data.getPilot().rotate(-60);// rotate to the other direction ans see if there is something
					System.out.println("2nd rotating distanFce "+data.getUs().getDistance());
					if(data.getUs().getDistance()<50){    // if the Us sensor found an object 
						while(data.getUs().getDistance()>35){
							data.getPilot().forward();
						}
						if(object_not_wall(data, 50)){//if found an object
							break;
						}
						else{
							break;
						}
					}
					data.getPilot().rotate(30);// if nothing was found, return to original direction of movement

				}
		 */

		/** 
		 *The next 8 lines are a way to turn around easily after the robot searched in one direction for a while
		 *It is a fast turning to the side basicaly 
		 *It is not really used in the current code (22.12.2014) but could be left for "just in case purpose"
		 */
		/**
			data.getMa().setSpeed(0);//turn to the side easily and fast. one engine is silenced for 1/2 a second - that implements aturn
			try {
				Thread.sleep(500);
				System.out.println("turning around");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 */
		/**previous code from the original project. Would be DELETED if we won't find a use for it
			System.out.println("searching objects");
			data.getPilot().rotate(-25);
			alignClosest(data, 10);
			if (data.getUs().getDistance() < 40) {
				while (data.getUs().getDistance() > 28)
					data.getPilot().forward();
				data.getPilot().stop();
				if(isBall(data))
					break;
				else{
					data.getPilot().rotate(120);
				}

			}
			else {
				data.getPilot().forward();
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {System.out.println("exception in sleep");}
			}
		 */


	}




}
