package z3solver.z3ConfigurationGenerator;

import com.microsoft.z3.IntExpr;
import z3solver.model.MissionMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("CommentedOutCode")
public class SkillMapGenerator {

    static Map<String, Integer> swSkill = new HashMap<>();
    MissionMeta missionMeta;

    public SkillMapGenerator(MissionMeta missionMeta) {
        this.missionMeta = missionMeta;
    }

    public ArrayList<IntExpr> getSkillXlist(Map<String, Integer> skillMap) {

        ArrayList<IntExpr> skillXlist = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : skillMap.entrySet()) {
            if (entry.getValue().equals(1)) {
                IntExpr xExp = missionMeta.getCtx().mkIntConst(entry.getKey());
                skillXlist.add(xExp);
            }
        }
        return skillXlist;
    }

    public void swMapGenerating() {


        for (int i = 0; i < missionMeta.getNumOfFBs(); i++) {
            swSkill.put("x" + (i + 1), 0);
        }

//        swSkill.put("x1", 1);
        swSkill.put("x2", 1);
        swSkill.put("x7", 1);
        swSkill.put("x8", 1);
        swSkill.put("x9", 1);
		swSkill.put("x10", 1);
		swSkill.put("x11", 1);
		swSkill.put("x12", 1);
		swSkill.put("x13", 1);
		swSkill.put("x14", 1);
		swSkill.put("x15", 1);
		swSkill.put("x16", 1);
		swSkill.put("x17", 1);
		swSkill.put("x18", 1);
//		swSkill.put("x19", 1);
//		swSkill.put("x20", 1);

        missionMeta.setSwSkillXlist(getSkillXlist(swSkill));

    }

    public void hwMapGenerating() {
        Map<String, Integer> hwSkill = new HashMap<>();

        for (int i = 0; i < missionMeta.getNumOfFBs(); i++) {
            hwSkill.put(Integer.toString(i), 0);
        }
        hwSkill.put(Integer.toString(1), 1);
        hwSkill.put(Integer.toString(2), 1);
        hwSkill.put(Integer.toString(3), 1);

        missionMeta.setHwSkillXlist(getSkillXlist(hwSkill));

    }
}
