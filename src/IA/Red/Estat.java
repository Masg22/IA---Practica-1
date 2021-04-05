package IA.Red;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import IA.Red.UIMain;
import IA.Red.Estat.ConnexCentro;
import IA.Red.Estat.ConnexSensor;

import java.util.LinkedList;
import java.util.Queue;


public class Estat {
	
	//#################   ATRIBUTS   ############################### 

	final static int MAXINPUTSENSOR = 3;
	final static int MAXINPUTCENTER = 25;
	final static Double LAMBDA = (double) 1;

	private static int N;//UI
	private static int M;//UI
	private static int alfa, beta, gamma;//UI
	private static int nsensors;
	private static int ncentres;
	private boolean connexions[][]; //UI

	static Sensores sensores; 
	static CentrosDatos centros;
	
	private Sensores origSensores;
	private CentrosDatos origCentros;

	// Llistat de Connexions dels sensors
	private ArrayList<ConnexSensor> connexSList;

	// Llistat de Connexions dels centres
	private ArrayList<ConnexCentro> connexCList;

	private Double coste;
	
	// Class definition ConnexSensor
		// conexiones a sensores de 1 a S y conexiones a centros de -1 a -C
		// La connexio a connectionOut 0 simbolitza un sensor que no te conexio de
		// sortida
		public class ConnexSensor {
			private ArrayList<Integer> connectionIn;
			private int connectionOut;
			private Boolean isFree;
			private Double transmission;

			public ConnexSensor() {
				connectionIn = new ArrayList<Integer>(MAXINPUTSENSOR);
				connectionOut = 0;
				isFree = true;
				transmission = 0.0;
			}

			public ConnexSensor(ArrayList<Integer> connectionIn, int connectionOut, Double transmission) {
				this.connectionIn = new ArrayList<Integer>(connectionIn);
				this.connectionOut = connectionOut;
				if (connectionIn.size() >= MAXINPUTSENSOR) {
					this.isFree = false;
				} else {
					this.isFree = true;
				}
				this.transmission = new Double(transmission);
			}

			public ConnexSensor(ConnexSensor cs) {
				connectionIn = new ArrayList<Integer>(cs.connectionIn);
				connectionOut = cs.connectionOut;
				isFree = cs.isFree;
				transmission = cs.transmission;
			}

			//Cridada sobre el primer paràmetre
			public Boolean addConnectionIn(int parentId, int sensorId) {

				Double parentCapacity = sensores.get(parentId-1).getCapacidad();
				Double transmissionParent = connexSList.get(parentId).getTransmission();
				Double transmissionChild = connexSList.get(sensorId).getTransmission();
				
				
				this.setTransmission(transmissionParent + transmissionChild);
				Boolean ret = this.recActTransmission(transmissionChild, parentId);
				
				//if(parentId == 6) System.out.println("add 1 " + transmissionChild);
				//if(parentId == 6) System.out.println("add 2 " + transmissionParent);
				if(!ret) {
					this.setTransmission(transmissionParent);
					//if(parentId == 6) System.out.println("final " + transmissionParent);
				}
				else {
					connectionIn.add(sensorId);
					//if(parentId == 6) System.out.println("final " + (transmissionChild + transmissionParent));
				}

				if (connectionIn.size() >= MAXINPUTSENSOR) {
					isFree = false;
				} else if (this.transmission >= (parentCapacity * 3)) {
					isFree = false;
				}
				return ret;
			}
			
			//Cridada sobre el segon paràmetre
			public Boolean deleteConnexion(int sensorId, int actual) {
				//Elimina sensorId de actual
				
				if(this.getConnectionIn().size() == 0) {
					return false;
				}
				
				connectionIn.remove(connectionIn.indexOf(sensorId));

				Double transmission1 = connexSList.get(sensorId).getTransmission();
				Double transmission2 = connexSList.get(actual).getTransmission()-transmission1;
				//if(actual == 6) System.out.println("delete 1 " + transmission1);
			//	if(actual == 6) System.out.println("delete 2 " + transmission2);
				
				this.setTransmission(transmission2);
				Boolean aux = this.recActTransmission(-transmission1, actual);
				if (aux) {
					isFree = true;
					//if(actual == 6) System.out.println("final " + transmission2);
				}
				else {
					this.setTransmission(connexSList.get(actual).getTransmission()+transmission1);
				//	if(actual == 6) System.out.println("final " + connexSList.get(actual).getTransmission()+transmission1);
					connectionIn.add(sensorId);
					return false;
				}
				return true;
			}

			public Boolean addTransmission(Double tranmission,int id) {
				if(this.getTransmission() + transmission <= sensores.get(id-1).getCapacidad()*3)this.setTransmission(this.getTransmission() + transmission);
				else return false;
				return true;
			}
			
