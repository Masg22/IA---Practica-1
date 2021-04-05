package IA.Red;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import IA.Red.Estat.ConnexSensor;

public class DibuixNetwork extends Dibuix {

	Estat estat; // Instància activa de la ciutat

	/*
	 * Constructores de la superclasse
	 */
	public DibuixNetwork() {
		super();
	}

	public DibuixNetwork(int n, int m) {
		super(n * 10, m * 10);
		try {
			setColorQuadricula(new Color(200, 200, 200));
			setDibuixarQuadricula(true);
			setTamanyQuadriculaX(10);
			setTamanyQuadriculaY(10);
		} catch (Exception e) {
			System.err.println("Excepcio: " + e.toString());
		}
	}

	/*
	 * Constructora de la classe, donant el tamany del dibuix, i les dimensions de
	 * la quadricula (M i N). S'inicialitza amb els vectors de sensors,
	 * centres i amb les connexions.
	 */
	public DibuixNetwork(Estat newNetwork) {
		super();
		estat = newNetwork;
		init();
	}

	public DibuixNetwork(int width, int height, int n, int m, Sensores sensores, CentrosDatos centros, boolean[][] connexions) {
		super();
		for (int i = 0; i < sensores.size(); i++) {
			Sensor sensor = sensores.get(i);
			dibuixarSensor(sensor.getCoordX(), sensor.getCoordY());
		}
		for (int i = 0; i < centros.size(); i++) {
			Centro centro = centros.get(i);
			dibuixarCentre(centro.getCoordX(), centro.getCoordY());
		}
		try {
			setColorQuadricula(new Color(200, 200, 200));
			setDibuixarQuadricula(true);
			setTamanyQuadriculaX(10);
			setTamanyQuadriculaY(10);
			setRadi(4);
			enquadrar(-10, -10, n * 10, m * 10);
		} catch (Exception e) {
			System.err.println("Excepcio: " + e.toString());
		}
	}

	public void novaNetwork(Estat newNetwork) {
		estat = newNetwork;
		init();
	}

	public void dibuixarSensor(int x, int y) {
		try {
			setColorPunts(Color.red);
			int id = dibuixarPunt(x * 10, y * 10);
			setEtiquetaPunt(id, "" + id);
		} catch (Exception e) {
			System.out.println("Excepcio: " + e.toString());
		}
	}

	public void dibuixarCentre(int x, int y) {
		try {
			setColorPunts(Color.blue);
			int id = dibuixarPunt(x * 10, y * 10);
			setEtiquetaPunt(id, "" + id);
		} catch (Exception e) {
			System.out.println("Excepcio: " + e.toString());
		}
	}

	public void dibuixarConnexio(int a, int b) {
		try {
			dibuixarFletxa(a, b);
		} catch (Exception e) {
			System.out.println("Excepcio: " + e.toString());
		}
	}

	private void init() {
		esborrarTot();
		setColorFletxes(Color.orange);
		Sensores sensores = estat.getSensores();
		CentrosDatos centros = estat.getCentros();

		for (int i = 0; i < sensores.size(); i++) {
			Sensor sensor = sensores.get(i);
			dibuixarSensor(sensor.getCoordX(), sensor.getCoordY());
		}
		for (int i = 0; i < centros.size(); i++) {
			Centro centro = centros.get(i);
			dibuixarCentre(centro.getCoordX(), centro.getCoordY());
		}

		try {
			setColorQuadricula(new Color(200, 200, 200));
			setDibuixarQuadricula(true);
			setTamanyQuadriculaX(10);
			setTamanyQuadriculaY(10);
			setRadi(4);
			enquadrar(-10, -10, estat.getN() * 10, estat.getM() * 10);
		} catch (Exception e) {
			System.out.println("Excepcio: " + e.toString());
		}
	}

	public void actualitzar() throws Exception {
		esborrarFletxes();
		
		boolean[][] connexions = estat.getConnexions();
		for (int i = 0; i < connexions.length; i++) {
			for (int j = 0; j < connexions[i].length; j++) {
				if (connexions[i][j])
					dibuixarConnexio(i, j);
			}
		}

	}

}
