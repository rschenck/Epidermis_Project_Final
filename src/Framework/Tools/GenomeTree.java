package Framework.Tools;

import java.util.Iterator;

/**
 * Created by bravorr on 8/4/17.
 */
class GenomeTree<T extends Genome> implements Iterable<T> {
    int nGenomesEver;
    int nLivingGenomes;
    int nTreeGenomes;
    long totalPop;
    final public boolean removeEmptyLeaves;
    T root;
    T living;

    public GenomeTree(T root, boolean removeEmptyLeaves) {
        this.root = root;
        this.root.Init(this, null, nGenomesEver);
        this.removeEmptyLeaves = removeEmptyLeaves;
    }

    @Override
    public Iterator<T> iterator() {
        return new myIter(living);
    }

    private class myIter implements Iterator<T> {
        T curr;

        myIter(T first) {
            this.curr = first;
        }

        @Override
        public boolean hasNext() {
            return curr.nextLiving != null;
        }

        @Override
        public T next() {
            curr = (T) curr.nextLiving;
            return curr;
        }
    }

    public int GetNumGenomes() {
        return nGenomesEver;
    }

    public int GetNumLivingGenomes() {
        return nLivingGenomes;
    }

    public int GetNumTreeGenomes() {
        return nTreeGenomes;
    }

    public long GetTotalPop() {
        return totalPop;
    }
}
