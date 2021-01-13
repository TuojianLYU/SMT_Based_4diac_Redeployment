package z3solver.z3ConfigurationGenerator;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import z3solver.model.MissionMeta;

public class InitializationConstructor {

	MissionMeta missionMeta;
	Context ctx;

	public InitializationConstructor(MissionMeta missionMeta) {
		this.missionMeta = missionMeta;
		ctx = missionMeta.getCtx();
	}

	public BoolExpr initializationConstructingMK() {



		BoolExpr andBool = ctx.mkAnd();

		for (int i = 0; i < missionMeta.getNumOfFBs(); i++) {

			andBool = ctx.mkAnd(andBool, ctx.mkAnd(
					ctx.mkGe(missionMeta.getXlist().get(i), ctx.mkInt(1)
					), ctx.mkLe(missionMeta.getXlist().get(i), ctx.mkInt(missionMeta.getNumMaxFBs()))));
		}

		return andBool;
	}
}
