package Framework.Misc;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by rafael on 1/29/17.
 */
public interface GuiComp {
    int compX();
    int compY();
    boolean IsActive();
    void SetActive(boolean isActive);
    void GetComps(ArrayList<Component> putHere, ArrayList<Integer> coordsHere, ArrayList<Integer> compSizesHere);
}
