package AgentFramework;

import AgentFramework.GenomeTracker;

import java.util.Iterator;

/**
 * should be declared myType extends GenomeInfo <myType>
 */
public abstract class GenomeInfo <T extends GenomeInfo> {
    int id;
    int popSize;
    T next;
    T prev;
    public GenomeTracker<T> myTracker;

    /**
     * gets the current number of clones that share this genome
     */
    public int GetClonePop(){
        return popSize;
    }

    /**
     * ignore
     */
    void _Init(GenomeTracker myTracker,int id,T next,T prev){
        this.myTracker=myTracker;
        this.id=id;
        this.next=next;
        this.prev=prev;
        this.popSize=0;
    }

    /**
     * removes clone from GenomeInfo population
     */
    public void DisposeClone(){
        myTracker.DisposeClone((T)this);
    }

    /**
     * adds new clone to GenomeInfo population
     */
    public T NewClone(){
        popSize++;
        return (T)this;
    }

    /**
     * returns a GenomeInfo instance that is either identical to the original or a mutant
     */
    public T PossiblyMutate(){
        T nextGenome= _RunPossibleMutation();
        if(nextGenome==null){
            return (T)this;
        }
        DisposeClone();
        return nextGenome;
    }
    public String FullLineageInfoStr(String delim){
        return myTracker.FullLineageInfoStr(id,delim);
    }

    /**
     * a potential mutation event, return null if the genome did not change, otherwise return a new genome with the change inside
     * do not change the parent genome!!!!!!!!!!!!!!
     */
    public abstract T _RunPossibleMutation();

    /**
     * returns a string with info about the genome to be stored
     */
    public abstract String GenomeInfoStr();
}