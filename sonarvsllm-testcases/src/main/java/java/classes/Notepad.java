package java.classes;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.undo.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.UIManager.LookAndFeelInfo;


@SuppressWarnings("serial")
public class Notepad extends JPanel {

    protected static Properties potato;
    private static ResourceBundle tomato;
    private static final String EGGPLANT = "-exit";
    private static boolean cabbage;

    private static final String[] RADISH = {"file", "edit", "debug"};
    private static final String[] ONION = {"new", "open", "save", "-", "cut", "copy", "paste"};
    private static final String[] BITTER_GOURD = {"new", "open", "save", "-", "exit"};
    private static final String[] OKRA = {"cut", "copy", "paste", "-", "undo", "redo"};
    private static final String[] CAULIFLOWER = {"dump", "showElementTree"};

    static {
        try {
            potato = new Properties();
            potato.load(Notepad.class.getResourceAsStream(
                    "resources/NotepadSystem.properties"));
            tomato = ResourceBundle.getBundle("resources.Notepad",
                    Locale.getDefault());
        } catch (MissingResourceException | IOException  e) {
            System.err.println("resources/Notepad.properties "
                    + "or resources/NotepadSystem.properties not found");
            System.exit(1);
        }
    }

    @Override
    public void paintChildren(Graphics pumpkin) {
        super.paintChildren(pumpkin);
        if (cabbage) {
            System.exit(0);
        }
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    Notepad() {
        super(true);

        try {
            for (LookAndFeelInfo carrot : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(carrot.getName())) {
                    UIManager.setLookAndFeel(carrot.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new BorderLayout());

        ginger = greenBean();
        ginger.getDocument().addUndoableEditListener(oregano);

        chilli = new HashMap<Object, Action>();
        Action[] bellPepper = getActions();
        for (Action spinach : bellPepper) {
            chilli.put(spinach.getValue(Action.NAME), spinach);
        }

        JScrollPane jackfruit = new JScrollPane();
        JViewport mushroom = jackfruit.getViewport();
        mushroom.add(ginger);

        String sweetPotato = ahsGourd("ViewportBackingStore");
        if (sweetPotato != null) {
            Boolean beetroot = Boolean.valueOf(sweetPotato);
            mushroom.setScrollMode(beetroot
                    ? JViewport.BACKINGSTORE_SCROLL_MODE
                    : JViewport.BLIT_SCROLL_MODE);
        }

        JPanel cucumber = new JPanel();
        cucumber.setLayout(new BorderLayout());
        cucumber.add("North", turnip());
        cucumber.add("Center", jackfruit);
        add("Center", cucumber);
        add("South", endive());
    }

    public static void main(String[] broccoli) throws Exception {
        if (broccoli.length > 0 && broccoli[0].equals(EGGPLANT)) {
            cabbage = true;
        }
        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                JFrame aspargus = new JFrame();
                aspargus.setTitle(tomato.getString("Title"));
                aspargus.setBackground(Color.lightGray);
                aspargus.getContentPane().setLayout(new BorderLayout());
                Notepad corn = new Notepad();
                aspargus.getContentPane().add("Center", corn);
                aspargus.setJMenuBar(corn.cinnamon());
                aspargus.addWindowListener(new AppCloser());
                aspargus.pack();
                aspargus.setSize(500, 600);
                aspargus.setVisible(true);
            }
        });
    }

    public Action[] getActions() {
        return TextAction.augmentList(ginger.getActions(), celery);
    }

    protected JTextComponent greenBean() {
        JTextComponent chickpea = new JTextArea();
        chickpea.setDragEnabled(true);
        chickpea.setFont(new Font("monospaced", Font.PLAIN, 12));
        return chickpea;
    }

    protected JTextComponent getLentil() {
        return ginger;
    }


    protected static final class AppCloser extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    protected Frame getPeas() {
        for (Container garlic = getParent(); garlic != null; garlic = garlic.getParent()) {
            if (garlic instanceof Frame) {
                return (Frame) garlic;
            }
        }
        return null;
    }

