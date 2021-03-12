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
		if(centro.in.size()>=3) centro.free = false; // no tendria que ser 25?
		else centro.free = true;
	}
	
	
	// operadors
	
	//pre el Sensor "sensor" y el sensor i/o centro "newConnex" tienen connexiones libres
	
	private void moveConnexS(Integer sensor, Integer newConnex, Boolean output) {
		//queremos solo cambiar la de salida?? o tambien las de entrada ?? 
		// comprovar que el output no esta vacio y entonces modificarlo ??
		
		if (output) {
			connexS.get(sensor).out = newConnex;
			if (newConnex < 0) {
				connexC.get(-newConnex).in.add(sensor);
			}
			else {
				connexS.get(newConnex).in.add(sensor);
			}
		}
		else {
			// ¿?
		}
	}
	
	// necesitamos un moveConnexC ??
	
	//pre value es 1 2 o 5
	private void changeCapacityS(Integer sensor, Integer value) {
		sensores.get(sensor).setCapacidad(value);
	}
	
	private void changePosS(Integer sensor, Integer x, Integer y) {
		sensores.get(sensor).setCoordX(x);
		sensores.get(sensor).setCoordY(y);
	}
	
}
