package IA.Red;

import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;

import aima.search.framework.Problem;
import aima.search.framework.Search;
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
				estat.solucioInicial3();
				HillClimbingSearch(estat);
			}
			else if(opcion == 2) {
				
			}
		}
		
	}
    private static void HillClimbingSearch(Estat estat) {
        try {
            Problem problem =  new Problem(estat, new RedSuccessorFunction1(), new RedGoalTest(), new RedHeuristicFunction1());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
           // System.out.print(((Estat) search.getGoalState()).toString());
           // System.out.println("\n" + ((Estat) search.getGoalState()).correspondenciasToString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void display () {
		System.out.println("HillClimbing: 1");
		System.out.println("Simulated Annealing: 2");
		System.out.println("Salir: 0");
	}
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }
}
