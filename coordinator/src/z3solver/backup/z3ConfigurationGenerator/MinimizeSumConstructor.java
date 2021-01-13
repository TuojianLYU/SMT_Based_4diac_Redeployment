package z3solver.backup.z3ConfigurationGenerator;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

import java.util.ArrayList;

public class MinimizeSumConstructor {

    public BoolExpr minimizeSumConstructing(Context ctx, ArrayList<IntExpr> xlist, int numOfFBs, IntExpr[][] intensity,
                                            IntExpr sumExp) {

        ArithExpr arithExpr = (IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(xlist.get(0), xlist.get(0)), ctx.mkTrue()),
                ctx.mkInt(0), ctx.mkInt(0));
        BoolExpr boolExpr;

        for (int i = 0; i < numOfFBs - 1; i++) {
            for (int j = i + 1; j < numOfFBs; j++) {
                arithExpr = ctx.mkAdd(arithExpr,
                        (IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(xlist.get(i), xlist.get(j)), ctx.mkTrue()),
                                intensity[i][j], ctx.mkInt(0)));
            }
        }
		boolExpr = ctx.mkAnd(ctx.mkEq(sumExp, arithExpr));

        return boolExpr;
    }
}
