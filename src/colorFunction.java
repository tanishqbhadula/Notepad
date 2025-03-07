import java.awt.*;

public class colorFunction {
    GUI gui;

    public colorFunction(GUI gui) {
        this.gui = gui;
    }

    public void changeColor(String color) {
        switch (color) {
            case "lightMode":
                gui.window.getContentPane().setBackground(Color.white);
                gui.textArea.setBackground(Color.white);
                gui.textArea.setForeground(Color.black);
                break;
            case "darkMode":
                gui.window.getContentPane().setBackground(Color.black);
                gui.textArea.setBackground(Color.black);
                gui.textArea.setForeground(Color.white);
                break;
            case "redMode":
                gui.window.getContentPane().setBackground(Color.red);
                gui.textArea.setBackground(Color.red);
                gui.textArea.setForeground(Color.white);
                break;
            default:
                System.out.println("ERROR in color switch");
        }
    }
}
