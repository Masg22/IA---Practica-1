package IA.Red;

import java.util.List;
import java.util.ArrayList;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class RedSuccessorFunction1 implements SuccessorFunction {

	@Override
	public List getSuccessors(Object actualState) {
		ArrayList<Successor> llistaSucc = new ArrayList<>();
		Estat estat = (Estat) actualState;

		for (int i = 0; i < estat.sensores.size(); ++i) {
			
			for (int j = 0; j < estat.centros.size(); ++j) {
				Estat nou = new Estat(estat);

				// tots els centres posibles
				if (nou.createConnexionS(i + 1, -(j) - 1)) {
					StringBuffer S = new StringBuffer();
					S.append("sensor" + i + "moved to center" + j + "\n");
					llistaSucc.add(new Successor(S.toString(), nou));
				}
			}
			for (int k = 0; k < estat.sensores.size(); ++k) {
				Estat nou = new Estat(estat);
				
				// tots els sensors posibles
				if ( nou.createConnexionS(i + 1, k + 1)) {
					StringBuffer S = new StringBuffer();
					S.append("sensor" + i + "moved to sensor" + k + "\n");
					llistaSucc.add(new Successor(S.toString(), nou));
				}
			}
		}
		
		return llistaSucc;
	}
}
