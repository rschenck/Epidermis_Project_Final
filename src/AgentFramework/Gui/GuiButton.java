package AgentFramework.Gui;

import AgentFramework.Misc.ButtonAction;
import AgentFramework.Misc.GuiComp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * a gui item that when clicked executes the myAction function
 */
public class GuiButton extends JButton implements GuiComp {

    int compX;
    int compY;
    boolean newThread;
    ButtonAction myAction;
    Thread runThread;

    /**
     * @param text text on the button
     * @param compX width on the gui GridBagLayout
     * @param compY height on the gui GridBagLayout
     * @param newThread whether the button action will run in a new thread
     * @param myAction function will execute when the button is clicked
     */
    public GuiButton(String text,int compX,int compY,boolean newThread,ButtonAction myAction){
        super(text);
        this.compX=compX;
        this.compY=compY;
        this.myAction=myAction;
        this.newThread=newThread;
        this.setupAction();
    }

    /**
     * @param text text on the button
     * @param newThread whether the button action will run in a new thread
     * @param myAction function will execute when the button is clicked
     */
    public GuiButton(String text,boolean newThread,ButtonAction myAction){
        super(text);
        this.compX=1;
        this.compY=1;
        this.myAction=myAction;
        this.newThread=newThread;
        this.setupAction();
    }

    void setupAction(){
        if(newThread){
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    runThread=new Thread() {
                        public void run() {
                            myAction.Action(e);
                        }
                    };
                    runThread.start();
                }
            });

        }
        else {
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myAction.Action(e);
                }
            });
        }
    }

    /**
     * ignore
     */
    @Override
    public int compX() {
        return compX;
    }

    /**
     * ignore
     */
    @Override
    public int compY() {
        return compY;
    }

    /**
     * ignore
     */
    @Override
    public void GetComps(ArrayList<Component> putHere, ArrayList<Integer> coordsHere, ArrayList<Integer> compSizesHere) {
        putHere.add(this);
        coordsHere.add(0);
        coordsHere.add(0);
        compSizesHere.add(compX);
        compSizesHere.add(compY);
    }
}