			public Boolean recActTransmission(Double transmissionChange, int actual){
				/*this.transmission += transmissionChange;
				int next = this.connectionOut;
				if (next < 0) { //es un centro
					System.out.println("ConnOut C:" + connectionOut);
					return (connexCList.get(-(next)).actCapacity(transmissionChange));
				}
				else { // es otro sensor
					connexSList.get(next).recActTransmission(transmissionChange, next);
				}
				return false;*/
				
				Queue<Integer> q = new LinkedList<Integer>();
				q.add(this.connectionOut);
				int it = 0;
				//System.out.println("SOY" + actual + it);
				while (! q.isEmpty()) {
					++it;
					Integer connex = q.poll();
					//System.out.println("ESTOY EN" + connex + it);
					if (connex < 0) { // CENTRE
						connexCList.get(- connex).actCapacity(transmissionChange);
						return true;
					}
					else { // SENSOR
						Boolean aux = connexSList.get(connex).addTransmission(transmissionChange, connex);
						if(!aux) {
							Queue<Integer> q1 = new LinkedList<Integer>();
							q1.add(connexSList.get(actual).getConnectionOut());
							Integer del = 0;
							while(!q1.isEmpty() ) {
								del = q1.poll();
								if(connex != del) {
									Boolean aux2 = connexSList.get(del).addTransmission(-transmissionChange, del);
									q1.add(connexSList.get(del).getConnectionOut());
								}
								
							}
							return false;
						}
						q.add(connexSList.get(connex).getConnectionOut());	
						//System.out.println("VOY A" + connexSList.get(connex).getConnectionOut() );

					}
				}
				return false;
			}
			
			public Boolean checkExit(Integer sensorReplaced) {
				if (this.connectionOut == sensorReplaced) return false;
				else if (this.connectionOut < 0) return true;
				else {
						return connexSList.get(this.connectionOut).checkExit(sensorReplaced);
					}				
				/*Queue<Integer> q = new LinkedList<Integer>();
				
				q.add(this.connectionOut);
				
				
				while (! q.isEmpty() ) {
					
					Integer connex = q.poll();
					if (connex < 0) { // CENTRO
						return true;
					}
					else { // SENSOR
						if (connex == sensorReplaced) return false;
						q.add(connexSList.get(connex).getConnectionOut());
					}
				}
				return false;*/
			}
			
