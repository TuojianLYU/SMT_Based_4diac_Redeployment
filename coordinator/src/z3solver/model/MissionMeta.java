package z3solver.model;

import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

import java.util.ArrayList;

public class MissionMeta {

    private Context ctx;
    private String filePath;
    private int numMaxFBs;
    private int numOfContainers;
    private int numOfFBs;
    int[][] intensity;
    ArrayList<IntExpr> swSkillXlist;
    ArrayList<IntExpr> hwSkillXlist;
    ArrayList<IntExpr> xlist = new ArrayList<>();

    public MissionMeta() {
    }

    public MissionMeta(Context ctx, String filePath, int numMaxFBs, int numOfContainers, int numOfFBs, int[][] intensity) {
        this.ctx = ctx;
        this.filePath = filePath;
        this.numMaxFBs = numMaxFBs;
        this.numOfContainers = numOfContainers;
        this.numOfFBs = numOfFBs;
        this.intensity = intensity;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getNumMaxFBs() {
        return numMaxFBs;
    }

    public void setNumMaxFBs(int numMaxFBs) {
        this.numMaxFBs = numMaxFBs;
    }

    public int getNumOfContainers() {
        return numOfContainers;
    }

    public void setNumOfContainers(int numOfContainers) {
        this.numOfContainers = numOfContainers;
    }

    public int getNumOfFBs() {
        return numOfFBs;
    }

    public void setNumOfFBs(int numOfFBs) {
        this.numOfFBs = numOfFBs;
    }

    public int[][] getIntensity() {
        return intensity;
    }

    public void setIntensity(int[][] intensity) {
        this.intensity = intensity;
    }

    public ArrayList<IntExpr> getSwSkillXlist() {
        return swSkillXlist;
    }

    public void setSwSkillXlist(ArrayList<IntExpr> swSkillXlist) {
        this.swSkillXlist = swSkillXlist;
    }

    public ArrayList<IntExpr> getHwSkillXlist() {
        return hwSkillXlist;
    }

    public void setHwSkillXlist(ArrayList<IntExpr> hwSkillXlist) {
        this.hwSkillXlist = hwSkillXlist;
    }

    public ArrayList<IntExpr> getXlist() {
        return xlist;
    }

    public void setXlist(ArrayList<IntExpr> xlist) {
        this.xlist = xlist;
    }
}
