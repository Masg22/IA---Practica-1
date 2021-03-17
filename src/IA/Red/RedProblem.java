package IA.Red;

import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Random;
import java.util.Scanner;

import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;


public class RedProblem {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        Random random = new Random();
		int opcion = -1;
		while(opcion != 0) {
			display();
			opcion = in.nextInt();
			if(opcion == 1) {
				System.out.println("Seed or -1 for a random one");
				Integer seed = in.nextInt();
				if(seed < 0) seed = random.nextInt();
				
				System.out.println("Number of sensors");
				Integer nsens = in.nextInt();
				
				System.out.println("Number of centers");
				Integer ncent = in.nextInt();
				
				Estat estat = new Estat(nsens, seed, ncent, seed);
				
			}
			else if(opcion == 2) {
				
			}
		}
		
	}
	
	public static void display () {
		System.out.println("HillClimbing: 1");
		System.out.println("Simulated Annealing: 2");
		System.out.println("Salir: 0");
	}

}
