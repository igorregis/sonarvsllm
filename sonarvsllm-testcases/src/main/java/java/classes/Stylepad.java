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
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TextAction;


@SuppressWarnings("serial")
public class Stylepad extends Notepad {

    private static ResourceBundle resources;
    private FileDialog fileDialog;

    private static final String[] MENUBAR_KEYS = {"file", "edit", "color",
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
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
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
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Stylepad.class.getName()).log(Level.SEVERE, null,
                    ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Stylepad.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @Override
    public Action[] getActions() {
        Action[] defaultActions = {
                new NewAction(),
                new OpenAction(),
                new SaveAction(),
                new StyledEditorKit.FontFamilyAction("font-family-SansSerif",
                        "SansSerif"), };
        Action[] a = TextAction.augmentList(super.getActions(), defaultActions);
        return a;
    }

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

    @Override
    protected JTextComponent createEditor() {
        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        initDocument(doc, sc);
        JTextPane p = new JTextPane(doc);
        p.setDragEnabled(true);

        //p.getCaret().setBlinkRate(0);

        return p;
    }

    @Override
    protected JMenu createMenu(String key) {
        if (key.equals("color")) {
            return createColorMenu();
        }
        return super.createMenu(key);
    }

    @Override
    protected String[] getItemKeys(String key) {
        switch (key) {
            case "font":
                return FONT_KEYS;
            default:
                return super.getItemKeys(key);
        }
    }

    @Override
    protected String[] getMenuBarKeys() {
        return MENUBAR_KEYS;
    }

    @Override
    protected String[] getToolBarKeys() {
        return TOOLBAR_KEYS;
    }

    JMenu createColorMenu() {
        ActionListener a;
        JMenuItem mi;
        JMenu menu = new JMenu(getResourceString("color" + labelSuffix));
        mi = new JMenuItem(resources.getString("Red"));
        mi.setHorizontalTextPosition(JButton.RIGHT);
        mi.setIcon(new ColoredSquare(Color.red));
        a =
                new StyledEditorKit.ForegroundAction("set-foreground-red",
                        Color.red);
        //a = new ColorAction(se, Color.red);
        mi.addActionListener(a);
        menu.add(mi);
        mi = new JMenuItem(resources.getString("Green"));
        mi.setHorizontalTextPosition(JButton.RIGHT);
        mi.setIcon(new ColoredSquare(Color.green));
        a = new StyledEditorKit.ForegroundAction("set-foreground-green",
                Color.green);
        //a = new ColorAction(se, Color.green);
        mi.addActionListener(a);
        menu.add(mi);
        mi = new JMenuItem(resources.getString("Blue"));
        mi.setHorizontalTextPosition(JButton.RIGHT);
        mi.setIcon(new ColoredSquare(Color.blue));
        a = new StyledEditorKit.ForegroundAction("set-foreground-blue",
                Color.blue);
        //a = new ColorAction(se, Color.blue);
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


    class OpenAction extends AbstractAction {

        OpenAction() {
            super(openAction);
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
                    FileInputStream fin = new FileInputStream(f);
                    ObjectInputStream istrm = new ObjectInputStream(fin);
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
                    System.err.println("IOException: " + io.getMessage());
                } catch (ClassNotFoundException cnf) {
                    System.err.println("Class not found: " + cnf.getMessage());
                }
            } else {
                // should put in status panel
                System.err.println("No such file: " + f);
            }
        }
    }


    class SaveAction extends AbstractAction {

        SaveAction() {
            super(saveAction);
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
                FileOutputStream fstrm = new FileOutputStream(f);
                ObjectOutput ostrm = new ObjectOutputStream(fstrm);
                ostrm.writeObject(getEditor().getDocument());
                ostrm.flush();
                frame.setTitle(f.getName());
            } catch (IOException io) {
                System.err.println("IOException: " + io.getMessage());
            }
        }
    }

    class NewAction extends AbstractAction {

        NewAction() {
            super(newAction);
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