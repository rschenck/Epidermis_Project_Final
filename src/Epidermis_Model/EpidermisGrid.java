package Epidermis_Model;

import AgentFramework.GenomeTracker;
import AgentFramework.Grid3unstackable;
import AgentFramework.GridDiff3;
import AgentFramework.Gui.Gui;
import AgentFramework.Gui.GuiVis;
import static AgentFramework.Utils.*;
import static Epidermis_Model.EpidermisConst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by schencro on 3/31/17.
 */


// Grid specific parameters
class EpidermisGrid extends Grid3unstackable<EpidermisCell> {
    final Random RN=new Random();
    static final int[] divHoodBasal={1,0,0, -1,0,0, 0,0,1, 0,0,-1, 0,1,0}; // Coordinate set for four beside and one above [x,y,z,x,y,z...]
    static final int[] divHood={1,0,-1,0,0,1,0,-1}; // Coordinate set for two beside and one above and one below [x,y,x,y...]
    static final int[] moveHood={1,0,0, -1,0,0, 0,0,1, 0,0,-1, 0,-1,0};
    static final int[] inBounds= new int[6];
    static final double EGF_DIFFUSION_RATE=0.08; //keratinocyte growth factor
    static final double DECAY_RATE=0.001; //chemical decay rate of growth factors
    static final double SOURCE_EGF=1; //constant level at basement
    static final int AIR_HEIGHT=15; //air, keratinocyte death! (threshold level for placement of keratinocytes essentially)
    static final int CHEMICAL_STEPS=100; // number of times diffusion is looped every tick
    boolean running;
    float r_lambda_weekly = 0;
    int xDim;
    int yDim;
    int zDim;
    long popSum=0;
    int[] MeanProlif = new int[EpidermisConst.xSize * EpidermisConst.ySize * EpidermisConst.zSize];
    int[] MeanDeath = new int[EpidermisConst.xSize * EpidermisConst.ySize * EpidermisConst.zSize];
    GenomeTracker<EpidermisCellGenome> GenomeStore;
    GridDiff3 EGF;

    public EpidermisGrid(int x, int y, int z) {
        super(x,y,z,EpidermisCell.class);
        running = false;
        xDim = x;
        yDim = y;
        zDim = z;
        EGF = new GridDiff3(x, y, z);
        GenomeStore = new GenomeTracker<>(new EpidermisCellGenome(1,1,1,""), true, true);
        PlaceCells();
    }

    public void PlaceCells() {
        for (int x = 0; x < xDim; x++) {
            for (int z = 0; z < xDim; z++) {
                for (int y = 0; y < AIR_HEIGHT; y++) {
                    if (GetAgent(x, y, z) == null) {
                        EpidermisCell c = NewAgent(x, y, z);
                        c.init(KERATINOCYTE, GenomeStore.NewProgenitor()); // Initializes cell types; Uniform Start
                    }
                }
            }
        }
    }


    public void RunStep() {
        for (int i = 0; i < CHEMICAL_STEPS; i++) {
            ChemicalLoop();
        }
        for (EpidermisCell c: this) {
            c.CellStep();
            MeanProlif(c);
//            MeanDeath();
        }
        popSum+=Pop();
        CleanShuffInc(RN); // Special Sauce
    }

    public void DrawChemicals(GuiVis chemVis, boolean egf, boolean bfgf) {
        for (int x = 0; x < xDim; x++) {
            for (int z = 0; z < xDim; z++) {
                for (int y = 0; y < yDim; y++) {
                    if (egf) {
                        chemVis.SetColorHeat(x, y, EGF.SQgetCurr(x, y, z) / SOURCE_EGF, "rgb");
                    }
                }
            }
        }
    }

    public void ActivityHeatMap(GuiVis heatVis, EpidermisGrid Epidermis, EpidermisCellVis CellDraw, int[] MeanLife, String heatColor) {
        for(int i=0; i<MeanLife.length; i++){
            if(MeanLife[i]!=0) {
                heatVis.SetColorHeat(ItoX(i), ItoY(i), MeanLife[i] / (float)EpidermisConst.VisUpdate, heatColor);
            } else {
                heatVis.SetColor(ItoX(i),ItoY(i), 0.0f, 0.0f, 0.0f);
            }
        }
    }

