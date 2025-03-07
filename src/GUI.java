import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.border.*;

public class GUI implements ActionListener {
    JFrame window;
    JTextArea textArea;
    JScrollPane scrollPane;
    boolean wordWrapOn = false;
    JMenuBar menuBar;
    JMenu file, edit, color, format;
    JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, fileExit,    //FILE
                editUndo, editRedo,     //EDIT
                formatWordWrap, fontArial, fontCSMS, fontTNS, fontSize8, fontSize10, fontSize12, fontSize16, fontSize24,   //FORMAT
                colorWhite, colorBlack, colorRed;      //COLOR
    JMenu formatFontMenu, formatFontSizeMenu;   //FORMAT SUB MENUS

    fileFunction fileFunc = new fileFunction(this); //implements the file functions class by passing current gui class
    formatFunction formatFunc = new formatFunction(this); //implements the format functions class
    colorFunction colorFunc = new colorFunction(this); //implements the color funcs class
    editFunction editFunc = new editFunction(this); //implements the edit funcs class

    public static void main(String[] args) {
        new GUI();
    }
    // base constructor
    public GUI() {
        createWindow();
        createTextArea();
        createMenuBar();
        formatFunc.currFont = "arial"; //default font set to ARIAL
        formatFunc.createFont(16); //default font size set to 16
        formatFunc.wordWrap(); //default word wrap set to 'ON'
        colorFunc.changeColor("lightMode"); //default background set to WHITE
        window.setVisible(true);
    }
    // create window layout
    public void createWindow() {
        window = new JFrame("Notepad");
        window.setSize(700,550);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // add text area
    public void createTextArea() {
        textArea = new JTextArea();

        //timer ensures if multiple chars are deleted at once then all of them are registered in undo stack
        Timer deleteDelayTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                editFunc.saveState();
            }
        });
        deleteDelayTimer.setRepeats(false); //only run timer once

        //doc listener checks for changes occurring in the text area and updates undo redo stacks accordingly
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent event) { //update when new text entered
                String text = textArea.getText();
                //updates save state word by word ie by checking blank space
                if (text.length()>1) {
                    char last = text.charAt(text.length() - 1);
                    if (Character.isWhitespace(last) || last == '.' || last == ',' || last == '!' || last == '?' || last == ';' ||
                            last == ':') {
                        editFunc.saveState();
                    }
                }
                //System.out.println("add");
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent event) { //update when text is deleted
                deleteDelayTimer.restart();
                //System.out.println("del");
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent event) { //update when text is modified/changed
            }
        });

        // customize this scroll pane by creating a new custom scroll pane class
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createSoftBevelBorder(1, Color.WHITE, Color.WHITE));
        window.add(scrollPane);
    }
    // menu bar
    public void createMenuBar() {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        file = new JMenu("File");
        menuBar.add(file);
        createFileMenu();

        edit = new JMenu("Edit");
        menuBar.add(edit);
        createEditMenu();

        color = new JMenu("Theme");
        menuBar.add(color);
        createColorMenu();

        format = new JMenu("Format");
        menuBar.add(format);
        createFormatMenu();
    }
    //file menu items
    public void createFileMenu() {
        fileNew = new JMenuItem("New");
        fileNew.addActionListener(this);
        fileNew.setActionCommand("new");
        file.add(fileNew);

        fileOpen = new JMenuItem("Open");
        fileOpen.addActionListener(this);
        fileOpen.setActionCommand("open");
        file.add(fileOpen);

        fileSave = new JMenuItem("Save");
        fileSave.addActionListener(this);
        fileSave.setActionCommand("save");
        file.add(fileSave);

        fileSaveAs = new JMenuItem("Save As");
        fileSaveAs.addActionListener(this);
        fileSaveAs.setActionCommand("saveAs");
        file.add(fileSaveAs);

        fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(this);
        fileExit.setActionCommand("exit");
        file.add(fileExit);
    }
    //edit menu items
    public void createEditMenu() {
        editUndo = new JMenuItem("Undo");
        editUndo.addActionListener(this);
        editUndo.setActionCommand("undo");
        edit.add(editUndo);

        editRedo = new JMenuItem("Redo");
        editRedo.addActionListener(this);
        editRedo.setActionCommand("redo");
        edit.add(editRedo);
    }
    //format menu items
    public void createFormatMenu() {
        //word wrap
        formatWordWrap = new JMenuItem("Word Wrap: OFF");
        formatWordWrap.addActionListener(this);
        formatWordWrap.setActionCommand("wordWrap");
        format.add(formatWordWrap);

        //font menu
        formatFontMenu = new JMenu("Font");
        format.add(formatFontMenu);

        fontArial = new JMenuItem("Arial");
        fontArial.addActionListener(this);
        fontArial.setActionCommand("arial");
        formatFontMenu.add(fontArial);

        fontCSMS = new JMenuItem("Comic Sans MS");
        fontCSMS.addActionListener(this);
        fontCSMS.setActionCommand("comicSansMS");
        formatFontMenu.add(fontCSMS);

        fontTNS = new JMenuItem("Times New Roman");
        fontTNS.addActionListener(this);
        fontTNS.setActionCommand("timesNewRoman");
        formatFontMenu.add(fontTNS);

        //font size menu
        formatFontSizeMenu = new JMenu("Font Size");
        format.add(formatFontSizeMenu);

        fontSize8 = new JMenuItem("8");
        fontSize8.addActionListener(this);
        fontSize8.setActionCommand("8");
        formatFontSizeMenu.add(fontSize8);

        fontSize10 = new JMenuItem("10");
        fontSize10.addActionListener(this);
        fontSize10.setActionCommand("10");
        formatFontSizeMenu.add(fontSize10);

        fontSize12 = new JMenuItem("12");
        fontSize12.addActionListener(this);
        fontSize12.setActionCommand("12");
        formatFontSizeMenu.add(fontSize12);

        fontSize16 = new JMenuItem("16");
        fontSize16.addActionListener(this);
        fontSize16.setActionCommand("16");
        formatFontSizeMenu.add(fontSize16);

        fontSize24 = new JMenuItem("24");
        fontSize24.addActionListener(this);
        fontSize24.setActionCommand("24");
        formatFontSizeMenu.add(fontSize24);
    }
    //color menu items
    public void createColorMenu() {
        colorBlack = new JMenuItem("Dark Mode");
        colorBlack.addActionListener(this);
        colorBlack.setActionCommand("darkMode");
        color.add(colorBlack);

        colorWhite = new JMenuItem("Light Mode");
        colorWhite.addActionListener(this);
        colorWhite.setActionCommand("lightMode");
        color.add(colorWhite);

//        colorRed = new JMenuItem("Red Mode");
//        colorRed.addActionListener(this);
//        colorRed.setActionCommand("redMode");
//        color.add(colorRed);
    }

