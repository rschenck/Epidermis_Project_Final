package Framework.Grids;


import java.util.ArrayList;

import static Framework.Tools.Utils.InDim;
import static Framework.Tools.Utils.ModWrap;

/**
 * extend the AgentSQ2 class if you want agents that exist on a 2D discrete lattice
 * with the possibility of stacking multiple agents on the same grid square
 * @param <T> the extended Grid2 class that the agents will live in
 * Created by rafael on 11/18/16.
 */
public class AgentSQ2<T extends Grid2> extends AgentBaseSpatial<T>{
    private int xSq;
    private int ySq;
    AgentSQ2 nextSq;
    AgentSQ2 prevSq;

    void Setup(double i){
        Setup(i);
    }
    void Setup(double xSq,double ySq){
        Setup((int)xSq,(int)ySq);
    }

    @Override
    void Setup(double x, double y, double z) {
        throw new IllegalStateException("shouldn't be adding 2D agent to 3D grid");
    }

    @Override
    void Setup(int i) {
        xSq=myGrid.ItoX(i);
        ySq=myGrid.ItoY(i);
        iSq=i;
        AddSQ(i);

    }

    @Override
    void Setup(int x, int y) {
        this.xSq=x;
        this.ySq=y;
        iSq=myGrid.I(xSq,ySq);
        AddSQ(iSq);

    }

    @Override
    void Setup(int x, int y, int z) {
        throw new IllegalStateException("shouldn't be adding 2D agent to 3D grid");
    }

    /**
     * Moves the agent to the specified square
     */
    public void MoveSQ(int x, int y){
        //moves agent discretely
        if(!alive){
            throw new RuntimeException("attempting to move dead agent");
        }
        int iNewPos=myGrid.I(x,y);
        RemSQ();
        AddSQ(iNewPos);
        this.xSq=x;
        this.ySq=y;
        iSq=iNewPos;
    }
    public void MoveI(int i){
        if(!alive){
            throw new RuntimeException("attempting to move dead agent");
        }
        int x=G().ItoX(i);
        int y=G().ItoY(i);
        RemSQ();
        AddSQ(i);
        this.xSq=x;
        this.ySq=y;
        iSq=i;
    }
    void AddSQ(int i){
        if(myGrid.grid[i]!=null){
            ((AgentSQ2)myGrid.grid[i]).prevSq=this;
            this.nextSq=(AgentSQ2)myGrid.grid[i];
        }
        myGrid.grid[i]=this;
    }
    void RemSQ(){
        if(myGrid.grid[iSq]==this){
            myGrid.grid[iSq]=this.nextSq;
        }
        if(nextSq!=null){
            nextSq.prevSq=prevSq;
        }
        if(prevSq!=null){
            prevSq.nextSq=nextSq;
        }
        prevSq=null;
        nextSq=null;
    }

    /**
     * Moves the agent to the specified square
     */
    public void MoveSafeSQ(int newX,int newY){
        if(!alive){
            throw new RuntimeException("Attempting to move dead agent");
        }
        if (G().In(newX, newY)) {
            MoveSQ(newX, newY);
            return;
        }
        if (G().wrapX) {
            newX = ModWrap(newX, G().xDim);
        } else if (!InDim(G().xDim, newX)) {
            newX = Xsq();
        }
        if (G().wrapY) {
            newY = ModWrap(newY, G().yDim);
        } else if (!InDim(G().yDim, newY))
            newY = Ysq();
        MoveSQ(newX,newY);
    }
    public void MoveSafeSQ(int newX,int newY,boolean wrapX,boolean wrapY){
        if(!alive){
            throw new RuntimeException("Attempting to move dead agent");
        }
        if (G().In(newX, newY)) {
            MoveSQ(newX, newY);
            return;
        }
        if (wrapX) {
            newX = ModWrap(newX, G().xDim);
        } else if (!InDim(G().xDim, newX)) {
            newX = Xsq();
        }
        if (wrapY) {
            newY = ModWrap(newY, G().yDim);
        } else if (!InDim(G().yDim, newY))
            newY = Ysq();
        MoveSQ(newX,newY);
    }

    /**
     * gets the xDim coordinate of the square that the agent occupies
     */
    public int Xsq(){
        return xSq;
    }

    /**
     * gets the yDim coordinate of the square that the agent occupies
     */
    public int Ysq(){
        return ySq;
    }

    /**
     * gets the xDim coordinate of the agent
     */
    public double Xpt(){
        return xSq+0.5;
    }

    /**
     * gets the yDim coordinate of the agent
     */
    public double Ypt(){
        return ySq+0.5;
    }


    /**
     * deletes the agent
     */
    public void Dispose(){
        //kills agent
        if(!alive){
            throw new RuntimeException("attempting to dispose already dead agent");
        }
        RemSQ();
        myGrid.agents.RemoveAgent(this);
    }

    @Override
    void GetAllOnSquare(ArrayList<AgentBaseSpatial> putHere) {
        AgentSQ2 toList=this;
        while (toList!=null){
            putHere.add(toList);
            toList=toList.nextSq;
        }
    }

    //addCoords
}
