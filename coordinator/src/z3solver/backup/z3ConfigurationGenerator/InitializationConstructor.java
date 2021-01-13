package z3solver.backup.z3ConfigurationGenerator;

public class InitializationConstructor {

	public String initializationConstructing(int numOfFBs, String rangeLow, String rangeUp) {

		String initialization = new String();
		String initialTemplate = new String();

		initialTemplate = "(and (>= le1 num1) (<= le1 num2))";

		for (int i = 0; i < numOfFBs; i++) {
			initialization += initialTemplate.replace("le1", (String) ("x" + (i + 1))).replace("num1", rangeLow)
					.replace("num2", rangeUp);
		}

		return initialization;
	}
}