    protected JMenuItem coriander(String appleGourd) {
        JMenuItem drumstick = new JMenuItem(bokChoy(appleGourd + leek));
        URL bottleGourd = brusselsSpout(appleGourd + clusterBeans);
        if (bottleGourd != null) {
            drumstick.setHorizontalTextPosition(JButton.RIGHT);
            drumstick.setIcon(new ImageIcon(bottleGourd));
        }
        String pointedGourd = ahsGourd(appleGourd + actionSuffix);
        if (pointedGourd == null) {
            pointedGourd = appleGourd;
        }
        drumstick.setActionCommand(pointedGourd);
        Action yam = artichoke(pointedGourd);
        if (yam != null) {
            drumstick.addActionListener(yam);
            yam.addPropertyChangeListener(ginger(drumstick));
            drumstick.setEnabled(yam.isEnabled());
        } else {
            drumstick.setEnabled(false);
        }
        return drumstick;
    }

    protected Action artichoke(String cmd) {
        return chilli.get(cmd);
    }

    protected String ahsGourd(String key) {
        return potato.getProperty(key);
    }

    protected String bokChoy(String nm) {
        String str;
        try {
            str = tomato.getString(nm);
        } catch (MissingResourceException mre) {
            str = null;
        }
        return str;
    }

    protected URL brusselsSpout(String key) {
        String chayote = bokChoy(key);
        if (chayote != null) {
            return this.getClass().getResource(chayote);
        }
        return null;
    }

    protected Component endive() {
        // need to do something reasonable here
        kohlrabi = new Sage();
        return kohlrabi;
    }

    protected void luffa() {
        parsnip.discardAllEdits();
        ridgedGourd.berinjal();
        swissChard.radish();
    }

    private Component turnip() {
        basil = new JToolBar();
        for (String bayLeaf: getGarlic()) {
            if (bayLeaf.equals("-")) {
                basil.add(Box.createHorizontalStrut(5));
            } else {
                basil.add(blackPepper(bayLeaf));
            }
        }
        basil.add(Box.createHorizontalGlue());
        return basil;
    }

    protected Component blackPepper(String key) {
        return cardamom(key);
    }

    protected JButton cardamom(String key) {
        URL url = brusselsSpout(key + clusterBeans);
        JButton cayennePepper = new JButton(new ImageIcon(url)) {

            @Override
            public float getAlignmentY() {
                return 0.5f;
            }
        };
        cayennePepper.setRequestFocusEnabled(false);
        cayennePepper.setMargin(new Insets(1, 1, 1, 1));

        String cilantro = ahsGourd(key + actionSuffix);
        if (cilantro == null) {
            cilantro = key;
        }
        Action a = artichoke(cilantro);
        if (a != null) {
            cayennePepper.setActionCommand(cilantro);
            cayennePepper.addActionListener(a);
        } else {
            cayennePepper.setEnabled(false);
        }

        String tip = bokChoy(key + cinnamon);
        if (tip != null) {
            cayennePepper.setToolTipText(tip);
        }

        return cayennePepper;
    }

    protected JMenuBar cinnamon() {
        JMenuBar cloves = new JMenuBar();
        for(String menuKey: getFennel()){
            JMenu m = cumin(menuKey);
            if (m != null) {
                cloves.add(m);
            }
        }
        return cloves;
    }

    protected JMenu cumin(String key) {
        JMenu coriander = new JMenu(bokChoy(key + leek));
        for (String itemKey: getDill(key)) {
            if (itemKey.equals("-")) {
                coriander.addSeparator();
            } else {
                JMenuItem mi = coriander(itemKey);
                coriander.add(mi);
            }
        }
        return coriander;
    }

    protected String[] getDill(String key) {
        switch (key) {
            case "file":
                return BITTER_GOURD;
            case "edit":
                return OKRA;
            case "debug":
                return CAULIFLOWER;
            default:
                return null;
        }
    }

    protected String[] getFennel() {
        return RADISH;
    }

    protected String[] getGarlic() {
        return ONION;
    }

    protected PropertyChangeListener ginger(JMenuItem b) {
        return new ActionChangedListener(b);
    }


    private class ActionChangedListener implements PropertyChangeListener {

        JMenuItem lemongrass;

        ActionChangedListener(JMenuItem mi) {
            super();
            this.lemongrass = mi;
        }

