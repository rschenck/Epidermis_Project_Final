package Framework.Misc;

import Framework.Tools.Genome;

import java.util.ArrayList;

@FunctionalInterface
public interface GenomeLineageFn<T extends Genome>{
    void GenomeLineageFn(ArrayList<T> lineage);
}

