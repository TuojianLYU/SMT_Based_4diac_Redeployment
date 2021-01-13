package z3solver.z3Coordinator;

/*
This is the coordinator to call other classes and methods here
Date of this java doc is 07-01-2021
Author: Tuojian Lyu
*/

import com.microsoft.z3.Context;
import com.microsoft.z3.Optimize;
import org.xml.sax.SAXException;
import z3solver.model.MissionMeta;
import z3solver.model.MissionMetaBuilder;
import z3solver.z3ConfigurationGenerator.Z3Generator;
import z3solver.z3Parser.Z3Parser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class Z3Coordinator {

    public MissionMeta missionMeta = new MissionMeta();
    private String filePath;
    private int numMaxFBs;
    private int numOfContainers;
    private Z3Generator z3Generator = new Z3Generator();
    private Z3Parser z3Parser = new Z3Parser();

    public Z3Coordinator(String filePath, int numMaxFBs, int numOfContainers) {
        this.filePath = filePath;
        this.numMaxFBs = numMaxFBs+1;
        this.numOfContainers = numOfContainers;
    }

    public void initialization() {
        HashMap<String, String> cfg = new HashMap<>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);
        // -------------------
        String file = this.filePath;
        try {
            z3Parser.parsing(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        missionMeta = new MissionMetaBuilder()
                .setFilePath(filePath)
                .setCtx(ctx)
                .setNumMaxFBs(numMaxFBs)
                .setNumOfContainers(numOfContainers)
                .setNumOfFBs(z3Parser.getNumOfFBs())
                .setIntensity(z3Parser.getIntensity()).createMissionMeta();

        z3Generator.initialization(missionMeta);
    }

    public Optimize launchMission() {
        return z3Generator.generating();
    }

}
