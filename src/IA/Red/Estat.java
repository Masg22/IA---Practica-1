package IA.Red;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Estat {

	final static int MAXINPUTSENSOR = 3;
	final static int MAXINPUTCENTER = 25;

	static Sensores sensores;
	static CentrosDatos centros;

	// Llistat de Connexions dels sensors
	private ArrayList<ConnexSensor> connexSList;
	
	// Llistat de Connexions dels centres
	private ArrayList<ConnexCentro> connexCList;

	private double coste;

	// Class definition ConnexSensor
	// conexiones a sensores de 1 a S y conexiones a centros de -1 a -C
	// La connexio a connectionOut 0 simbolitza un sensor que no te conexio de sortida
	public class ConnexSensor {
		private ArrayList<Integer> connectionIn;
		private int connectionOut;
		private Boolean isFree;
		private Double transmission;


		public ConnexSensor() {
			connectionIn = new ArrayList<Integer>(MAXINPUTSENSOR);
			connectionOut = 0;
			isFree = true;
			connectionOut = 0;
		}

		public ConnexSensor(ArrayList<Integer> connectionIn, int connectionOut, Double transmission) {
			this.connectionIn = connectionIn;
			this.connectionOut = connectionOut;
			if(connectionIn.size() >= MAXINPUTSENSOR) {
				this.isFree = false;
			} else {
				this.isFree = true;
			}
			this.transmission = transmission;
		}

		public ConnexSensor(ConnexSensor cs) {
			connectionIn = new ArrayList<Integer>(cs.connectionIn);
			connectionOut = cs.connectionOut;
			isFree = cs.isFree;
			transmission = cs.transmission;
		}
		
		public void addConnectionIn(int parentId, int sensorId) {
			connectionIn.add(sensorId);
			
			Double parentCapacity = sensores.get(parentId).getCapacidad();
			Double transmission1 = connexSList.get(sensorId).getTransmission();
			this.addTranmission(transmission1);
			
			if(connectionIn.size() >= MAXINPUTSENSOR) {
				isFree = false;
			} else if(this.transmission == (parentCapacity*3)) {
				isFree = false;
			}
		}
		
		
		public void deleteConnexion(int sensorId) {
			connectionIn.remove(connectionIn.indexOf(sensorId));
			
			Double transmission1 = connexSList.get(sensorId).getTransmission();
			this.addTranmission(-transmission1);
			
			isFree = true;
		}
		
		public void addTranmission(Double tranmission) {
			this.transmission += transmission;
		}

		// GETTERS
		public ArrayList<Integer> getConnectionIn() {
			return connectionIn;
		}

		public int getConnectionOut() {
			return connectionOut;
		}

		public Boolean getIsFree() {
			return isFree;
		}

		public Double getTransmission() {
			return transmission;
		}
		
		// SETTERS
		public void setConnectionIn(ArrayList<Integer> in) {
			this.connectionIn = in;
		}

		public void setConnectionOut(int out) {
			this.connectionOut = out;
		}

		public void setIsFree(Boolean free) {
			this.isFree = free;
		}
		
		public void setTransmission(Double transmission) {
			this.transmission = transmission;
		}
	}

	// Class definition ConnexSensor
	public class ConnexCentro {
		private ArrayList<Integer> connectionIn;
		private Boolean isFree;

		public ConnexCentro() {
			connectionIn = new ArrayList<Integer>(MAXINPUTCENTER);
			isFree = true;
		}

		public ConnexCentro(ArrayList<Integer> connectionIn) {
			this.connectionIn = connectionIn;
			if(connectionIn.size() >= MAXINPUTCENTER) {
				this.isFree = false;
			} else {
				this.isFree = true;
			}
		}

		public ConnexCentro(ConnexCentro cc) {
			connectionIn = new ArrayList<Integer>(cc.connectionIn);
			isFree = cc.isFree;
		}
		
		public void addConnectionIn(int sensorId) {
			connectionIn.add(sensorId);
			if(connectionIn.size() >= MAXINPUTCENTER) {
				isFree = false;
			}
		}
		
		public void deleteConnexion(int sensorId) {
			connectionIn.remove(connectionIn.indexOf(sensorId));
			isFree = true;
		}

		// GETTERS
		public ArrayList<Integer> getConnectionIn() {
			return connectionIn;
		}

		public Boolean getIsFree() {
			return isFree;
		}

		// SETTERS
		public void setConnectionIn(ArrayList<Integer> in) {
			this.connectionIn = in;
		}

		public void setIsFree(Boolean free) {
			this.isFree = free;
		}
	}

	
	// * Constructors *
	
	public Estat(int nsens, int sensSeed, int ncent, int centSeed) {

		sensores = new Sensores(nsens, sensSeed);

		centros = new CentrosDatos(ncent, centSeed);
		
		// We add +1 to the number of sensors because we will use positive numbers for the Sensor ID, and 
		// negative IDs for the center. In that case, we cannot use the 0 position (there is not a -0 +0 id)
		
		nsens++;

		connexSList = new ArrayList<ConnexSensor>(nsens);
		for (int i = 0; i < nsens; i++) {
			connexSList.add(new ConnexSensor());
        }

		// We add +1 to the number of centers because we will use positive numbers for the Sensor ID, and 
		// negative IDs for the center. In that case, we cannot use the 0 position (there is not a -0 +0 id)
		
		ncent++;
		
		connexCList = new ArrayList<ConnexCentro>(ncent);
		for (int i = 0; i < ncent; i++) {
			connexCList.add(new ConnexCentro());
        }
		
		coste = 0.0;
	}

	public Estat(Estat estat) {
		sensores = estat.getSensores();
		centros = estat.getCentros();
		// We have to create a shadow copy of the ArrayList, not just the 
		// reference like  "connexSList = estat.getConnexSList();"
		connexSList = new ArrayList<ConnexSensor>(estat.getConnexSList());
		connexCList = new ArrayList<ConnexCentro>(estat.getConnexCList());
		coste = estat.getCoste();
	}

	
	// * Functions *
	
	public void solucioInicial1() {
		Collections.sort(sensores, new Comparator<Sensor>() {
			@Override
			public int compare(Sensor s1, Sensor s2) {
				return ((Double) s2.getCapacidad()).compareTo((Double) s1.getCapacidad());
			}
		});
		
		// It is useful ?
		
	}
	
	public void solucioInicial2() {
		Collections.sort(sensores, new Comparator<Sensor>() {
			@Override
			public int compare(Sensor s1, Sensor s2) {
				// We sort the sensors with the distance from the 0,0 coordinate
				Integer distS1 = (s1.getCoordX()*s1.getCoordX()) + (s1.getCoordY()*s1.getCoordY());
				Integer distS2 = (s2.getCoordX()*s2.getCoordX()) + (s2.getCoordY()*s2.getCoordY());
				
				return (distS2.compareTo(distS1));
			}
		});
		
		Collections.sort(centros, new Comparator<Centro>() {
			@Override
			public int compare(Centro c1, Centro c2) {
				// We sort the centers with the distance from the 0,0 coordinate
				Integer distC1 = (c1.getCoordX()*c1.getCoordX()) + (c1.getCoordY()*c1.getCoordY());
				Integer distC2 = (c2.getCoordX()*c2.getCoordX()) + (c2.getCoordY()*c2.getCoordY());
				
				return (distC2.compareTo(distC1));
			}
		});
		
		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);
		
		
		for(int j = 0, jc = 1; j < centros.size(); j++, jc++) {
			for(int i = 0, is = 1; i < sensores.size(); i++, is++) {
				if(!sensorsConnected[i]) {
					if(connexCList.get(jc).isFree) {
						connexSList.get(is).setConnectionOut(-jc);
						connexCList.get(jc).addConnectionIn(is);
						sensorsConnected[i] = true;
					}
				}
			}
		}
		
		for(int i = 0; i < sensorsConnected.length; i++) {
			if(!sensorsConnected[i]) {
				for(int j = sensores.size() - 1, js = sensores.size(); j >= 0; j--, js--) {
					if(connexSList.get(js).isFree && connexSList.get(js).connectionOut != 0 && (sensores.get(i).getCapacidad() > sensores.get(i).getCapacidad())) {
						connexSList.get(is).setConnectionOut(-jc);
						connexSList.get(is).setConnectionOut(-jc);
						sensorsConnected[i] = true;
					}
				}
			}
		}
	}
	
	public void solucioInicial3() {
		Collections.sort(sensores, new Comparator<Sensor>() {
			@Override
			public int compare(Sensor s1, Sensor s2) {
				// We sort the sensors with the distance from the 0,0 coordinate
				Integer distS1 = (s1.getCoordX()*s1.getCoordX()) + (s1.getCoordY()*s1.getCoordY());
				Integer distS2 = (s2.getCoordX()*s2.getCoordX()) + (s2.getCoordY()*s2.getCoordY());
				
				return (distS2.compareTo(distS1));
			}
		});
		
		Collections.sort(centros, new Comparator<Centro>() {
			@Override
			public int compare(Centro c1, Centro c2) {
				// We sort the centers with the distance from the 0,0 coordinate
				Integer distC1 = (c1.getCoordX()*c1.getCoordX()) + (c1.getCoordY()*c1.getCoordY());
				Integer distC2 = (c2.getCoordX()*c2.getCoordX()) + (c2.getCoordY()*c2.getCoordY());
				
				return (distC2.compareTo(distC1));
			}
		});
		
		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);
		
		for(int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).addTranmission(sensores.get(i).getCapacidad());
		}
		
		for(int j = 0, jc = 1; j < centros.size(); j++, jc++) {
			for(int i = 0, is = 1; i < sensores.size(); i++, is++) {
				if(!sensorsConnected[i]) {
					if(connexCList.get(jc).isFree) {
						connexSList.get(is).setConnectionOut(-jc);
						connexCList.get(jc).addConnectionIn(is);
						sensorsConnected[i] = true;
					}
				}
			}
		}
		
		for(int i = 0, is = 1; i < sensorsConnected.length; i++, is++) {
			if(!sensorsConnected[i]) {
				for(int j = 0, js = 1; j < sensores.size(); j++, js++) {
					if(connexSList.get(js).isFree && sensorsConnected[j] && ((connexSList.get(is).getTransmission() + connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad()*3  )) {
						connexSList.get(is).setConnectionOut(js);
						connexSList.get(js).addConnectionIn(js,is);
						sensorsConnected[i] = true;
					}
				}
			}
		}
	}
	
	// * Operators *

	// pre el Sensor "sensor" y el sensor i/o centro "newConnex" tienen connexiones
	// libres

	public void createConnexionS(Integer sensorID, Integer newConnexID) {
		int oldConnexID = connexSList.get(sensorID).getConnectionOut();
		
		if (oldConnexID == 0) {
			// Si sensorID no estaba conectat a res, no eliminem cap connexio
		} else if (oldConnexID < 0) {									// Si sensorID estaba conectat a un Centre
			connexCList.get(-oldConnexID).deleteConnexion(sensorID);
		} else { 														// Si sensorID estaba conectat a un Sensor
			connexSList.get(oldConnexID).deleteConnexion(sensorID);
		}
		
		if (newConnexID < 0) { 											// Si la nova Conexio es a un Centre
			connexSList.get(sensorID).setConnectionOut(newConnexID);
			connexCList.get(-newConnexID).addConnectionIn(sensorID);
		} else {														// Si la nova Conexio es a un Sensor
			connexSList.get(sensorID).setConnectionOut(newConnexID);
			connexSList.get(newConnexID).addConnectionIn(newConnexID,sensorID);
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

	public ArrayList<ConnexSensor> getConnexSList() {
		return connexSList;
	}

	public void setConnexSList(ArrayList<ConnexSensor> connexS) {
		this.connexSList = connexS;
	}

	public ArrayList<ConnexCentro> getConnexCList() {
		return connexCList;
	}

	public void setConnexCList(ArrayList<ConnexCentro> connexC) {
		this.connexCList = connexC;
	}

	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}
}
