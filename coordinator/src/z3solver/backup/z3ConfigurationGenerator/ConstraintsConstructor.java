package z3solver.backup.z3ConfigurationGenerator;

import z3solver.model.MissionMeta;

public class ConstraintsConstructor {

	MissionMeta missionMeta;

	public ConstraintsConstructor(MissionMeta missionMeta) {
		this.missionMeta = missionMeta;
	}

	public String getConstraints() {

		String constraints = "";
		String temp = "(and expr)";

		int[] array = new int[missionMeta.getNumMaxFBs()];

		for (int i = 0; i < array.length; i++) {
			array[i] = i + 1;
		}

		constraints = myLoop(array, missionMeta.getNumOfFBs(), constraints);

		constraints = temp.replace("expr", constraints);

		return constraints;

	}

	static String myLoop(int[] array, int n, String constraints) {

		for (int i = array[array.length - 1]; i <= n; i++) {
			array[array.length - 1] = i;
			constraints = constraintsConstructor(array, constraints);
		}

		for (int i = 0; i < array.length - 1; i++) {
			if (array[array.length - i - 2] != n - i - 1) {
				array[array.length - i - 2]++;
				for (int j = array.length - i - 1; j <= array.length - 1; j++) {
					array[j] = array[j - 1] + 1;
				}
				return myLoop(array, n, constraints);
			}
		}

		return constraints;
	}

	static String constraintsConstructor(int[] array, String constraints) {

		String constraintsTemplate1;
		String constraintsTemplate2;
		StringBuilder temp = new StringBuilder();

		constraintsTemplate1 = "(= le1 le2)";
		constraintsTemplate2 = "(not (and expr))";

		constraintsTemplate1 = constraintsTemplate1.replace("le1", "x" + array[0]);

		for (int i = 0; i < array.length - 1; i++) {

			temp.append(constraintsTemplate1.replace("le2", "x" + array[i + 1]));
		}

		constraints += constraintsTemplate2.replace("expr", temp.toString());

		return constraints;
	}
}
