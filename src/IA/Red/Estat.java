package IA.Red;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import IA.Red.UIMain;
import java.util.LinkedList;
import java.util.Queue;


public class Estat {

	final static int MAXINPUTSENSOR = 3;
	final static int MAXINPUTCENTER = 25;

	private static int N;
	private static int M;
	private static int alfa, beta, gamma;
	private static int nsensors; // numero de sensors
	private static int ncentres; // numero de centres
	private boolean connexions[][]; // la fila i indica les connex del node i

	static Sensores sensores;
	static CentrosDatos centros;
	
	private Sensores origSensores;
	private CentrosDatos origCentros;

	// Llistat de Connexions dels sensors
	private ArrayList<ConnexSensor> connexSList;

	// Llistat de Connexions dels centres
	private ArrayList<ConnexCentro> connexCList;

	private double coste;

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
			this.connectionIn = connectionIn;
			this.connectionOut = connectionOut;
			if (connectionIn.size() >= MAXINPUTSENSOR) {
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

			Double parentCapacity = sensores.get(parentId-1).getCapacidad();
			Double transmission1 = connexSList.get(sensorId).getTransmission();
			
			this.recActTransmission(transmission1);

			if (connectionIn.size() >= MAXINPUTSENSOR) {
				isFree = false;
			} else if (this.transmission == (parentCapacity * 3)) {
				isFree = false;
			}
		}

		public void deleteConnexion(int sensorId) {
			connectionIn.remove(connectionIn.indexOf(sensorId));

			Double transmission1 = connexSList.get(sensorId).getTransmission();
			
			this.recActTransmission(-transmission1);

			isFree = true;
		}

		public void addTranmission(Double tranmission) {
			this.transmission += transmission;
		}
		
		public void recActTransmission(Double transmissionChange){
			/*this.transmission += transmissionChange;
			
			System.out.println("Transmission:" + transmission);
			
			if (this.connectionOut < 0) { //es un centro
				System.out.println("ConnOut C:" + connectionOut);
				connexCList.get(-(this.connectionOut)).actCapacity(transmissionChange);
			}
			else { // es otro sensor
				connexSList.get(this.connectionOut).recActTransmission(transmissionChange);
			}*/
			
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(this.connectionOut);
			while (! q.isEmpty()) {
				Integer connex = q.poll();
				if (connex < 0) { // CENTRE
					connexCList.get(- connex).actCapacity(transmissionChange);
				}
				else { // SENSOR
					connexSList.get(connex).addTransmission(transmissionChange);
					q.add(connexSList.get(connex).getConnectionOut());	
				}
			}
		
		}
		