			public Boolean checkPropagation(Double trans, int sensorID) {
				if(trans > sensores.get(sensorID -1).getCapacidad() * 2) return false;
				Queue<Integer> q = new LinkedList<Integer>();
				q.add(this.connectionOut); 
				while (! q.isEmpty()) {
					Integer connex = q.poll();
					if (connex < 0) { // CENTRE
						return (connexCList.get(-connex).getRecepction() + trans <= 150);
					}
					else { // SENSOR
						//connexSList.get(connex).addTransmission(transmissionChange, connex);
						if (connexSList.get(connex).getTransmission() + trans > sensores.get(connex -1).getCapacidad() * 3) return  false;
						q.add(connexSList.get(connex).getConnectionOut());	
						//System.out.println("VOY A" + connexSList.get(connex).getConnectionOut() );

					}
				}
				
				return false;
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
				return Double.valueOf(this.transmission);
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
		// Class definition ConnexCentro
		public class ConnexCentro {
			private ArrayList<Integer> connectionIn;
			private Boolean isFree;
			private Double reception; 

			public ConnexCentro() {
				connectionIn = new ArrayList<Integer>(MAXINPUTCENTER);
				isFree = true;
				reception = 0.0;
			}

			public ConnexCentro(ArrayList<Integer> connectionIn) {
				this.connectionIn = new ArrayList<Integer>(connectionIn);
				if (connectionIn.size() >= MAXINPUTCENTER) {
					this.isFree = false;
				} else {
					this.isFree = true; 
				}
				for(int i = 1; i <= connectionIn.size(); ++i) {
					this.reception += connexSList.get(connectionIn.get(i)).getTransmission(); 
				}
			}

			public ConnexCentro(ConnexCentro cc) {
				connectionIn = new ArrayList<Integer>(cc.connectionIn);
				isFree = cc.isFree;
				reception = cc.reception;
			}

			public Boolean addConnectionIn(int sensorId) {
				connectionIn.add(sensorId);
				if(this.reception + connexSList.get(sensorId).getTransmission() <= 150.0)this.reception += connexSList.get(sensorId).getTransmission();
				else return false;
				if (connectionIn.size() >= MAXINPUTCENTER || this.reception == 150.0) {
					isFree = false;
				}
				return true;
			}

			public void deleteConnexion(int sensorId) {
				connectionIn.remove(connectionIn.indexOf(sensorId));
				this.reception -= connexSList.get(sensorId).getTransmission();
				isFree = true;
			}
			
			public Boolean actCapacity(Double capacityChange) {
				if(this.reception + capacityChange <= 150.0)this.reception += capacityChange;
				else return false;
				return true;
			}
			

			// GETTERS
			public ArrayList<Integer> getConnectionIn() {
				return connectionIn;
			}

			public Boolean getIsFree() {
				return isFree;
			}
			public Double getRecepction() {
				return reception;
			}

			// SETTERS
			public void setConnectionIn(ArrayList<Integer> in) {
				this.connectionIn = in;
			}

			public void setIsFree(Boolean free) {
				this.isFree = free;
			}
			public void setRecepction(Double r) {
				this.reception = r;
			}
		}
		
	public void createNetwork(int n, int m, int nsens, int sensSeed, int ncent, int centSeed) {
			coste = 0.0;
			N = n;
			M = m;
			nsensors = nsens;
			ncentres = ncent;

			connexions = new boolean[nsensors + ncentres][nsensors + ncentres]; // Les connexions ja estan a false
			for (int i = 0; i < nsensors + ncentres; i++)
				for (int j = 0; j < nsensors + ncentres; j++)
					connexions[i][j] = false;

			sensores = new Sensores(nsens, sensSeed);
			centros = new CentrosDatos(ncent, centSeed);
			
			origSensores = new Sensores(nsens, sensSeed);
			origCentros = new CentrosDatos(ncent, centSeed);
			
			connexSList.clear();
			connexCList.clear();

			// We add +1 to the number of sensors because we will use positive numbers for
			// the Sensor ID, and
			// negative IDs for the center. In that case, we cannot use the 0 position
			// (there is not a -0 +0 id)

			nsens++;

			connexSList = new ArrayList<ConnexSensor>(nsens);
			for (int i = 0; i < nsens; i++) {
				connexSList.add(new ConnexSensor());
			}

			// We add +1 to the number of centers because we will use positive numbers for
			// the Sensor ID, and
			// negative IDs for the center. In that case, we cannot use the 0 position
			// (there is not a -0 +0 id)

			ncent++;

			connexCList = new ArrayList<ConnexCentro>(ncent);
			for (int i = 0; i < ncent; i++) {
				connexCList.add(new ConnexCentro());
			}
		}
	public Estat() {
		N = 100;
		M = 100;
		alfa = 3;
		beta = 2;
		gamma = 1;
		nsensors = 100;
		ncentres = 4;

		sensores = new Sensores(nsensors, 1234);
		centros = new CentrosDatos(ncentres, 4321);
		
		origSensores = new Sensores(nsensors, 1234);
		origCentros = new CentrosDatos(ncentres, 4321);

		connexSList = new ArrayList<ConnexSensor>(nsensors);
		connexCList = new ArrayList<ConnexCentro>(ncentres);

		coste = 0.0;
	}

	public Estat(int nsens, int sensSeed, int ncent, int centSeed) {

		sensores = new Sensores(nsens, sensSeed);
		centros = new CentrosDatos(ncent, centSeed);
		
		origSensores = new Sensores(nsens, sensSeed);
		origCentros = new CentrosDatos(ncent, centSeed);
		
		nsensors = nsens;
		ncentres = nsens;
		
		connexions = new boolean[nsensors + ncentres][nsensors + ncentres]; // Les connexions ja estan a false
		for (int i = 0; i < nsensors + ncentres; i++)
			for (int j = 0; j < nsensors + ncentres; j++)
				connexions[i][j] = false;

		// We add +1 to the number of sensors because we will use positive numbers for
		// the Sensor ID, and
		// negative IDs for the center. In that case, we cannot use the 0 position
		// (there is not a -0 +0 id)

		nsens++;

		connexSList = new ArrayList<ConnexSensor>(nsens);
		for (int i = 0; i < nsens; i++) {
			connexSList.add(new ConnexSensor());
		}

		// We add +1 to the number of centers because we will use positive numbers for
		// the Sensor ID, and
		// negative IDs for the center. In that case, we cannot use the 0 position
		// (there is not a -0 +0 id)

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

		boolean aux[][] = estat.getConnexions();
		connexions = new boolean[aux.length][aux[0].length];
		for (int i = 0; i < nsensors + ncentres; i++)
			for (int j = 0; j < nsensors + ncentres; j++)
				connexions[i][j] = aux[i][j];

		// We have to create a shadow copy of the ArrayList, not just the
		// reference like "connexSList = estat.getConnexSList();"
		connexSList = new ArrayList<ConnexSensor>(estat.getConnexSList());
		connexCList = new ArrayList<ConnexCentro>(estat.getConnexCList());
		coste =  new Double(estat.getCoste());
	}

	// * Functions *

	public void generarEstatInicial(int tipus) {
		switch (tipus) {
		case 0:
			solucioInicial1();
			break;
		case 1:
			solucioInicial2();
			break;
		case 2:
			solucioInicial3();
			break;
		}
	}

	// 
	public void solucioInicial1() {
		coste = 0.0;
		
		sensores.clear();
		centros.clear();
		
		sensores.addAll(origSensores);
		centros.addAll(origCentros);
		
		//System.out.println("SOL1  S:" + sensores.toString());
		//System.out.println("SOL1  C:" + centros.toString());
		
		connexSList.clear();
		connexCList.clear();

		connexSList = new ArrayList<ConnexSensor>(nsensors+1);
		for (int i = 0; i < nsensors+1; i++) {
			connexSList.add(new ConnexSensor());
		}

		connexCList = new ArrayList<ConnexCentro>(ncentres+1);
		for (int i = 0; i < ncentres+1; i++) {
			connexCList.add(new ConnexCentro());
		}
		
		for (int i = 0; i < nsensors + ncentres; i++)
			for (int j = 0; j < nsensors + ncentres; j++)
				connexions[i][j] = false;
		/*
		Collections.sort(sensores, new Comparator<Sensor>() {
			@Override
			public int compare(Sensor s1, Sensor s2) {
				// We sort the sensors with the distance from the 0,0 coordinate
				Integer distS1 = (s1.getCoordX() * s1.getCoordX()) + (s1.getCoordY() * s1.getCoordY());
				Integer distS2 = (s2.getCoordX() * s2.getCoordX()) + (s2.getCoordY() * s2.getCoordY());

				return (distS2.compareTo(distS1));
			}
		});

		Collections.sort(centros, new Comparator<Centro>() {
			@Override
			public int compare(Centro c1, Centro c2) {
				// We sort the centers with the distance from the 0,0 coordinate
				Integer distC1 = (c1.getCoordX() * c1.getCoordX()) + (c1.getCoordY() * c1.getCoordY());
				Integer distC2 = (c2.getCoordX() * c2.getCoordX()) + (c2.getCoordY() * c2.getCoordY());

				return (distC2.compareTo(distC1));
			}
		});
		*/
		
		UIMain.modificaNetworkRePaint(this);

		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);
		

		for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).setTransmission(sensores.get(i).getCapacidad());
		}

