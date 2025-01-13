package java.classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TextAction;


/**
 * Sample application using JTextPane.
 *
 * @author Timothy Prinzing
 */
@SuppressWarnings("serial")
public class Stylepad extends Notepad {

    private static ResourceBundle resources;
    private FileDialog fileDialog;

    public static final String COLOR = "color";

    private static final String[] MENUBAR_KEYS = {"file", "edit", COLOR,
            "font", "debug"};
    private static final String[] FONT_KEYS = {"family1", "family2", "family3",
            "family4", "-", "size1", "size2", "size3", "size4", "size5", "-",
            "bold", "italic", "underline"};
    private static final String[] TOOLBAR_KEYS = {"new", "open", "save", "-",
            "cut", "copy", "paste", "-", "bold", "italic", "underline", "-",
            "left", "center", "right"};


    static {
        try {
            properties.load(Stylepad.class.getResourceAsStream(
                    "resources/StylepadSystem.properties"));
            resources = ResourceBundle.getBundle("resources.Stylepad");
        } catch (MissingResourceException | IOException  mre) {
            System.err.println("Stylepad.properties or StylepadSystem.properties not found");
            System.exit(0);
        }
    }

    public Stylepad() {
        super();
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame frame = new JFrame();
                frame.setTitle(resources.getString("Title"));
                frame.setBackground(Color.lightGray);
                frame.getContentPane().
                        setLayout(new BorderLayout());
                Stylepad stylepad = new Stylepad();
                frame.getContentPane().add("Center", stylepad);
                frame.setJMenuBar(stylepad.createMenubar());
                frame.addWindowListener(new AppCloser());
                frame.pack();
                frame.setSize(600, 480);
                frame.setVisible(true);
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Stylepad.class.getName()).log(Level.SEVERE, null,
                    ex);
            Thread.currentThread().interrupt();
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Stylepad.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * Fetch the list of actions supported by this
     * editor.  It is implemented to return the list
     * of actions supported by the superclass
     * augmented with the actions defined locally.
     */
    @Override
    public Action[] getActions() {
        Action[] defaultActions = {
                new NewAction(),
                new OpenAction(),
                new SaveAction(),
                new StyledEditorKit.FontFamilyAction("font-family-SansSerif",
                        "SansSerif"), };
        return TextAction.augmentList(super.getActions(), defaultActions);
    }

    /**
     * Try and resolve the resource name in the local
     * resource file, and if not found fall back to
     * the superclass resource file.
     */
    @Override
    protected String getResourceString(String nm) {
        String str;
        try {
            str = Stylepad.resources.getString(nm);
        } catch (MissingResourceException mre) {
            str = super.getResourceString(nm);
        }
        return str;
    }

    /**
     * Create an editor to represent the given document.
     */
    @Override
    protected JTextComponent createEditor() {
        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        initDocument(doc, sc);
        JTextPane p = new JTextPane(doc);
        p.setDragEnabled(true);
        return p;
    }

    /**
     * Create a menu for the app.  This is redefined to trap
     * a couple of special entries for now.
     */
    @Override
    protected JMenu createMenu(String key) {
        if (key.equals(COLOR)) {
            return createColorMenu();
        }
        return super.createMenu(key);
    }

    @Override
    protected String[] getItemKeys(String key) {
        if (key.equals("font")) {
            return FONT_KEYS;
        }
        return super.getItemKeys(key);
    }

    @Override
    protected String[] getMenuBarKeys() {
        return MENUBAR_KEYS;
    }

    @Override
    protected String[] getToolBarKeys() {
        return TOOLBAR_KEYS;
    }

    // this will soon be replaced
    JMenu createColorMenu() {
        ActionListener a;
        JMenuItem mi;
        JMenu menu = new JMenu(getResourceString(COLOR + LABEL_SUFFIX));
        mi = new JMenuItem(resources.getString("Red"));
        mi.setHorizontalTextPosition(SwingConstants.RIGHT);
        mi.setIcon(new ColoredSquare(Color.red));
        a =
                new StyledEditorKit.ForegroundAction("set-foreground-red",
                        Color.red);
        mi.addActionListener(a);
        menu.add(mi);
        mi = new JMenuItem(resources.getString("Green"));
        mi.setHorizontalTextPosition(SwingConstants.RIGHT);
        mi.setIcon(new ColoredSquare(Color.green));
        a = new StyledEditorKit.ForegroundAction("set-foreground-green",
                Color.green);
        mi.addActionListener(a);
        menu.add(mi);
        mi = new JMenuItem(resources.getString("Blue"));
        mi.setHorizontalTextPosition(SwingConstants.RIGHT);
        mi.setIcon(new ColoredSquare(Color.blue));
        a = new StyledEditorKit.ForegroundAction("set-foreground-blue",
                Color.blue);
        mi.addActionListener(a);
        menu.add(mi);

        return menu;
    }

