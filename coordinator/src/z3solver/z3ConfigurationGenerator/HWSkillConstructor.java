package z3solver.z3ConfigurationGenerator;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import z3solver.model.MissionMeta;

import java.util.ArrayList;

public class HWSkillConstructor {

    MissionMeta missionMeta;

    public HWSkillConstructor(MissionMeta missionMeta) {
        this.missionMeta = missionMeta;
    }

    public BoolExpr skillConstructing(Context ctx) {

        SkillMapGenerator skillMapGenerator = new SkillMapGenerator(missionMeta);
        skillMapGenerator.swMapGenerating();
        skillMapGenerator.hwMapGenerating();

        ArrayList<IntExpr> swSkillXlist = missionMeta.getSwSkillXlist();
        ArrayList<IntExpr> hwSkillXlist = missionMeta.getHwSkillXlist();

        BoolExpr andBool = ctx.mkAnd();
        for (IntExpr intExpr : swSkillXlist) {
            BoolExpr orBool = ctx.mkOr();
            for (IntExpr expr : hwSkillXlist) {
                orBool = ctx.mkOr(orBool, ctx.mkEq(ctx.mkTrue(),
                        ctx.mkITE(ctx.mkEq(intExpr,
                                ctx.mkInt(Integer.parseInt(expr.toString().replaceAll("[^0-9]", "")))), ctx.mkTrue(), ctx.mkFalse())));
            }

            andBool = ctx.mkAnd(andBool, orBool);
        }

        return andBool;
    }
}
