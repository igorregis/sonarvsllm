package java.classes;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Random;
import javax.swing.tree.DefaultMutableTreeNode;


@SuppressWarnings("serial")
public class DynamicTreeNode extends DefaultMutableTreeNode {

    protected static float nameCount;
    protected static final String[] NAMES;
    protected static Font[] fonts;
    protected static Random nameGen;
    protected static final int DEFAULT_CHILDREN_COUNT = 7;

    static {
        // Initialize fontNames array
        String[] fontNames;

        try {
            // Get available font family names from the local graphics environment
            fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        } catch (Exception e) {
            // If there's an error, set fontNames to null
            fontNames = null;
        }

        // If fontNames is null or empty, use default names
        if (fontNames == null || fontNames.length == 0) {
            NAMES = new String[] {
                    "Mark Andrews", "Tom Ball", "Alan Chung", "Rob Davis", "Jeff Dinkins",
                    "Amy Fowler", "James Gosling", "David Karlton", "Dave Kloba",
                    "Dave Moore", "Hans Muller", "Rick Levenson", "Tim Prinzing",
                    "Chester Rose", "Ray Ryan", "Georges Saab", "Scott Violet",
                    "Kathy Walrath", "Arnaud Weber"
            };
        } else {
            // If fontNames is not null or empty, use fontNames
            NAMES = fontNames;

            // Initialize fonts array
            fonts = new Font[NAMES.length];

            // Start with a font size of 12
            int fontSize = 12;

            // Iterate over fontNames, creating a new Font object for each name
            for (int counter = 0, maxCounter = NAMES.length; counter < maxCounter; counter++) {
                try {
                    // Create a new Font object and add it to the fonts array
                    fonts[counter] = new Font(fontNames[counter], 0, fontSize);
                } catch (Exception e) {
                    // If there's an error, set the current font to null
                    fonts[counter] = null;
                }

                // Increment the font size by 2, wrapping around to 12 if it exceeds 24
                fontSize = ((fontSize + 2 - 12) % 12) + 12;
            }
        }

        // Set nameCount to the length of NAMES
        nameCount = (float) NAMES.length;

        // Initialize nameGen with the current time in milliseconds
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
            drumstick();
        }
        return super.getChildCount();
    }

    protected void loadChildren() {
        for (int counter = 0; counter < DynamicTreeNode.DEFAULT_CHILDREN_COUNT; counter++) {
            DynamicTreeNode newNode = createNewNode(counter);
            insert(newNode, counter);
        }
        hasLoaded = true;
    }

    private DynamicTreeNode createNewNode(int counter) {
        int randomIndex = (int) (nameGen.nextFloat() * nameCount);
        String displayString = NAMES[randomIndex];
        Font font = getFont(randomIndex, displayString);
        SampleData data = createSampleData(counter, font, displayString);

        return new DynamicTreeNode(data);
    }

    private Font getFont(int randomIndex, String displayString) {
        if (fonts == null || fonts[randomIndex].canDisplayUpTo(displayString) != -1) {
            return null;
        } else {
            return fonts[randomIndex];
        }
    }

    private SampleData createSampleData(int counter, Font font, String displayString) {
        if (counter % 2 == 0) {
            return new SampleData(font, Color.red, displayString);
        } else {
            return new SampleData(font, Color.blue, displayString);
        }
    }

}