import java.util.*;

public class editFunction {
    GUI gui;
    static Stack<String> undoStack;
    static Stack<String> redoStack;
    static StringBuffer text; //to load text for undo and redo
    static Scanner commandReader; //to get commands (OPTIONAL MIGHT DELETE LATER)

    public editFunction(GUI gui){
        this.gui = gui;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        text = new StringBuffer();
        commandReader = new Scanner(System.in);
    }

    //update stacks whenever new text entry added
    public void saveState() {
        if (undoStack.isEmpty() || !undoStack.peek().equals(gui.textArea.getText())) {
            undoStack.push(gui.textArea.getText()); //push any new text entry into undo stack
        }
    }
    //implement command on input
    public void doAction(String command) {
        switch (command) {
            case "undo":
                undo();
                break;
            case "redo":
                redo();
                break;
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(gui.textArea.getText()); //save curr text to undo stack
            String lastInput = redoStack.pop();

            gui.textArea.setText(lastInput); //writes the text back on document
        }
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(gui.textArea.getText()); //load curr text onto redo stack
            //if stack only has 1 element then pop and peek give errors so we set text area to blank in that case
            undoStack.pop();
            if (!undoStack.isEmpty()) {
                gui.textArea.setText(undoStack.peek()); //loads prev text on screen (ie undo action)
            } else {
                gui.textArea.setText("");
            }
        }
    }
}
