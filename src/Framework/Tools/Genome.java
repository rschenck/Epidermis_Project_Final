package Framework.Tools;

import Framework.Misc.GenomeFn;
import Framework.Misc.GenomeLineageFn;

import java.util.ArrayList;

/**
 * Created by bravorr on 8/4/17.
 */
public class Genome<T extends Genome> {
    long pop;
    T parent;
    T firstChild;
    T nextSibling;
    T prevSibling;
    T nextLiving;
    T prevLiving;
    int id;
    GenomeTree myTracker;

    public int GetId() {
        return id;
    }

    public long GetPop() {
        return pop;
    }

    public T GetParent() {
        return parent;
    }

    void Init(GenomeTree myTracker, T parent, int id) {
        myTracker.nGenomesEver++;
        myTracker.nLivingGenomes++;
        myTracker.nTreeGenomes++;
        this.pop = 1;
        myTracker.totalPop++;
        if (this.myTracker != null) {
            throw new IllegalStateException("Adding Genome to tree twice!");
        }
        this.myTracker = myTracker;
        this.parent = parent;
        this.id = id;
    }

    public void NewMutantGenome(T child) {
        T new_clone = child;
        child.Init(myTracker, this, myTracker.nGenomesEver);
        T current_right_child = this.firstChild;
        this.firstChild = new_clone;
        if (current_right_child != null) {
            current_right_child.prevSibling = new_clone;
            new_clone.nextSibling = current_right_child;
        }
        child.nextLiving = myTracker.living;
        if (myTracker.living != null) {
            myTracker.living.prevLiving = child;
        }
        myTracker.living = child;
        this.DecPop();
    }

    public void SetPop(long pop) {
        if (myTracker == null) {
            throw new IllegalStateException("Genome must be part of tracker before pop can be changed!");
        }
        if (pop < 0) {
            throw new IllegalStateException("Can't decrease pop below 0!");
        }
        if (this.pop <= 0) {
            throw new IllegalStateException("Can't alter dead Genome!");
        }
        myTracker.totalPop += pop - this.pop;
        this.pop = pop;
        if (pop == 0) {
            KillGenome();
        }
    }

    public void IncPop() {
        SetPop(this.pop + 1);
    }

    public void DecPop() {
        SetPop(this.pop - 1);
    }

    void RemoveGenomeFromTree() {
        myTracker.nTreeGenomes--;
        T my_left_sib = this.nextSibling;
        T my_right_sib = this.prevSibling;
        T parent = this.parent;

        if (this == parent.firstChild) {
            // clone is parent's most recent child
            parent.firstChild = my_left_sib;
            if (my_left_sib == null && parent.pop == 0) {
                //recursively remove dead branches
                parent.RemoveGenomeFromTree();
            }
        }

        if (my_right_sib != null) {
            my_right_sib.nextSibling = my_left_sib;
        }

        if (my_left_sib != null) {
            my_left_sib.prevSibling = my_right_sib;
        }
    }

    void KillGenome() {
        myTracker.nLivingGenomes--;
        if (prevLiving != null) {
            prevLiving.nextLiving = nextLiving;
        }
        if (nextLiving != null) {
            nextLiving.prevLiving = prevLiving;
        }
        if (myTracker.removeEmptyLeaves && firstChild == null) {
            this.RemoveGenomeFromTree();
        }
    }

    public void Traverse(GenomeFn GenomeFunction) {
        GenomeFunction.GenomeFn(this);
        T child = firstChild;
        while (child != null) {
            child.Traverse(GenomeFunction);
            child = (T) child.nextSibling;
        }
    }

    public void TraverseWithLineage(ArrayList<T> lineageStorage, GenomeLineageFn GenomeFunction) {
        lineageStorage.add((T) this);
        GenomeFunction.GenomeLineageFn(lineageStorage);
        T child = firstChild;
        while (child != null) {
            child.TraverseWithLineage(lineageStorage, GenomeFunction);
            lineageStorage.remove(lineageStorage.size() - 1);
            child = (T) child.nextSibling;
        }
    }

    public void GetChildren(ArrayList<Genome> childrenStorage) {
        Genome child = firstChild;
        while (child != null) {
            childrenStorage.add(child);
            child = child.nextSibling;
        }
    }

    public void GetLineage(ArrayList<Genome> lineageStorage) {
        Genome parent = this;
        while (parent != null) {
            lineageStorage.add(parent);
            parent = parent.parent;
        }
    }
}