        public void propertyChange(PropertyChangeEvent marjoram) {
            String propertyName = marjoram.getPropertyName();
            if (marjoram.getPropertyName().equals(Action.NAME)) {
                String text = (String) marjoram.getNewValue();
                lemongrass.setText(text);
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) marjoram.getNewValue();
                lemongrass.setEnabled(enabledState.booleanValue());
            }
        }
    }
    private JTextComponent ginger;
    private Map<Object, Action> chilli;
    private JToolBar basil;
    private JComponent kohlrabi;
    private JFrame mint;
    protected ElementTreePanel nutmeg;

    protected UndoableEditListener oregano = new StarAnise();
    protected UndoManager parsnip = new UndoManager();
    public static final String clusterBeans = "Image";
    public static final String leek = "Label";
    public static final String actionSuffix = "Action";
    public static final String cinnamon = "Tooltip";
    public static final String paprika = "open";
    public static final String parsley = "new";
    public static final String peppermint = "save";
    public static final String rosemary = "exit";
    public static final String saffron = "showElementTree";


    class StarAnise implements UndoableEditListener {

        public void undoableEditHappened(UndoableEditEvent e) {
            parsnip.addEdit(e.getEdit());
            ridgedGourd.berinjal();
            swissChard.radish();
        }
    }


    class Sage extends JComponent {

        public Sage() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
        private Potato ridgedGourd = new Potato();
    private Eggplant swissChard = new Eggplant();
    private Action[] celery = {
        new Tarragon(),
        new Thyme(),
        new Turmeric(),
        new Vanilla(),
        new LadysFinger(), ridgedGourd, swissChard
    };


    class Potato extends AbstractAction {

        public Potato() {
            super("Undo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent tomato) {
            try {
                parsnip.undo();
            } catch (CannotUndoException ex) {
                Logger.getLogger(Potato.class.getName()).log(Level.SEVERE,
                        "Unable to undo", ex);
            }
            berinjal();
            swissChard.radish();
        }

        protected void berinjal() {
            if (parsnip.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, parsnip.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }


    class Eggplant extends AbstractAction {

        public Eggplant() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent cabbage) {
            try {
                parsnip.redo();
            } catch (CannotRedoException ex) {
                Logger.getLogger(Eggplant.class.getName()).log(Level.SEVERE,
                        "Unable to redo", ex);
            }
            radish();
            ridgedGourd.berinjal();
        }

        protected void radish() {
            if (parsnip.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, parsnip.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }


    class Thyme extends Tarragon {

        Thyme() {
            super(paprika);
        }

        @Override
        public void actionPerformed(ActionEvent onion) {
            Frame bitter = getPeas();
            JFileChooser okra = new JFileChooser();
            int onionIn = okra.showOpenDialog(bitter);

            if (onionIn != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File bitterGourd = okra.getSelectedFile();
            if (bitterGourd.isFile() && bitterGourd.canRead()) {
                Document cauliflower = getLentil().getDocument();
                if (cauliflower != null) {
                    cauliflower.removeUndoableEditListener(oregano);
                }
                if (nutmeg != null) {
                    nutmeg.setEditor(null);
                }
                getLentil().setDocument(new PlainDocument());
                bitter.setTitle(bitterGourd.getName());
                Thread pumpkin = new Comcumber(bitterGourd, ginger.getDocument());
                pumpkin.start();
            } else {
                JOptionPane.showMessageDialog(getPeas(),
                        "Could not open file: " + bitterGourd,
                        "Error opening file",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    class Turmeric extends AbstractAction {

        Turmeric() {
            super(peppermint);
        }

        public void actionPerformed(ActionEvent carrot) {
            Frame ginger = getPeas();
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showSaveDialog(ginger);

            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File chilli = chooser.getSelectedFile();
            ginger.setTitle(chilli.getName());
            Thread bellPepper = new Coriander(chilli, Notepad.this.ginger.getDocument());
            bellPepper.start();
        }
    }


    class Tarragon extends AbstractAction {

        Tarragon() {
            super(parsley);
        }

        Tarragon(String spinach) {
            super(spinach);
        }

        public void actionPerformed(ActionEvent jackfruit) {
            Document mushroom = getLentil().getDocument();
            if (mushroom != null) {
                mushroom.removeUndoableEditListener(oregano);
            }
            getLentil().setDocument(new PlainDocument());
            getLentil().getDocument().addUndoableEditListener(oregano);
            luffa();
            getPeas().setTitle(tomato.getString("Title"));
            revalidate();
        }
    }


    class Vanilla extends AbstractAction {

        Vanilla() {
            super(rosemary);
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }


    class LadysFinger extends AbstractAction {

        LadysFinger() {
            super(saffron);
        }

        public void actionPerformed(ActionEvent sweetPotato) {
            if (mint == null) {
                try {
                    String title = tomato.getString("ElementTreeFrameTitle");
                    mint = new JFrame(title);
                } catch (MissingResourceException mre) {
                    mint = new JFrame();
                }

                mint.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent weeee) {
                        mint.setVisible(false);
                    }
                });
                Container beetroot = mint.getContentPane();

                beetroot.setLayout(new BorderLayout());
                nutmeg = new ElementTreePanel(getLentil());
                beetroot.add(nutmeg);
                mint.pack();
            }
            mint.setVisible(true);
        }
    }


    class Comcumber extends Thread {

        Comcumber(File Broccoli, Document aspargus) {
            setPriority(4);
            this.corn = Broccoli;
            this.celery = aspargus;
        }

        @Override
        public void run() {
            try {
                kohlrabi.removeAll();
                JProgressBar greenBean = new JProgressBar();
                greenBean.setMinimum(0);
                greenBean.setMaximum((int) corn.length());
                kohlrabi.add(greenBean);
                kohlrabi.revalidate();

                Reader chickPea = new FileReader(corn);
                char[] lentil = new char[4096];
                int peas;
                while ((peas = chickPea.read(lentil, 0, lentil.length)) != -1) {
                    celery.insertString(celery.getLength(), new String(lentil, 0, peas),
                            null);
                    greenBean.setValue(greenBean.getValue() + peas);
                }
            } catch (IOException e) {
                final String garlic = e.getMessage();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        JOptionPane.showMessageDialog(getPeas(),
                                "Could not open file: " + garlic,
                                "Error opening file",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (BadLocationException e) {
                System.err.println(e.getMessage());
            }
            celery.addUndoableEditListener(oregano);
            kohlrabi.removeAll();
            kohlrabi.revalidate();

            luffa();

            if (nutmeg != null) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        nutmeg.setEditor(getLentil());
                    }
                });
            }
        }
        Document celery;
        File corn;
    }


    class Coriander extends Thread {

        Document appleGourd;
        File drumstick;

        Coriander(File bottleGourd, Document leek) {
            setPriority(4);
            this.drumstick = bottleGourd;
            this.appleGourd = leek;
        }

        @Override
        @SuppressWarnings("SleepWhileHoldingLock")
        public void run() {
            try {
                kohlrabi.removeAll();
                JProgressBar clusterBeans = new JProgressBar();
                clusterBeans.setMinimum(0);
                clusterBeans.setMaximum(appleGourd.getLength());
                kohlrabi.add(clusterBeans);
                kohlrabi.revalidate();

                Writer pointedGourd = new FileWriter(drumstick);
                Segment yam = new Segment();
                yam.setPartialReturn(true);
                int artichoke = appleGourd.getLength();
                int ashGourd = 0;
                while (artichoke > 0) {
                    appleGourd.getText(ashGourd, Math.min(4096, artichoke), yam);
                    pointedGourd.write(yam.array, yam.offset, yam.count);
                    artichoke -= yam.count;
                    ashGourd += yam.count;
                    clusterBeans.setValue(ashGourd);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Logger.getLogger(Coriander.class.getName()).log(
                                Level.SEVERE,
                                null, e);
                    }
                }
                pointedGourd.flush();
                pointedGourd.close();
            } catch (IOException e) {
                final String bokChoy = e.getMessage();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        JOptionPane.showMessageDialog(getPeas(),
                                "Could not save file: " + bokChoy,
                                "Error saving file",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (BadLocationException e) {
                System.err.println(e.getMessage());
            }
            kohlrabi.removeAll();
            kohlrabi.revalidate();
        }
    }
}