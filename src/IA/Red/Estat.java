package IA.Red;

import java.util.ArrayList;

public class Estat {

	final static int MAXINPUTSENSOR = 3;
	final static int MAXINPUTCENTER = 25;

	private static Sensores sensores;
	private static CentrosDatos centros;

	// Llistat de Connexions dels sensors
	private ArrayList<ConnexSensor> connexS;
	
	// Llistat de Connexions dels centres
	private ArrayList<ConnexCentro> connexC;
	
	// Vector de 100 posicions on definim el Grid (solucio) actual
	private int networkGrid[];

	private double coste;

	// Class definition ConnexSensor
	// conexiones a sensores de 0 a S y conexiones a centros de -1 a -C
	public class ConnexSensor {
		private int id;
		private ArrayList<Integer> connectionIn;
		private int connectionOut;
		private Boolean isFree;

		public ConnexSensor() {
			id = -1;
			connectionIn = new ArrayList<Integer>();
			connectionOut = -1;
			isFree = true;
		}

		public ConnexSensor(int id, ArrayList<Integer> connectionIn, int connectionOut) {
			this.id = id;
			this.connectionIn = connectionIn;
			this.connectionOut = connectionOut;
			if(connectionIn.size() >= MAXINPUTSENSOR) {
				this.isFree = false;
			} else {
				this.isFree = true;
			}
		}

		public ConnexSensor(ConnexSensor cs) {
			id = cs.id;
			connectionIn = cs.connectionIn;
			connectionOut = cs.connectionOut;
			isFree = cs.isFree;
		}
		
		public int setConnection(int sensorId) {
			connectionIn.add(sensorId);
			if(connectionIn.size() >= MAXINPUTSENSOR) {
				isFree = false;
			}
			return id;
		}
		
		public void deleteConnexion(int sensorId) {
			connectionIn.remove(connectionIn.indexOf(sensorId));
			if(connectionIn.size() < MAXINPUTCENTER) {
				isFree = true;
			}
		}

		// GETTERS
		public int getId() {
			return id;
		}
		
		public ArrayList<Integer> getConnectionIn() {
			return connectionIn;
		}

		public int getConnectionOut() {
			return connectionOut;
		}

		public Boolean getIsFree() {
			return isFree;
		}

		// SETTERS
		public void setId(int id) {
			this.id = id;
		}
		
		public void setConnectionIn(ArrayList<Integer> in) {
			this.connectionIn = in;
		}

		public void setConnectionOut(int out) {
			this.connectionOut = out;
		}

		public void setIsFree(Boolean free) {
			this.isFree = free;
		}
	}

	// Class definition ConnexSensor
	public class ConnexCentro {
		private int id;
		private ArrayList<Integer> connectionIn;
		private Boolean isFree;

		public ConnexCentro() {
			connectionIn = new ArrayList<Integer>();
			isFree = true;
		}

		public ConnexCentro(int id, ArrayList<Integer> connectionIn) {
			this.id = id;
			this.connectionIn = connectionIn;
			if(connectionIn.size() >= MAXINPUTCENTER) {
				this.isFree = false;
			} else {
				this.isFree = true;
			}
		}

		public ConnexCentro(ConnexCentro cc) {
			id = cc.id;
			connectionIn = cc.connectionIn;
			isFree = cc.isFree;
		}
		
		public int setConnection(int sensorId) {
			connectionIn.add(sensorId);
			if(connectionIn.size() >= MAXINPUTCENTER) {
				isFree = false;
			}
			return id;
		}
		
		public void deleteConnexion(int sensorId) {
			connectionIn.remove(connectionIn.indexOf(sensorId));
			if(connectionIn.size() >= MAXINPUTCENTER) {
				isFree = false;
			}
		}

		// GETTERS
		public int getId() {
			return id;
		}
		
		public ArrayList<Integer> getConnectionIn() {
			return connectionIn;
		}

		public Boolean getIsFree() {
			return isFree;
		}

		// SETTERS
		public void setId(int id) {
			this.id = id;
		}
		
		public void setConnectionIn(ArrayList<Integer> in) {
			this.connectionIn = in;
		}

		public void setIsFree(Boolean free) {
			this.isFree = free;
		}
	}

	// *************Constructoras*************
	public Estat(int nsens, int sens_seed, int ncent, int cent_seed) {

		sensores = new Sensores(nsens, sens_seed);

		centros = new CentrosDatos(ncent, cent_seed);

		connexS = new ArrayList<ConnexSensor>(nsens);

		connexC = new ArrayList<ConnexCentro>(ncent + 1);
		
		// Call the init first solution function
		networkGrid = new int[100];

		coste = 0.0;
	}

	public Estat(Estat estat) {
		sensores = estat.getSensores();
		centros = estat.getCentros();
		connexS = estat.getConnexS();
		connexC = estat.getConnexC();
		networkGrid = estat.getNetworkGrid();
		coste = estat.getCoste();
	}

	// *************Occupacy para sensores y centros*************
	// Controlar cada vez que añadimos una connexion a un sensor o centro.
	public void setOccupacyS(ConnexSensor sensor) {
		if (sensor.getConnectionIn().size() >= MAXINPUTSENSOR)
			sensor.setIsFree(false);
		else
			sensor.setIsFree(true);
	}

	public void setOccupacyC(ConnexCentro centro) {
		if (centro.getConnectionIn().size() >= MAXINPUTCENTER)
			centro.setIsFree(false);
		else
			centro.setIsFree(true);
	}

	// operadors

	// pre el Sensor "sensor" y el sensor i/o centro "newConnex" tienen connexiones
	// libres

	public void moveConnexS(Integer sensor, Integer newConnex) {
		int oldConnex = connexS.get(sensor).getConnectionOut();
		if (oldConnex < 0) { // CENTRO
			connexC.get(- oldConnex).deleteConnexion(sensor);
		}
		else { // SENSOR
			connexS.get(oldConnex).deleteConnexion(sensor);
		}
		
		if (newConnex < 0) { // CENTRO
			connexS.get(sensor).setConnection(newConnex);
			connexC.get(- newConnex).setConnection(sensor);
		} 
		else { // SENSOR
			connexS.get(sensor).setConnectionOut(newConnex);
			connexS.get(newConnex).setConnection(sensor);
		}
		
	}

	// pre value es 1 2 o 5
	public void changeCapacityS(Integer sensorId, Integer value) {
		sensores.get(sensorId).setCapacidad(value);
	}

	public void changePosS(Integer sensorId, Integer x, Integer y) {
		sensores.get(sensorId).setCoordX(x);
		sensores.get(sensorId).setCoordY(y);
	}

	// * Getters and Setters *

	public Sensores getSensores() {
		return sensores;
	}

	public void setSensores(Sensores sensores) {
		Estat.sensores = sensores;
	}

	public CentrosDatos getCentros() {
		return centros;
	}

	public void setCentros(CentrosDatos centros) {
		Estat.centros = centros;
	}

	public ArrayList<ConnexSensor> getConnexS() {
		return connexS;
	}

	public void setConnexS(ArrayList<ConnexSensor> connexS) {
		this.connexS = connexS;
	}

	public ArrayList<ConnexCentro> getConnexC() {
		return connexC;
	}

	public void setConnexC(ArrayList<ConnexCentro> connexC) {
		this.connexC = connexC;
	}

	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}

	public int[] getNetworkGrid() {
		return networkGrid;
	}

	public void setNetworkGrid(int networkGrid[]) {
		this.networkGrid = networkGrid;
	}
}
