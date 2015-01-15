
public class DoEverything {
	public void searchCatchAndGoal(Data data) {

		Search s=new Search();
		Catch c=new Catch();
		Goal g=new Goal();
		
		s.searchObject(data);
		data.getMc().backward();
		c.catchObject(data);
		g.findGoal(data, data.goal_color);

	}
}


/**
private boolean verifyObjectCatching(Data data) {
closeArm(data);
System.out.println("TACHO COUNT "+data.getMc().getTachoCount());
int firstTacho=data.getMc().getTachoCount() ;
if (firstTacho >= data.arm_max*0.95){//managed to close until the end
	openArm(data);
	data.getPilot().rotate(-5);
	int secondTacho=data.getMc().getTachoCount() ;
	if(secondTacho > secondTacho - 5){//then it is probably 
		
	}
	
}
}*/