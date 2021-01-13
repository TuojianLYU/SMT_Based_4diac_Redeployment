package z3solver.main;

import com.microsoft.z3.Optimize;
import z3solver.z3ConfigurationGenerator.Z3Generator;
import z3solver.z3Coordinator.Z3Coordinator;

public class Main {
    public static void main(String[] args) throws Z3Generator.TestFailedException {

        long startTime = System.nanoTime();
        // This definition block is to define all the required variables for z3
        // generator
        // The swSkill and hwSkill are now manually defined
        int numMaxFBs = 5;
        int numOfContainers = 5;
        String filePath = "xml/QX20.xml";

        Z3Coordinator z3Coordinator = new Z3Coordinator(filePath, numMaxFBs, numOfContainers);
        z3Coordinator.initialization();
        Optimize result = z3Coordinator.launchMission();

        System.out.println(result.Check());
        System.out.println(result.getModel());

        System.out.println("Execution time = " + ((System.nanoTime() - startTime) / 1000000));

    }
}
