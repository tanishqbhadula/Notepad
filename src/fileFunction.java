import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class fileFunction {
    GUI gui;
    String fileName;
    String fileAddr;
    String openAddr;

    public fileFunction(GUI gui){
        this.gui = gui;
    }
    // FILE -> NEW
    public void newFile() {
        gui.textArea.setText("");
        gui.window.setTitle("untitled");
        fileAddr = null; //to initialize name and addr as null so that when we save a new file, the program
        fileName = null; //try to overwrite but rather goto save as directly to save a new file.
    }
    // FILE -> OPEN
    public void openFile() {
        FileDialog fileDialog = new FileDialog(gui.window, "open", FileDialog.LOAD);
        fileDialog.setVisible(true);
        if(fileDialog.getFile() != null){
            fileName = fileDialog.getFile();
            fileAddr = fileDialog.getDirectory();
            openAddr = fileAddr + fileName; //to create address (of file to open) from directory
            gui.window.setTitle(fileName); //copies name of file
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(openAddr)); //passes address of file to reader
            gui.textArea.setText(""); //clears the screen
            String currLine = null;
            while((currLine = bufferedReader.readLine()) != null) {
                gui.textArea.append(currLine + "\n"); //adds text line by line
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("FILE NOT FOUND");
        }
    }
    public void saveAs(){
        FileDialog fileDialog = new FileDialog(gui.window, "Save",FileDialog.SAVE);
        fileDialog.setVisible(true);

        if(fileDialog.getFile() != null) {
            fileName = fileDialog.getFile();
            fileAddr = fileDialog.getDirectory();
            openAddr = fileAddr + fileName; //generate open address same as open function
            gui.window.setTitle(fileName);
        }
        try {
            FileWriter fileWriter = new FileWriter(openAddr);
            fileWriter.write(gui.textArea.getText()); //copy all contents to new save file
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("FILE NOT FOUND");
        }
    }
    public void save() {
        if (fileName == null) {
            saveAs(); //if its a new file
        } else {
            try { //else overwrite existing file
                FileWriter fileWriter = new FileWriter(openAddr);
                fileWriter.write(gui.textArea.getText());
                gui.window.setTitle(fileName);
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("ERROR in save");
            }
        }
    }
}