    public void LayerVis(GuiVis heatVis, EpidermisGrid Epidermis, EpidermisCellVis CellDraw, int[] MeanLife, String heatColor) {
        int[] MeanLayer = new int[EpidermisConst.ySize];
        for(int i=0; i<MeanLife.length; i++){
            int y = ItoY(i);
            MeanLayer[y] += MeanLife[i];
            MeanLife[i] = 0;
        }
        for(int y = 0; y<MeanLayer.length; y++) {
            float LayerAvg = MeanLayer[y] / (EpidermisConst.xSize * (float) EpidermisConst.VisUpdate);
                if(LayerAvg!=0) {
                    for (int x = 0; x < EpidermisConst.xSize; x++) {
                        heatVis.SetColorHeat(x, y, LayerAvg, heatColor);
                    }
                } else {
                    for (int x = 0; x < EpidermisConst.xSize; x++) {
                        heatVis.SetColor(x, y, 0.0f, 0.0f, 0.0f);
                    }
                }
        }
    }

    public void MeanProlif(EpidermisCell c){
            if(c.Action == DIVIDE){
                MeanProlif[c.Isq()] += 1;
            }
    }

    public float GetOldestCell(EpidermisGrid Epidermis){
        float Age = 0;
        int aliveCells = 0;
        for (EpidermisCell c: this) {
            if(c!=null){
                Age += c.Age();
                aliveCells += 1;
            }
        }
        return Age/aliveCells;
    }


    public void DrawCellActivity(GuiVis vis, EpidermisGrid Epidermis, EpidermisCellVis CellDraw) {
        long time = System.currentTimeMillis();
        for (int x = 0; x < xDim; x++) {
            for (int z = 0; z < xDim; z++) {
                for (int y = 0; y < yDim; y++) {
                    EpidermisCell c = Epidermis.GetAgent(x, y, z);
                    if (c != null) {
                        CellDraw.DrawCellonGrid(vis, c);
                    } else {
                        CellDraw.DrawEmptyCell(vis, x, y);
                    }
                }
            }
        }
    }

    public void DrawCellPops(GuiVis vis, EpidermisGrid Epidermis, EpidermisCellVis CellDraw){
        long time = System.currentTimeMillis();
        for (int x = 0; x < xDim; x++) {
            for (int y = 0; y < yDim; y++) {
                EpidermisCell c = Epidermis.GetAgent(x, y, zDim/2);
                if (c != null) {
                        CellDraw.DrawCellonGridPop(vis, c);
                } else {
                    CellDraw.DrawEmptyCell(vis, x, y);
                }
            }
        }
    }

    // Inflicting a wound to simulate wound repair...
    public void inflict_wound(){
        for (int x = 37; x < 37*3; x++){
            for (int z = 37; z < 37 *3; z++){
                for (int y=0; y < yDim; y++) {
                    EpidermisCell c = GetAgent(x, y, z);
                    if (c != null) {
                        c.itDead();
                    }
                }
            }
        }
    }

    public boolean checkWoundHeal(int AvgHeight){
        if(GetAgent(xDim/2, 0, zDim/2)!=null){
            return true;
        } else {
            return false;
        }
    }

    public void ChemicalLoop(){
        //DIFFUSION
        EGF.Diffuse(EGF_DIFFUSION_RATE,false,0,true);
        //CELL CONSUMPTION
        for (EpidermisCell c: this) {
                EGF.SQaddNext(c.Xsq(),c.Ysq(),c.Zsq(), c.KERATINO_EGF_CONSPUMPTION*EGF.SQgetCurr(c.Xsq(), c.Ysq(), c.Zsq()));
        }

        //DECAY RATE
        for(int i=0;i<EGF.length;i++){
            EGF.SQsetNext(ItoX(i),ItoY(i),ItoZ(i), EGF.SQgetNext(ItoX(i), ItoY(i), ItoZ(i))*(1.0-DECAY_RATE));
        }

        //SOURCE ADDITION
        for(int x=0;x<xDim;x++) {
            for(int z=0;z<zDim;z++) {
                EGF.SQsetNext(x, 0, z, SOURCE_EGF);
            }
        }

        //SWAP CURRENT FOR NEXT
        EGF.SwapNextCurr();
    }
}