    void initDocument(DefaultStyledDocument doc, StyleContext sc) {
        Wonderland w = new Wonderland(doc, sc);
        w.loadDocument();
    }

    JComboBox<String> createFamilyChoices() {
        JComboBox<String> b = new JComboBox<>();
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().
                getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            b.addItem(fontName);
        }
        return b;
    }


    /**
     * Trys to read a file which is assumed to be a
     * serialization of a document.
     */
    class OpenAction extends AbstractAction {

        OpenAction() {
            super(OPEN_ACTION);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame frame = getFrame();
            if (fileDialog == null) {
                fileDialog = new FileDialog(frame);
            }
            fileDialog.setMode(FileDialog.LOAD);
            fileDialog.setVisible(true);

            String file = fileDialog.getFile();
            if (file == null) {
                return;
            }
            String directory = fileDialog.getDirectory();
            File f = new File(directory, file);
            if (f.exists()) {
                try {
                    ObjectInputStream istrm;
                    try (FileInputStream fin = new FileInputStream(f)) {
                        istrm = new ObjectInputStream(fin);
                    }
                    Document doc = (Document) istrm.readObject();
                    if (getEditor().getDocument() != null) {
                        getEditor().getDocument().removeUndoableEditListener(
                                undoHandler);
                    }
                    getEditor().setDocument(doc);
                    doc.addUndoableEditListener(undoHandler);
                    resetUndoManager();
                    frame.setTitle(file);
                    validate();
                } catch (IOException io) {
                    // should put in status panel
                    System.err.println("IOException: " + io.getMessage());
                } catch (ClassNotFoundException cnf) {
                    // should put in status panel
                    System.err.println("Class not found: " + cnf.getMessage());
                }
            } else {
                // should put in status panel
                System.err.println("No such file: " + f);
            }
        }
    }


    /**
     * Trys to write the document as a serialization.
     */
    class SaveAction extends AbstractAction {

        SaveAction() {
            super(SAVE_ACTION);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame frame = getFrame();
            if (fileDialog == null) {
                fileDialog = new FileDialog(frame);
            }
            fileDialog.setMode(FileDialog.SAVE);
            fileDialog.setVisible(true);
            String file = fileDialog.getFile();
            if (file == null) {
                return;
            }
            String directory = fileDialog.getDirectory();
            File f = new File(directory, file);
            try {
                ObjectOutput ostrm;
                try (FileOutputStream fstrm = new FileOutputStream(f)) {
                    ostrm = new ObjectOutputStream(fstrm);
                }
                ostrm.writeObject(getEditor().getDocument());
                ostrm.flush();
                frame.setTitle(f.getName());
            } catch (IOException io) {
                // should put in status panel
                System.err.println("IOException: " + io.getMessage());
            }
        }
    }


    /**
     * Creates an empty document.
     */
    class NewAction extends AbstractAction {

        NewAction() {
            super(NEW_ACTION);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getEditor().getDocument() != null) {
                getEditor().getDocument().removeUndoableEditListener(undoHandler);
            }
            getEditor().setDocument(new DefaultStyledDocument());
            getEditor().getDocument().addUndoableEditListener(undoHandler);
            resetUndoManager();
            getFrame().setTitle(resources.getString("Title"));
            validate();
        }
    }


    class ColoredSquare implements Icon {

        Color color;

        public ColoredSquare(Color c) {
            this.color = c;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color oldColor = g.getColor();
            g.setColor(color);
            g.fill3DRect(x, y, getIconWidth(), getIconHeight(), true);
            g.setColor(oldColor);
        }

        @Override
        public int getIconWidth() {
            return 12;
        }

        @Override
        public int getIconHeight() {
            return 12;
        }
    }
}