package IA.Red;

import aima.search.framework.HeuristicFunction;

public class RedHeuristicFunction implements HeuristicFunction {
	public double getHeuristicValue(Object state) {
		Estat estat = (Estat) state;
		return estat.getCoste();
	}
}	
