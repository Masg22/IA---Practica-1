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
				System.out.println("Sensors Seed or -1 for a random one");
				Integer sseed = in.nextInt();
				if(sseed < 0) sseed = random.nextInt();
				
				System.out.println("Number of sensors");
				Integer nsens = in.nextInt();
				
				System.out.println("Centers Seed or -1 for a random one");
				Integer cseed = in.nextInt();
				if(cseed < 0) cseed = random.nextInt();
				
				System.out.println("Number of centers");
				Integer ncent = in.nextInt();
				
				Estat estat = new Estat(nsens, sseed, ncent, cseed);
				estat.solucioInicial3();
				HillClimbingSearch(estat);
			}
			else if(opcion == 2) {
				System.out.println("Sensors Seed or -1 for a random one");
				Integer sseed = in.nextInt();
				if(sseed < 0) sseed = random.nextInt();
				
				System.out.println("Number of sensors");
				Integer nsens = in.nextInt();
				
				System.out.println("Centers Seed or -1 for a random one");
				Integer cseed = in.nextInt();
				if(cseed < 0) cseed = random.nextInt();
				
				System.out.println("Number of centers");
				Integer ncent = in.nextInt();
				
				Estat estat = new Estat(nsens, sseed, ncent, cseed);
				estat.solucioInicial1();
				SimulatedAnnealingSearch(estat);
			}
		}
		
	}
    private static void HillClimbingSearch(Estat estat) {
        try {
        	long TInicio = System.currentTimeMillis();
            Problem problem =  new Problem(estat, new RedSuccessorFunction1(), new RedGoalTest(), new RedHeuristicFunction1());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            System.out.println(((Estat) search.getGoalState()).toString());
            System.out.println("\n" + ((Estat) search.getGoalState()).connexionesToString());
            System.out.println("COSTE FINAL " + ((Estat) search.getGoalState()).getCoste()+"\n");
            System.out.println("MEJORA DE COSTE " + (estat.getCoste() - ((Estat) search.getGoalState()).getCoste())+"\n");
            
            System.out.println("Tiempo de ejecucion: " + (System.currentTimeMillis()-TInicio) +"ms" );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   private static void SimulatedAnnealingSearch(Estat estat) {
        try {
        	long TInicio = System.currentTimeMillis();
            Problem problem =  new Problem(estat, new RedSuccessorFunction1(), new RedGoalTest(), new RedHeuristicFunction1());
            Search search = new SimulatedAnnealingSearch(8000, 80, 4, 0.001);
            SearchAgent agent = new SearchAgent(problem, search);

            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());
            System.out.println(((Estat) search.getGoalState()).toString());
            System.out.println("\n" + ((Estat) search.getGoalState()).connexionesToString());
            System.out.println("COSTE FINAL " + ((Estat) search.getGoalState()).getCoste()+"\n");
            System.out.println("MEJORA DE COSTE " + (estat.getCoste() - ((Estat) search.getGoalState()).getCoste())+"\n");
            
            System.out.println("Tiempo de ejecucion: " + (System.currentTimeMillis()-TInicio) +"ms" );

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
    	System.out.println("Printing acctions:");
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