		public Boolean checkExit(Integer sensorReplaced) {
			/*if (this.connectionOut == sensorReplaced) return false;
			else if (this.connectionOut < 0) return true;
			else {
				return connexSList.get(this.connectionOut).checkExit(sensorReplaced);
			}*/
			
			Queue<Integer> q = new LinkedList<Integer>();
			
			q.add(this.connectionOut);
			
			while (! q.isEmpty()) {
				Integer connex = q.poll();
				if (connex < 0) { // CENTRO
					return true;
				}
				else { // SENSOR
					if (connex == sensorReplaced) return false;
					q.add(connexSList.get(connex).getConnectionOut());
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

		private Double reception; 

		private Double capacity;
		//M: Hay que Modificar la capacidad cada vez que a�adimos un nuevo nodo directamente al centro o lo desconectamos 


		public ConnexCentro() {
			connectionIn = new ArrayList<Integer>(MAXINPUTCENTER);
			isFree = true;
			reception = 0.0;
		}

		public ConnexCentro(ArrayList<Integer> connectionIn) {
			this.connectionIn = connectionIn;
			if (connectionIn.size() >= MAXINPUTCENTER) {
				this.isFree = false;
			} else {
				this.isFree = true; 
			}
			//no salvamos perdidas
			for(int i = 1; i <= connectionIn.size(); ++i) {
				this.reception += connexSList.get(connectionIn.get(i)).getTransmission(); 
			}
		}

		public ConnexCentro(ConnexCentro cc) {
			connectionIn = new ArrayList<Integer>(cc.connectionIn);
			isFree = cc.isFree;
			reception = cc.reception;
		}

		public void addConnectionIn(int sensorId) {
			connectionIn.add(sensorId);
			this.reception += connexSList.get(sensorId).getTransmission();
			if (connectionIn.size() >= MAXINPUTCENTER || this.reception == 150.0) {
				isFree = false;
			}
		}

		public void deleteConnexion(int sensorId) {
			connectionIn.remove(connectionIn.indexOf(sensorId));
			this.reception -= connexSList.get(sensorId).getTransmission();
			isFree = true;
		}
		
		public void actCapacity(Double capacityChange) {
			
			// M: comprobamos aqui que si se suma una capacidad y excede el limite de 150 no se pueda ejecutar la operacion �?
			this.reception += capacityChange;
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

	// * Constructors *

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
		coste = estat.getCoste();
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

	public void solucioInicial1() {
		coste = 0.0;
		
		sensores.clear();
		centros.clear();
		
		sensores.addAll(origSensores);
		centros.addAll(origCentros);
		
		System.out.println("SOL1  S:" + sensores.toString());
		System.out.println("SOL1  C:" + centros.toString());
		
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
		
		//UIMain.modificaNetworkRePaint(this);

		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);

		for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).addTransmission(sensores.get(i).getCapacidad());
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
				for (int j = 0, js = 1; j < sensores.size(); j++, js++) {
					if (!sensorsConnected[i]) {
						if (connexSList.get(js).getIsFree() && sensorsConnected[j] && ((connexSList.get(is).getTransmission()
								+ connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad() * 3)) {
							connexSList.get(is).setConnectionOut(js);
							connexSList.get(js).addConnectionIn(js, is);
							connexions[i][j] = true;
							sensorsConnected[i] = true;
						}
					}
				}
			}
		}

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
		
		
		System.out.println("SOL2  S:" + sensores.toString());
		System.out.println("SOL2  C:" + centros.toString());
		
		//UIMain.modificaNetworkRePaint(this);

		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);

		for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).addTransmission(sensores.get(i).getCapacidad());
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
				for (int j = sensores.size() - 1, js = sensores.size(); j >= 0; j--, js--) {
					if (!sensorsConnected[i]) {
						if (connexSList.get(js).getIsFree() && sensorsConnected[j] && ((connexSList.get(is).getTransmission()
								+ connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad() * 3)) {
							connexSList.get(is).setConnectionOut(js);
							connexSList.get(js).addConnectionIn(js, is);
							connexions[i][j] = true;
							sensorsConnected[i] = true;
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
		
		System.out.println("SOL2  S:" + nsensors);
		
		System.out.println("SOL3  S size:" + connexSList.size());
		System.out.println("SOL3  SCON:" + connexSList.toString());
		
		System.out.println("SOL3  S:" + sensores.toString());
		/*
		for(int i = 0; i < sensores.size(); i++ ) {
			System.out.println("SOL3  S:" + sensores.get(i).toString());
		}
		*/
		
		System.out.println("SOL2  C:" + ncentres);
		
		System.out.println("SOL3  C size:" + connexCList.size());
		System.out.println("SOL3  CCON:" + connexCList.toString());
		
		System.out.println("SOL3  C:" + centros.toString());
		/*
		for(int i = 0; i < centros.size(); i++ ) {
			System.out.println("SOL3  S:" + centros.get(i).toString());
		}
		*/
		
		//UIMain.modificaNetworkRePaint(this);

		Boolean[] sensorsConnected = new Boolean[sensores.size()];
		Arrays.fill(sensorsConnected, Boolean.FALSE);
		

		for (int i = 0, is = 1; i < sensores.size(); i++, is++) {
			connexSList.get(is).addTransmission(sensores.get(i).getCapacidad());
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
				for (int j = 0, js = 1; j < sensores.size(); j++, js++) {
					if (!sensorsConnected[i]) {
						if (connexSList.get(js).getIsFree() && sensorsConnected[j] && ((connexSList.get(is).getTransmission()
								+ connexSList.get(js).getTransmission()) <= sensores.get(j).getCapacidad() * 3)) {
							connexSList.get(is).setConnectionOut(js);
							connexSList.get(js).addConnectionIn(js, is);
							connexions[i][j] = true;
							sensorsConnected[i] = true;
						}
					}
				}
			}
		}

		calcularCoste();
	}

	// calcul de la distancia d(x, y) = squareroot((x1-y1)^2 + (x2-y2)^2)
	// calcul del cost cost(x,y) = d(x,y)^2 * v(x)
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
				coste += ((x1 - y1) * (x1 - y1) + (x2 - y2) * (x2 - y2)) * transmission;
			}
		}
	}

	public void eraseCost(int x1, int y1, int x2, int y2, Double trans) {
		coste -= ((x1 - y1) * (x1 - y1) + (x2 - y2) * (x2 - y2)) * trans;
	}
	
	public void sumCost(int x1, int y1, int x2, int y2, Double trans) {
		coste += ((x1 - y1) * (x1 - y1) + (x2 - y2) * (x2 - y2)) * trans;
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

	// * Operators *

	// TODO Una funci� recursiva que retorno el sumatori de les transmissions que
	// penjen de un Sensor sensorId

	// pre el Sensor "sensor" y el sensor i/o centro "newConnex" tienen connexiones
	// libres
	// Els valors de sensorID i newConnexID ja vener "convertits" en identificadors
	// de sensor o centre segons convingui

	public Boolean createConnexionS(Integer sensorID, Integer newConnexID) {
		int x1,y1,x2,y2;
		Double trans;
		int oldConnexID = connexSList.get(sensorID).getConnectionOut();

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
					
					eraseCost(x1,y1,x2,y2,trans);
					
					connexSList.get(oldConnexID).deleteConnexion(sensorID);
				}

				connexSList.get(sensorID).setConnectionOut(newConnexID);
				connexCList.get(-newConnexID).addConnectionIn(sensorID);
				
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
			if (connexSList.get(newConnexID).getIsFree() /*&& connexSList.get(newConnexID).checkExit(sensorID)*/) {
				if (((connexSList.get(sensorID).getTransmission()
						+ connexSList.get(newConnexID).getTransmission()) <= sensores.get(newConnexID - 1)
								.getCapacidad() * 3)) {
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
						
						eraseCost(x1,y1,x2,y2,trans);
						
						connexSList.get(oldConnexID).deleteConnexion(sensorID);
					}

					connexSList.get(sensorID).setConnectionOut(newConnexID);
					connexSList.get(newConnexID).addConnectionIn(newConnexID, sensorID);
					
					
					x1 = sensores.get(sensorID-1).getCoordX();
					y1 = sensores.get(sensorID-1).getCoordY();
					
					x2 = sensores.get(newConnexID-1).getCoordX();
					y2 = sensores.get(newConnexID-1).getCoordY();
					
					trans = connexSList.get(sensorID).getTransmission();
					
					sumCost(x1,y1,x2,y2,trans);

					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
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
}
