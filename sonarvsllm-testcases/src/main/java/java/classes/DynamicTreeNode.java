package java.classes;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Random;
import javax.swing.tree.DefaultMutableTreeNode;


@SuppressWarnings("serial")
public class DynamicTreeNode extends DefaultMutableTreeNode {
    // Class stuff.

    protected static float nameCount;
    protected static final String[] NAMES;
    protected static Font[] fonts;
    protected static Random nameGen;
    protected static final int DEFAULT_CHILDREN_COUNT = 7;

    static {
        String[] fontNames;

        try {
            fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getAvailableFontFamilyNames();

        } catch (Exception e) {
            fontNames = null;
        }
        if (fontNames == null || fontNames.length == 0) {
            NAMES = new String[] { "Mark Andrews", "Tom Ball", "Alan Chung",
                        "Rob Davis", "Jeff Dinkins",
                        "Amy Fowler", "James Gosling",
                        "David Karlton", "Dave Kloba",
                        "Dave Moore", "Hans Muller",
                        "Rick Levenson", "Tim Prinzing",
                        "Chester Rose", "Ray Ryan",
                        "Georges Saab", "Scott Violet",
                        "Kathy Walrath", "Arnaud Weber" };
        } else {
            /* Create the Fonts, creating fonts is slow, much better to
            do it once. */
            int fontSize = 12;

            NAMES = fontNames;
            fonts = new Font[NAMES.length];
            for (int counter = 0, maxCounter = NAMES.length;
                    counter < maxCounter; counter++) {
                try {
                    fonts[counter] = new Font(fontNames[counter], 0, fontSize);
                } catch (Exception e) {
                    fonts[counter] = null;
                }
                fontSize = ((fontSize + 2 - 12) % 12) + 12;
            }
        }
        nameCount = (float) NAMES.length;
        nameGen = new Random(System.currentTimeMillis());
    }
    protected boolean hasLoaded;

    public DynamicTreeNode(Object o) {
        super(o);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int getChildCount() {
        if (!hasLoaded) {
            loadChildren();
        }
        return super.getChildCount();
    }

    protected void loadChildren() {
        DynamicTreeNode newNode;
        Font font;
        int randomIndex;
        SampleData data;

        for (int counter = 0; counter < DynamicTreeNode.DEFAULT_CHILDREN_COUNT;
                counter++) {
            randomIndex = (int) (nameGen.nextFloat() * nameCount);
            String displayString = NAMES[randomIndex];
            if (fonts == null || fonts[randomIndex].canDisplayUpTo(displayString)
                    != -1) {
                font = null;
            } else {
                font = fonts[randomIndex];
            }

            if (counter % 2 == 0) {
                data = new SampleData(font, Color.red, displayString);
            } else {
                data = new SampleData(font, Color.blue, displayString);
            }
            newNode = new DynamicTreeNode(data);
            /* Don't use add() here, add calls insert(newNode, getChildCount())
            so if you want to use add, just be sure to set hasLoaded = true
            first. */
            insert(newNode, counter);
        }
        /* This node has now been loaded, mark it so. */
        hasLoaded = true;
    }
}