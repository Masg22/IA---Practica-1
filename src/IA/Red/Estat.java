package IA.Red;

import java.util.ArrayList;

class ConnexSensor{
	ArrayList<Integer> in = new ArrayList<Integer>();
	Integer out = -1;
	Boolean free = false; 
}
class ConnexCentro{
	ArrayList<Integer> in = new ArrayList<Integer>();
	Boolean free = false;
}

public class Estat {
	
	static Sensores sensores;
	static CentrosDatos centros;
	
	private ArrayList<ConnexSensor> connexS;
	private ArrayList<ConnexCentro> connexC;
	
	private double coste;
	
	//*************Getters*************
	public double getCoste() {
		return coste;
	}
	//*************Constructoras*************
	public Estat(int nsens, int sens_seed, int ncent, int cent_seed) {
		
		sensores = new Sensores(nsens, sens_seed);
		centros = new CentrosDatos(ncent, cent_seed);
		
		connexS = new ArrayList<ConnexSensor>(nsens);
		
		connexC = new ArrayList<ConnexCentro>(ncent);
		
		coste = 0.0;
	}
	
	public Estat(Estat estat) {
		sensores = estat.sensores;
		centros = estat.centros;
		connexS = new ArrayList<ConnexSensor>(estat.connexS);
		connexC = new ArrayList<ConnexCentro>(estat.connexC);
		coste = estat.coste;
	}
	
	//*************Occupacy para sensores y centros*************
	//Controlar cada vez que añadimos una connexion a un sensor o centro. 
	public void setOccupacyS (ConnexSensor sensor) {
		if(sensor.in.size()>=3) sensor.free = false;
		else sensor.free = true;
	}
	public void setOccupacyC (ConnexCentro centro) {
		if(centro.in.size()>=3) centro.free = false;
		else centro.free = true;
	}
}