		for (int j = 0, jc = 1; j < centros.size(); j++, jc++) {
			for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
				if (!sensorsConnected[i]) {
					if (connexCList.get(jc).getIsFree() && (connexSList.get(is).getTransmission() + connexCList.get(jc).getRecepction() <= 150.0)) {
						connexSList.get(is).setConnectionOut(-jc);
						connexCList.get(jc).addConnectionIn(is);
						connexions[i][nsensors+j] = true;
						sensorsConnected[i] = true;
					}
				}
			}
		}

		for (int i = 0, is = 1; i < sensorsConnected.length; i++, is++) {
			if (!sensorsConnected[i]) {
				for (int j = 0, js = 1; !sensorsConnected[i]; j++, js++) {
					if (!sensorsConnected[i]) {
						if (connexSList.get(js).getIsFree() && sensorsConnected[j] && ((connexSList.get(is).getTransmission()
								+ connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad() * 3)) {
							Boolean aux = connexSList.get(js).addConnectionIn(js, is);
							if(aux) {
								connexions[i][j] = true;
								sensorsConnected[i] = true;	
								connexSList.get(is).setConnectionOut(js);
							}
						}
					}
				}
			}
		}
		//System.out.println("\n" + "SOL INICIAL: " + "\n");
		//System.out.println("\n" + connexionesToString());
		calcularCoste();
	}

	public void solucioInicial2() {
		coste = 0.0;
		
		connexSList.clear();
		connexCList.clear();

		connexSList = new ArrayList<ConnexSensor>(nsensors+1);
		for (int i = 0; i < nsensors+1; i++) {
			connexSList.add(new ConnexSensor());
		}

		connexCList = new ArrayList<ConnexCentro>(ncentres+1);
		for (int i = 0; i < ncentres+1; i++) {
			connexCList.add(new ConnexCentro());
		}
		
		for (int i = 0; i < nsensors + ncentres; i++)
			for (int j = 0; j < nsensors + ncentres; j++)
				connexions[i][j] = false;
		
		Collections.sort(sensores, new Comparator<Sensor>() {
			@Override
			public int compare(Sensor s1, Sensor s2) {
				// We sort the sensors with the distance from the 0,0 coordinate
				Integer distS1 = (s1.getCoordX() * s1.getCoordX()) + (s1.getCoordY() * s1.getCoordY());
				Integer distS2 = (s2.getCoordX() * s2.getCoordX()) + (s2.getCoordY() * s2.getCoordY());

				return (distS1.compareTo(distS2));
			}
		});
		
		Collections.sort(centros, new Comparator<Centro>() {
			@Override
			public int compare(Centro c1, Centro c2) {
				// We sort the centers with the distance from the 0,0 coordinate
				Integer distC1 = (c1.getCoordX() * c1.getCoordX()) + (c1.getCoordY() * c1.getCoordY());
				Integer distC2 = (c2.getCoordX() * c2.getCoordX()) + (c2.getCoordY() * c2.getCoordY());

				return (distC1.compareTo(distC2));
			}
		});
		
		
	//	System.out.println("SOL2  S:" + sensores.toString());
	//	System.out.println("SOL2  C:" + centros.toString());
		
		UIMain.modificaNetworkRePaint(this);

		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);

		for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).setTransmission(sensores.get(i).getCapacidad());
		}

		for (int j = 0, jc = 1; j < centros.size(); j++, jc++) {
			for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
				if (!sensorsConnected[i]) {
					if (connexCList.get(jc).getIsFree() && (connexSList.get(is).getTransmission() + connexCList.get(jc).getRecepction() <= 150.0)) {
						connexSList.get(is).setConnectionOut(-jc);
						connexCList.get(jc).addConnectionIn(is);
						connexions[i][nsensors+j] = true;
						sensorsConnected[i] = true;
					}
				}
			}
		}

		for (int i = 0, is = 1; i < sensorsConnected.length; i++, is++) {
			if (!sensorsConnected[i]) {
				for (int j = sensores.size() - 1, js = sensores.size(); !sensorsConnected[i]; j--, js--) {
					if (!sensorsConnected[i]) {
						if (connexSList.get(js).getIsFree() && sensorsConnected[j] && ((connexSList.get(is).getTransmission()
								+ connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad() * 3)) {
							Boolean aux = connexSList.get(js).addConnectionIn(js, is);
							if(aux) {
								connexions[i][j] = true;
								sensorsConnected[i] = true;	
								connexSList.get(is).setConnectionOut(js);
							}
						}
					}
				}
			}
		}
		calcularCoste();
	}

	public void solucioInicial3() {
		coste = 0.0;
		
		connexSList.clear();
		connexCList.clear();

		connexSList = new ArrayList<ConnexSensor>(nsensors+1);
		for (int i = 0; i < nsensors+1; i++) {
			connexSList.add(new ConnexSensor());
		}

		connexCList = new ArrayList<ConnexCentro>(ncentres+1);
		for (int i = 0; i < ncentres+1; i++) {
			connexCList.add(new ConnexCentro());
		}
		
		for (int i = 0; i < nsensors + ncentres; i++)
			for (int j = 0; j < nsensors + ncentres; j++)
				connexions[i][j] = false;
		
		Collections.sort(sensores, new Comparator<Sensor>() {
			@Override
			public int compare(Sensor s1, Sensor s2) {
				// We sort the sensors with the distance from the 0,0 coordinate
				Integer distS1 = (s1.getCoordX() * s1.getCoordX()) + (s1.getCoordY() * s1.getCoordY());
				Integer distS2 = (s2.getCoordX() * s2.getCoordX()) + (s2.getCoordY() * s2.getCoordY());

				return (distS1.compareTo(distS2));
			}
		});

		Collections.sort(centros, new Comparator<Centro>() {
			@Override
			public int compare(Centro c1, Centro c2) {
				// We sort the centers with the distance from the 0,0 coordinate
				Integer distC1 = (c1.getCoordX() * c1.getCoordX()) + (c1.getCoordY() * c1.getCoordY());
				Integer distC2 = (c2.getCoordX() * c2.getCoordX()) + (c2.getCoordY() * c2.getCoordY());

				return (distC1.compareTo(distC2));
			}
		});

		
		UIMain.modificaNetworkRePaint(this);

		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);
		

		for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).setTransmission(sensores.get(i).getCapacidad());
		}

		for (int j = 0, jc = 1; j < centros.size(); j++, jc++) {
			for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
				if (!sensorsConnected[i]) {
					if (connexCList.get(jc).getIsFree() && (connexSList.get(is).getTransmission() + connexCList.get(jc).getRecepction() <= 150.0)) {
						connexSList.get(is).setConnectionOut(-jc);
						connexCList.get(jc).addConnectionIn(is);
						connexions[i][nsensors+j] = true;
						sensorsConnected[i] = true;
					}
				}
			}
		}

		for (int i = 0, is = 1; i < sensorsConnected.length; i++, is++) {
			if (!sensorsConnected[i]) {
				for (int j = 0, js = 1; !sensorsConnected[i]; j++, js++) {
					if (!sensorsConnected[i]) {
						if (connexSList.get(js).getIsFree() && sensorsConnected[j] && ((connexSList.get(is).getTransmission()
								+ connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad() * 3)) {
						
							Boolean aux = connexSList.get(js).addConnectionIn(js, is);
							if(aux) {
								connexions[i][j] = true;
								sensorsConnected[i] = true;
								connexSList.get(is).setConnectionOut(js);
							}

						}
					}
				}
			}
		}
		//System.out.println("\n" + "SOL INICIAL: " + "\n");
		//System.out.println("\n" + connexionesToString());
		calcularCoste();
	}
	public void calcularCoste() {
		coste = 0.0;
		for (int i = 1; i < connexSList.size(); ++i) {

			int x1 = sensores.get(i - 1).getCoordX();
			int y1 = sensores.get(i - 1).getCoordY();
			int x2 = 0;
			int y2 = 0;

			int idOut = connexSList.get(i).getConnectionOut();
			Double transmission = connexSList.get(i).getTransmission();

			if (idOut == 0) {
				coste += 0.0;
			} else {
				if (idOut < 0) {
					x2 = centros.get((-idOut) - 1).getCoordX();
					y2 = centros.get((-idOut) - 1).getCoordY();
				} else {
					x2 = sensores.get(idOut - 1).getCoordX();
					y2 = sensores.get(idOut - 1).getCoordY();
				}
				Double dist2 = Math.pow(Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))),2);
				coste +=  dist2 * (LAMBDA * transmission);
				
				System.out.println("COSTE:  " + coste + "\n");
				System.out.println("TRANSMISSION:  " + transmission + "\n");

			}
		}
		
	}

	public void eraseCost(int x1, int y1, int x2, int y2, Double trans) {
	//	System.out.println("---------------" + "\n");
	//	System.out.println("COSTE" + coste + "\n");
		coste -= Math.pow(Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))),2) * (trans);
	
	//	System.out.println("Transmission added" + trans + "\n");
	//	System.out.println("Distance added" + Math.pow(Math.sqrt(((x1 - y1) * (x1 - y1) + (x2 - y2) * (x2 - y2))),2) + "\n");
		
	//	System.out.println("COSTE" + coste + "\n");
	//	if(coste < 0) coste = 0.0;
	}
	
	public void sumCost(int x1, int y1, int x2, int y2, Double trans) {
	//	System.out.println("++++++++++++++++++++" + "\n");
		//System.out.println("COSTE" + coste + "\n");

		coste +=  Math.pow(Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))),2) * (trans);
		
		//System.out.println("Transmission added" + trans + "\n");
	//	System.out.println("Distance added" + Math.pow(Math.sqrt(((x1 - y1) * (x1 - y1) + (x2 - y2) * (x2 - y2))),2) + "\n");
		
	//	System.out.println("COSTE" + coste + "\n");
	}
	
	public void actualitzarParametres(int a, int b, int g, int mnr) {
		alfa = a;
		beta = b;
		gamma = g;
		calcularCoste();
	}

	public Boolean isGoalState() {
		return false;
	}
	public Boolean createConnexionS(Integer sensorID, Integer newConnexID) {
		//System.out.println("\n" + "PRE-OPERADOR: " + "\n");
		//System.out.println("\n" + connexionesToString());
		
		int x1,y1,x2,y2;
		Double trans;
		int oldConnexID = connexSList.get(sensorID).getConnectionOut();
		
		/*System.out.println("ANTIGA CONNEX: " + oldConnexID);
		System.out.println("NOVA CONNEX: " + newConnexID);
		System.out.println("SOC CONNEX: " + sensorID);
		*/
		
		if(newConnexID == sensorID || oldConnexID == newConnexID) return false;
		
		//if(newConnexID > 0 && !(connexSList.get(newConnexID).checkExit(sensorID))) return false;
		
		//if(newConnexID > 0 && !connexSList.get(newConnexID).checkPropagation(connexSList.get(sensorID).getTransmission(), newConnexID)) return false;

		if (newConnexID < 0) { // Si la nova Conexio es a un Centre
			if (connexCList.get(-newConnexID).getIsFree() && connexSList.get(sensorID).getTransmission() + connexCList.get(-newConnexID).getRecepction() <= 150.0 ) {
				if (oldConnexID < 0) { // Si sensorID estaba conectat a un Centre
					
					
					x1 = sensores.get(sensorID-1).getCoordX();
					y1 = sensores.get(sensorID-1).getCoordY();
					
					x2 = centros.get((-oldConnexID)-1).getCoordX();
					y2 = centros.get((-oldConnexID)-1).getCoordY();
					
					trans = connexSList.get(sensorID).getTransmission();
					
					eraseCost(x1,y1,x2,y2,trans);					
					connexCList.get(-oldConnexID).deleteConnexion(sensorID);
					
					
				} else { // Si sensorID estaba conectat a un Sensor
					x1 = sensores.get(sensorID-1).getCoordX();
					y1 = sensores.get(sensorID-1).getCoordY();
					
					x2 = sensores.get(oldConnexID-1).getCoordX();
					y2 = sensores.get(oldConnexID-1).getCoordY();
					
					trans = connexSList.get(sensorID).getTransmission();
					
					Boolean aux = connexSList.get(oldConnexID).deleteConnexion(sensorID, oldConnexID);
					if(!aux) return false;
					eraseCost(x1,y1,x2,y2,trans);
				}

				connexSList.get(sensorID).setConnectionOut(newConnexID);
				Boolean aux = connexCList.get(-newConnexID).addConnectionIn(sensorID);
				if(!aux) System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRROOOOOOOOORRRRRRRRRRRRRRRRRRRR");
				x1 = sensores.get(sensorID-1).getCoordX();
				y1 = sensores.get(sensorID-1).getCoordY();
				
				x2 = centros.get((-newConnexID)-1).getCoordX();
				y2 = centros.get((-newConnexID)-1).getCoordY();
				
				trans = connexSList.get(sensorID).getTransmission();
				
				sumCost(x1,y1,x2,y2,trans);
		

				return true;
			} else {

				return false;
			}
		} else { // Si la nova Conexio es a un Sensor
			
			Double res_cap_nou = sensores.get((newConnexID-1)).getCapacidad() * 3;
			Double trans_act = connexSList.get(sensorID).getTransmission()+ connexSList.get(newConnexID).getTransmission(); 
			if (trans_act <= res_cap_nou && connexSList.get(newConnexID).getIsFree() /*&& connexSList.get(newConnexID).checkPropagation(connexSList.get(sensorID).getTransmission(), newConnexID)*/) {
					if (oldConnexID < 0) { // Si sensorID estaba conectat a un Centre
						
						x1 = sensores.get(sensorID-1).getCoordX();
						y1 = sensores.get(sensorID-1).getCoordY();
						
						x2 = centros.get((-oldConnexID)-1).getCoordX();
						y2 = centros.get((-oldConnexID)-1).getCoordY();
						
						trans = connexSList.get(sensorID).getTransmission();
						
						eraseCost(x1,y1,x2,y2,trans);
						//System.out.println("COST despres erase" + coste + "\n");

						
						connexCList.get(-oldConnexID).deleteConnexion(sensorID);
					} else { // Si sensorID estaba conectat a un Sensor
						x1 = sensores.get(sensorID-1).getCoordX();
						y1 = sensores.get(sensorID-1).getCoordY();
						
						x2 = sensores.get(oldConnexID-1).getCoordX();
						y2 = sensores.get(oldConnexID-1).getCoordY();
						
						trans = connexSList.get(sensorID).getTransmission();
					
						Boolean aux = connexSList.get(oldConnexID).deleteConnexion(sensorID, oldConnexID);
						if(!aux) {
							//eraseCost(x1,y1,x2,y2,trans);
							return false;
						}
						
						eraseCost(x1,y1,x2,y2,trans);
						//System.out.println("COST despres erase" + coste + "\n");
						
	
					}

					Boolean aux = connexSList.get(newConnexID).addConnectionIn(newConnexID, sensorID);
					if(!aux) {
						if (oldConnexID < 0) { // Si sensorID estaba conectat a un Centre
							
							x1 = sensores.get(sensorID-1).getCoordX();
							y1 = sensores.get(sensorID-1).getCoordY();
							
							x2 = centros.get((-oldConnexID)-1).getCoordX();
							y2 = centros.get((-oldConnexID)-1).getCoordY();
							connexCList.get(-oldConnexID).addConnectionIn(sensorID);	
							trans = connexSList.get(sensorID).getTransmission();
							
							sumCost(x1,y1,x2,y2,trans);


						} else { // Si sensorID estaba conectat a un Sensor
							x1 = sensores.get(sensorID-1).getCoordX();
							y1 = sensores.get(sensorID-1).getCoordY();
							
							x2 = sensores.get(oldConnexID-1).getCoordX();
							y2 = sensores.get(oldConnexID-1).getCoordY();
							Boolean aux2 = connexSList.get(oldConnexID).addConnectionIn(oldConnexID, sensorID);	
						//	if(!aux2) 	System.out.println("#################################################################");
							trans = connexSList.get(sensorID).getTransmission();
							
							sumCost(x1,y1,x2,y2,trans);
						//	System.out.println("COST despres erase" + coste + "\n");
							
		
						}
					}
					else {
						x1 = sensores.get(sensorID-1).getCoordX();
						y1 = sensores.get(sensorID-1).getCoordY();
						
						x2 = sensores.get(newConnexID-1).getCoordX();
						y2 = sensores.get(newConnexID-1).getCoordY();
						
						trans = connexSList.get(sensorID).getTransmission();
						
						sumCost(x1,y1,x2,y2,trans);
						connexSList.get(sensorID).setConnectionOut(newConnexID);
	
						return true;
					}
					
				}
			else {
				//System.out.println("\n" + "NOVA CONEX SENSOR FALSE: " + "\n");
				//System.out.println("\n" + connexionesToString());
				//System.out.println("COST " + coste + "\n");

				return false;
			}
		}
		return false;
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
			ArrayList<ConnexSensor> aux = new ArrayList<ConnexSensor>();
			for(int i = 0; i < connexSList.size(); ++i) {
				ConnexSensor aux1 = new ConnexSensor(connexSList.get(i));
			//	ArrayList<Integer> n = new ArrayList<Integer>(connexSList.get(i).getConnectionIn());
				Double trans1 = new Double(aux1.getTransmission());
				aux1.setTransmission(trans1);
			//	aux1.setConnectionIn(n);
				aux.add(aux1);
			}
			return aux;
		}

		public void setConnexSList(ArrayList<ConnexSensor> connexS) {
			this.connexSList = connexS;
		}

		public ArrayList<ConnexCentro> getConnexCList() {
			ArrayList<ConnexCentro> aux = new ArrayList<ConnexCentro>();
			for(int i = 0; i < connexCList.size(); ++i) {
				ConnexCentro aux1 = new ConnexCentro(connexCList.get(i));
				//Double recep = new Double(connexCList.get(i).getRecepction());
				//aux1.setRecepction(recep);
				aux.add(aux1);
			}
			return aux;
		}

		public void setConnexCList(ArrayList<ConnexCentro> connexC) {
			this.connexCList = connexC;
		}

		public Double getCoste() {
			return coste;
		}

		public void setCoste(Double coste) {
			this.coste = coste;
		}

		public int getN() {
			return N;
		}

		public void setN(int n) {
			N = n;
		}

		public int getM() {
			return M;
		}

		public void setM(int m) {
			M = m;
		}

		public int getAlfa() {
			return alfa;
		}

		public int getBeta() {
			return beta;
		}

		public int getGamma() {
			return gamma;
		}

		public int getNsensors() {
			return nsensors;
		}

		public int getNcentres() {
			return ncentres;
		}

		public boolean[][] getConnexions() {
			return connexions;
		}
		
		public String connexionesToString() {
            StringBuffer res = new StringBuffer();
            res.append("CONNEXIONES\n");
            calcularCoste();
            actualitzarCentres();
            for(int i = 1; i < connexCList.size(); ++i) {
                res.append("Centro " + (i-1) + "con capacidad: " + connexCList.get(i).getRecepction() + "\n");
            }
            for(int i = 1; i < connexSList.size(); ++i) {
                res.append("Sensor" + (i-1) + ", con cap: "+ sensores.get(i-1).getCapacidad() +", con transmision: " + connexSList.get(i).getTransmission()+" , ");
                int out = connexSList.get(i).getConnectionOut();
                if(out < 0) res.append("Connectat a " +(out)+"\n");
                else res.append("Connectat a " +(out-1)+"\n");
            }
            return res.toString();
        }
		
		public void finalUI() {

			connexions = new boolean[nsensors + ncentres][nsensors + ncentres]; // Les connexions ja estan a false
			for (int i = 0; i < nsensors + ncentres; i++)
				for (int j = 0; j < nsensors + ncentres; j++)
					connexions[i][j] = false;
			
			for(int i = 1; i < connexSList.size(); ++i) {
				int out = connexSList.get(i).getConnectionOut();
				if(out < 0) {
					connexions[i-1][sensores.size()+(-out-1)] = true;
				}
				else connexions[i-1][out-1] = true;
			}
		}
		
		public void actualitzarCentres() {
            //System.out.println("#################################");
            for(int i = 1; i < connexCList.size(); ++i) {

                ArrayList<Integer> a = connexCList.get(i).getConnectionIn();
                Double tot = 0.0;
                for(int k = 0; k < a.size(); ++k) {
                    Double t = connexSList.get(a.get(k)).getTransmission();
                    //System.out.println("TRANSMISIO A CENTRE" + (i-1) + " de" + t);
                    tot += t;
                }
                connexCList.get(i).setRecepction(tot);
                //System.out.println("FINAL " + (i-1) + " de" + connexCList.get(i).reception);
            }
        }
}
