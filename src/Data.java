import lejos.nxt.ColorSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.navigation.DifferentialPilot;


public class Data {

	//state
	private DifferentialPilot pilot;
	private NXTRegulatedMotor ma;
	private NXTRegulatedMotor mb;
	private NXTRegulatedMotor mc;
	private UltrasonicSensor us;
	private LightSensor ls;
	private ColorHTSensor cs1;
	private ColorSensor cs2;
	public final String[] colors_list  = {"red","green","blue","yellow","magenta","orange","white","black","pink","gray","light gray","dark gray","cyan"}; 
	public double arm_max=35;	
	public  double arm_min=15;	
	public String goal_color="";
	public String square_color="";
	public String object_color="";
	public boolean finish=false;
	//constructor
	public Data(DifferentialPilot pilot, NXTRegulatedMotor ma, NXTRegulatedMotor mb, NXTRegulatedMotor mc, 
			UltrasonicSensor us, LightSensor ls, ColorHTSensor cs1, ColorSensor cs2) {
		this.pilot = pilot;
		this.ma = ma;
		this.mb = mb;
		this.mc = mc;
		this.us = us;
		this.ls = ls;
		this.cs1 = cs1;
		this.cs2 = cs2;
	}

	//behavior
	
	public NXTRegulatedMotor getMa() {
		return ma;
	}


	public NXTRegulatedMotor getMb() {
		return mb;
	}


	public NXTRegulatedMotor getMc() {
		return mc;
	}


	public UltrasonicSensor getUs() {
		return us;
	}


	public LightSensor getLs() {
		return ls;
	}


	public ColorHTSensor getCs1() {
		return cs1;
	}


	public ColorSensor getCs2() {
		return cs2;
	}

	public DifferentialPilot getPilot() {
		return pilot;
	}

}