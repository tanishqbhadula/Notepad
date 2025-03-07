import java.awt.*;

public class formatFunction {
    GUI gui;
    Font arial, comicSansMS, timesNewRoman;
    String currFont;

    public formatFunction(GUI gui){
        this.gui = gui;
    }
    public void wordWrap() {
        if (!gui.wordWrapOn) { //when word wrap off
            gui.wordWrapOn = true;
            gui.textArea.setLineWrap(true);
            gui.textArea.setWrapStyleWord(true); //to prevent line break in between words ie to stop splitting words
            gui.formatWordWrap.setText("Word Wrap: ON");
        } else if (gui.wordWrapOn) {
            gui.wordWrapOn = false;
            gui.textArea.setLineWrap(false);
            gui.textArea.setWrapStyleWord(false);
            gui.formatWordWrap.setText("Word Wrap: OFF");
        }
    }
    public void createFont(int fontSize) {
        arial = new Font("Arial", Font.PLAIN, fontSize);
        comicSansMS = new Font("Comic Sans MS", Font.PLAIN, fontSize);
        timesNewRoman = new Font("Times New Roman", Font.PLAIN, fontSize);
        setFont(currFont);
    }
    public void setFont(String font){
        currFont = font;
        switch (currFont) {
            case "arial":
                gui.textArea.setFont(arial);
                break;
            case "comicSansMS":
                gui.textArea.setFont(comicSansMS);
                break;
            case "timesNewRoman":
                gui.textArea.setFont(timesNewRoman);
                break;
        }
    }
}