//action listener adds functionality to menu buttons using functions class methods
    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        //System.out.println("action command: " + command);
        switch (command) {
            case "new":
                fileFunc.newFile();
                break;
            case "open":
                fileFunc.openFile();
                break;
            case "saveAs":
                fileFunc.saveAs();
                break;
            case "save":
                fileFunc.save();
                break;
            case "exit":
                System.exit(0);
                break;
            case "wordWrap":
                formatFunc.wordWrap();
                break;
            case "arial":
                formatFunc.setFont("arial");
                break;
            case "comicSansMS":
                formatFunc.setFont("comicSansMS");
                break;
            case "timesNewRoman":
                formatFunc.setFont("timesNewRoman");
                break;
            case "8":
                formatFunc.createFont(8);
                break;
            case "10":
                formatFunc.createFont(10);
                break;
            case "12":
                formatFunc.createFont(12);
                break;
            case "16":
                formatFunc.createFont(16);
                break;
            case "24":
                formatFunc.createFont(24);
                break;
            case "lightMode":
                colorFunc.changeColor("lightMode");
                break;
            case "darkMode":
                colorFunc.changeColor("darkMode");
                break;
            case "redMode":
                colorFunc.changeColor("redMode");
                break;
            case "undo":
                editFunc.doAction("undo");
                break;
            case "redo":
                editFunc.doAction("redo");
                break;
            default:
                System.out.println("ERROR in switch");
                break;
        }
    }
}
