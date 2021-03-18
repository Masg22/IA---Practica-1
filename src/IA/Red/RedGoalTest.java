package IA.Red;

import aima.search.framework.GoalTest;

public class RedGoalTest implements GoalTest {
	public boolean isGoalState(Object state) {
		Estat estat = (Estat) state;
		return estat.isGoalState();
	}
}
