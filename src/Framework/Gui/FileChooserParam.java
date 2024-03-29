package Framework.Gui;

import Framework.Tools.FileIO;
import Framework.Misc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * a gui item that allows the user to select an existing file, or create a new file
 */
public class FileChooserParam extends JButton implements Framework.Misc.MenuItem,GuiComp {
    JFileChooser browser;
    String labelText;
    JLabel label;
    int compX;
    int compY;
    GuiWindow win;
    public FileChooserParam(ParamSet mySet, String label, String initVal){
        super();
        this.compX=1;
        this.compY=2;
        this.browser=new JFileChooser();
        this.labelText=label;
        this.label=new JLabel(labelText);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret=browser.showSaveDialog(null);
                if(ret==JFileChooser.APPROVE_OPTION){
                    SetFile(browser.getSelectedFile());
                }
            }
        });
        mySet.AddGuiMenuItem(this,initVal);
    }
    public FileChooserParam(ParamSet mySet, String label, int compX, int compY, String initVal){
        super();
        this.compX=compX;
        this.compY=compY;
        this.browser=new JFileChooser();
        this.labelText=label;
        this.label=new JLabel(labelText);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret=browser.showSaveDialog(null);
                if(ret==JFileChooser.APPROVE_OPTION){
                    SetFile(browser.getSelectedFile());
                }
            }
        });
        mySet.AddGuiMenuItem(this,initVal);
    }

    /**
     * ignore
     */
    @Override
    public int TypeID() {
        return 3;
    }

    /**
     * sets the foreground and background of the FileChooserParam
     * @param foregroundColor color of the text if null the GuiWindow color will be used
     * @param backgroundColor color of the background, if null the GuiWindow color will be used
     */
    public FileChooserParam SetColor(Color foregroundColor, Color backgroundColor){
        if(backgroundColor!=null){
            setOpaque(true);
            setBackground(backgroundColor);
            label.setOpaque(true);
            label.setBackground(backgroundColor);
        }
        if(foregroundColor !=null) {
            setForeground(foregroundColor);
            label.setForeground(foregroundColor);
        }
        return this;
    }
    /**
     * ignore
     */
    @Override
    public void Set(String filePath) {
        File chosen=new File(filePath);
        if(!chosen.exists()) {
            FileIO maker=new FileIO(filePath,"w");
            maker.Close();
            chosen=new File(filePath);
        }
        SetFile(chosen);
    }
    /**
     * sets the selected file
     */
    public void SetFile(File chosen){
        String name=chosen.getName();
        if(name.length()>10) { name = name.substring(0, 10); }
        this.setText(name);
        this.browser.setSelectedFile(chosen);
    }

    /**
     * ignore
     */
    @Override
    public String Get() {
        return this.browser.getSelectedFile().getAbsolutePath();
    }

    /**
     * ignore
     */
    @Override
    public String GetLabel() {
        return labelText;
    }

    /**
     * ignore
     */
    @Override
    public int NEntries() {
        return 2;
    }

    /**
     * ignore
     */
    @Override
    public <T extends Component> T GetEntry(int iEntry) {
        switch(iEntry){
            case 0: return (T)label;
            case 1: return (T)this;
            default: throw new IllegalArgumentException(iEntry+" does not match to an item!");
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

    @Override
    public boolean IsActive() {
        return true;
    }

    @Override
    public void SetActive(boolean isActive) {

    }

    /**
     * ignore
     */
    @Override
    public void GetComps(ArrayList<Component> putHere, ArrayList<Integer> coordsHere, ArrayList<Integer> compSizesHere) {
        int labelEnd=compY/2;
        putHere.add(this.label);
        coordsHere.add(0);
        coordsHere.add(0);
        compSizesHere.add(compX);
        compSizesHere.add(labelEnd);
        putHere.add(this);
        coordsHere.add(0);
        coordsHere.add(labelEnd);
        compSizesHere.add(compX);
        compSizesHere.add(compY-labelEnd);
    }
}
