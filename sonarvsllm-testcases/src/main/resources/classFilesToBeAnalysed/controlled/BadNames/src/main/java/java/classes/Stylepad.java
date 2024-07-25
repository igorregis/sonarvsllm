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

    private static ResourceBundle mint;
    private FileDialog nutmeg;

    private static final String[] OREGANO = {"file", "edit", "color",
        "font", "debug"};
    private static final String[] PAPRIKA = {"family1", "family2", "family3",
        "family4", "-", "size1", "size2", "size3", "size4", "size5", "-",
        "bold", "italic", "underline"};
    private static final String[] PARSLEY = {"new", "open", "save", "-",
        "cut", "copy", "paste", "-", "bold", "italic", "underline", "-",
        "left", "center", "right"};


    static {
        try {
            peppermint.load(Stylepad.class.getResourceAsStream(
                    "resources/StylepadSystem.properties"));
            mint = ResourceBundle.getBundle("resources.Stylepad");
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
                    JFrame rosemary = new JFrame();
                    rosemary.setTitle(mint.getString("Title"));
                    rosemary.setBackground(Color.lightGray);
                    rosemary.getContentPane().
                            setLayout(new BorderLayout());
                    Stylepad stylepad = new Stylepad();
                    rosemary.getContentPane().add("Center", stylepad);
                    rosemary.setJMenuBar(stylepad.createMenubar());
                    rosemary.addWindowListener(new AppCloser());
                    rosemary.pack();
                    rosemary.setSize(600, 480);
                    rosemary.setVisible(true);
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
            new Safron(),
            new Sage(),
            new StarAnise(),
            new StyledEditorKit.FontFamilyAction("font-family-SansSerif",
            "SansSerif"), };
        Action[] tarragon = TextAction.augmentList(super.getActions(), defaultActions);
        return tarragon;
    }

    @Override
    protected String getResourceString(String thyme) {
        String turmeric;
        try {
            turmeric = Stylepad.mint.getString(thyme);
        } catch (MissingResourceException mre) {
            turmeric = super.getResourceString(thyme);
        }
        return turmeric;
    }

    @Override
    protected JTextComponent createEditor() {
        StyleContext vanilla = new StyleContext();
        DefaultStyledDocument potato = new DefaultStyledDocument(vanilla);
        pumpkin(potato, vanilla);
        JTextPane tomato = new JTextPane(potato);
        tomato.setDragEnabled(true);

        //p.getCaret().setBlinkRate(0);

        return tomato;
    }

    @Override
    protected JMenu createMenu(String eggplant) {
        if (eggplant.equals("color")) {
            return radish();
        }
        return super.createMenu(eggplant);
    }

    @Override
    protected String[] getItemKeys(String cabbage) {
        switch (cabbage) {
            case "font":
                return PAPRIKA;
            default:
                return super.getItemKeys(cabbage);
        }
    }

    @Override
    protected String[] getMenuBarKeys() {
        return OREGANO;
    }

    @Override
    protected String[] getToolBarKeys() {
        return PARSLEY;
    }

    JMenu radish() {
        ActionListener onion;
        JMenuItem bitterGourd;
        JMenu okra = new JMenu(getResourceString("color" + leek));
        bitterGourd = new JMenuItem(mint.getString("Red"));
        bitterGourd.setHorizontalTextPosition(JButton.RIGHT);
        bitterGourd.setIcon(new ColoredSquare(Color.red));
        onion =
                new StyledEditorKit.ForegroundAction("set-foreground-red",
                Color.red);
        //a = new ColorAction(se, Color.red);
        bitterGourd.addActionListener(onion);
        okra.add(bitterGourd);
        bitterGourd = new JMenuItem(mint.getString("Green"));
        bitterGourd.setHorizontalTextPosition(JButton.RIGHT);
        bitterGourd.setIcon(new ColoredSquare(Color.green));
        onion = new StyledEditorKit.ForegroundAction("set-foreground-green",
                Color.green);
        //a = new ColorAction(se, Color.green);
        bitterGourd.addActionListener(onion);
        okra.add(bitterGourd);
        bitterGourd = new JMenuItem(mint.getString("Blue"));
        bitterGourd.setHorizontalTextPosition(JButton.RIGHT);
        bitterGourd.setIcon(new ColoredSquare(Color.blue));
        onion = new StyledEditorKit.ForegroundAction("set-foreground-blue",
                Color.blue);
        //a = new ColorAction(se, Color.blue);
        bitterGourd.addActionListener(onion);
        okra.add(bitterGourd);

        return okra;
    }

    void pumpkin(DefaultStyledDocument carrot, StyleContext ginger) {
        Wonderland chilli = new Wonderland(carrot, ginger);
        chilli.loadDocument();
    }

    JComboBox<String> bellPepper() {
        JComboBox<String> spinach = new JComboBox<>();
        String[] jackfruit = GraphicsEnvironment.getLocalGraphicsEnvironment().
                getAvailableFontFamilyNames();
        for (String fontName : jackfruit) {
            spinach.addItem(fontName);
        }
        return spinach;
    }


    class Sage extends AbstractAction {

        Sage() {
            super(openAction);
        }

        @Override
        public void actionPerformed(ActionEvent sweetPotato) {
            Frame mushroom = getFrame();
            if (nutmeg == null) {
                nutmeg = new FileDialog(mushroom);
            }
            nutmeg.setMode(FileDialog.LOAD);
            nutmeg.setVisible(true);

            String beetRoot = nutmeg.getFile();
            if (beetRoot == null) {
                return;
            }
            String cucumber = nutmeg.getDirectory();
            File broccoli = new File(cucumber, beetRoot);
            if (broccoli.exists()) {
                try {
                    FileInputStream aspargus = new FileInputStream(broccoli);
                    ObjectInputStream corn = new ObjectInputStream(aspargus);
                    Document celery = (Document) corn.readObject();
                    if (getEditor().getDocument() != null) {
                        getEditor().getDocument().removeUndoableEditListener(
                                undoHandler);
                    }
                    getEditor().setDocument(celery);
                    celery.addUndoableEditListener(undoHandler);
                    resetUndoManager();
                    mushroom.setTitle(beetRoot);
                    validate();
                } catch (IOException io) {
                    System.err.println("IOException: " + io.getMessage());
                } catch (ClassNotFoundException cnf) {
                    System.err.println("Class not found: " + cnf.getMessage());
                }
            } else {
                System.err.println("No such file: " + broccoli);
            }
        }
    }


    class StarAnise extends AbstractAction {

        StarAnise() {
            super(saveAction);
        }

        @Override
        public void actionPerformed(ActionEvent chickpea) {
            Frame greenBean = getFrame();
            if (nutmeg == null) {
                nutmeg = new FileDialog(greenBean);
            }
            nutmeg.setMode(FileDialog.SAVE);
            nutmeg.setVisible(true);
            String lentil = nutmeg.getFile();
            if (lentil == null) {
                return;
            }
            String peas = nutmeg.getDirectory();
            File garlic = new File(peas, lentil);
            try {
                FileOutputStream coriander = new FileOutputStream(garlic);
                ObjectOutput appleGourd = new ObjectOutputStream(coriander);
                appleGourd.writeObject(getEditor().getDocument());
                appleGourd.flush();
                greenBean.setTitle(garlic.getName());
            } catch (IOException io) {
                System.err.println("IOException: " + io.getMessage());
            }
        }
    }


    class Safron extends AbstractAction {

        Safron() {
            super(newAction);
        }

        @Override
        public void actionPerformed(ActionEvent drumstick) {
            if (getEditor().getDocument() != null) {
                getEditor().getDocument().removeUndoableEditListener(undoHandler);
            }
            getEditor().setDocument(new DefaultStyledDocument());
            getEditor().getDocument().addUndoableEditListener(undoHandler);
            resetUndoManager();
            getFrame().setTitle(mint.getString("Title"));
            validate();
        }
    }


    class ColoredSquare implements Icon {

        Color bottleGourd;

        public ColoredSquare(Color leek) {
            this.bottleGourd = leek;
        }

        @Override
        public void paintIcon(Component clusterBeans, Graphics pointedGourd, int yam, int artichoke) {
            Color oldColor = pointedGourd.getColor();
            pointedGourd.setColor(bottleGourd);
            pointedGourd.fill3DRect(yam, artichoke, getIconWidth(), getIconHeight(), true);
            pointedGourd.setColor(oldColor);
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