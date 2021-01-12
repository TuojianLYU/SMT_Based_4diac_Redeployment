/*
Author: Tuojian
This is the correct version z3 configuration generator but 
what is missing is the functionality to receive the variables 
from SysFileParser compoenent which is now implemented now.
*/
package z3solver.z3ConfigurationGenerator;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Optimize;
import z3solver.model.MissionMeta;

import java.util.ArrayList;

public class Z3Generator {
    public MissionMeta missionMeta = new MissionMeta();
    String numOfContainers;
    int numOfFBs;
    int numMaxFBs;
    IntExpr[][] intensity = new IntExpr[numOfFBs][];
    ArrayList<IntExpr> xlist = new ArrayList<>();


    public void initialization(MissionMeta missionMeta) {

        this.missionMeta = missionMeta;
        IntExpr[][] intensity = new IntExpr[missionMeta.getNumOfFBs()][missionMeta.getNumOfFBs()];
        int[][] intensityTemp = missionMeta.getIntensity();

        for (int i = 0; i < intensityTemp.length; i++) {
            for (int j = 0; j < intensityTemp.length; j++) {
                intensity[i][j] = missionMeta.getCtx().mkInt(intensityTemp[i][j]);
            }
        }

        this.numOfContainers = Integer.toString(missionMeta.getNumOfContainers());
        this.numMaxFBs = missionMeta.getNumMaxFBs();
        this.intensity = intensity;
        numOfFBs = missionMeta.getNumOfFBs();

        // this loop is for defineing the x1, x2 ... IntExpr type variables
        for (int i = 0; i < numOfFBs; i++) {
            IntExpr xExp = missionMeta.getCtx().mkIntConst("x" + (i + 1));
            xlist.add(xExp);
        }

    }

    public Optimize generating() {

        ConstraintsConstructor constraintsConstructor = new ConstraintsConstructor(missionMeta);
        InitializationConstructor initializationConstructor = new InitializationConstructor();
        MinimizeSumConstructor minimizeSumConstructor = new MinimizeSumConstructor();
        HWSkillConstructor hwSkillConstructor = new HWSkillConstructor(missionMeta);

        String initialization;
        String constraints;
        String assertExpr = "(assert (and expr))";
        StringBuilder z3Expr = new StringBuilder();
        String z3ExprTemplate = "(declare-const expr Int)";
        String rangeLow = "1";
        BoolExpr be1;
        IntExpr sumExp = missionMeta.getCtx().mkIntConst("MinSumOfIntensity");

        // generating the (declare-const x Int) and (>= x 1)(<= x 3) and (= x1 x2)(=
        // x1 x3)
        // and the final assert expr
        numMaxFBs += 1; // the real number is the number of taken balls not the maximum number, so it
        // should plus 1




        initialization = initializationConstructor.initializationConstructing(numOfFBs, rangeLow, numOfContainers);
        constraints = constraintsConstructor.getConstraints();
        assertExpr = assertExpr.replace("expr", initialization + constraints);

        // combine all the generated constraints into final z3 configuration z3Expr
        for (int i = 0; i < numOfFBs; i++) {
            z3Expr.append(z3ExprTemplate.replace("expr", "x" + (i + 1)));
        }

        // combine all the constraints and objectives together as z3 format
        z3Expr.append(assertExpr);
        BoolExpr f = missionMeta.getCtx().parseSMTLIB2String(z3Expr.toString(), null, null, null, null)[0];

        BoolExpr be = minimizeSumConstructor.minimizeSumConstructing(missionMeta.getCtx(), xlist, numOfFBs, intensity, sumExp);
        be = missionMeta.getCtx().mkAnd(f, be);
        be1 = hwSkillConstructor.skillConstructing(missionMeta.getCtx(), numOfFBs);
        be = missionMeta.getCtx().mkAnd(be, be1);
//		System.out.println(be);

        // optimize the z3 question
        Optimize opt = missionMeta.getCtx().mkOptimize();
        opt.Add(be);
        opt.MkMinimize(sumExp);

        return opt;
    }

    public static class TestFailedException extends Exception {
        public TestFailedException() {
            super("Check FAILED");
        }
    }

}