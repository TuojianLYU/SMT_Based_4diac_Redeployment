package z3solver.z3ConfigurationGenerator;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import z3solver.model.MissionMeta;

public class ConstraintsConstructor {

    MissionMeta missionMeta;
    Context ctx;
    BoolExpr andBool;

    public ConstraintsConstructor(MissionMeta missionMeta) {
        this.missionMeta = missionMeta;
        ctx = missionMeta.getCtx();
        andBool = ctx.mkAnd();
    }

    public BoolExpr getConstraints() {

        int[] array = new int[missionMeta.getNumMaxFBs()];

        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        myLoop(array, missionMeta.getNumOfFBs());

        return andBool;

    }

    public void myLoop(int[] array, int n) {

        for (int i = array[array.length - 1]; i <= n; i++) {
            array[array.length - 1] = i;
            constraintsConstructor(array);
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[array.length - i - 2] != n - i - 1) {
                array[array.length - i - 2]++;
                for (int j = array.length - i - 1; j <= array.length - 1; j++) {
                    array[j] = array[j - 1] + 1;
                }
                myLoop(array, n);
            }
        }

    }

    public void constraintsConstructor(int[] array) {

        BoolExpr temBool = ctx.mkAnd();

        for (int i = 0; i < array.length - 1; i++) {
            temBool = ctx.mkAnd(temBool, ctx.mkEq(missionMeta.getXlist().get(array[0] - 1),
                    missionMeta.getXlist().get(array[i + 1] - 1)));
        }
        andBool = ctx.mkAnd(andBool, ctx.mkNot(temBool));

    }
}
