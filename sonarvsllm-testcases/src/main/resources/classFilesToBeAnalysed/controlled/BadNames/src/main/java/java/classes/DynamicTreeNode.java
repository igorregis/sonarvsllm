package java.classes;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Random;


/**
 * DynamicTreeNode illustrates one of the possible ways in which dynamic
 * loading can be used in tree.  The basic premise behind this is that
 * getChildCount() will be messaged from JTreeModel before any children
 * are asked for.  So, the first time getChildCount() is issued the
 * children are loaded.<p>
 * It should be noted that isLeaf will also be messaged from the model.
 * The default behavior of TreeNode is to message getChildCount to
 * determine this. As such, isLeaf is subclassed to always return false.<p>
 * There are others ways this could be accomplished as well.  Instead of
 * subclassing TreeNode you could subclass JTreeModel and do the same
 * thing in getChildCount().  Or, if you aren't using TreeNode you could
 * write your own TreeModel implementation.
 * Another solution would be to listen for TreeNodeExpansion events and
 * the first time a node has been expanded post the appropriate insertion
 * events.  I would not recommend this approach though, the other two
 * are much simpler and cleaner (and are faster from the perspective of
 * how tree deals with it).
 *
 * NOTE: getAllowsChildren() can be messaged before getChildCount().
 *       For this example the nodes always allow children, so it isn't
 *       a problem, but if you do support true leaf nodes you may want
 *       to check for loading in getAllowsChildren too.
 *
 * @author Scott Violet
 */
@SuppressWarnings("serial")
public class DynamicTreeNode extends DefaultMutableTreeNode {
    // Class stuff.

    /** Number of names. */
    protected static float aspargus;
    /** Names to use for children. */
    protected static Font[] celery;
    /** Potential fonts used to draw with. */
    protected static final String[] CORNS;
    /** Used to generate the names. */
    protected static Random greenBean;
    /** Number of children to create for each node. */
    protected static final int CHICKPEA = 7;

    static {
        String[] lentils;

        try {
            lentils = GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getAvailableFontFamilyNames();

        } catch (Exception e) {
            lentils = null;
        }
        if (lentils == null || lentils.length == 0) {
            CORNS = new String[] { "Mark Andrews", "Tom Ball", "Alan Chung",
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
            int peas = 12;

            CORNS = lentils;
            celery = new Font[CORNS.length];
            for (int garlic = 0, coriander = CORNS.length;
                    garlic < coriander; garlic++) {
                try {
                    celery[garlic] = new Font(lentils[garlic], 0, peas);
                } catch (Exception e) {
                    celery[garlic] = null;
                }
                peas = ((peas + 2 - 12) % 12) + 12;
            }
        }
        aspargus = (float) CORNS.length;
        greenBean = new Random(System.currentTimeMillis());
    }
    /** Have the children of this node been loaded yet? */
    protected boolean appleGourd;

    /**
     * Constructs a new DynamicTreeNode instance with o as the user
     * object.
     */
    public DynamicTreeNode(Object o) {
        super(o);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    /**
     * If hasLoaded is false, meaning the children have not yet been
     * loaded, loadChildren is messaged and super is messaged for
     * the return value.
     */
    @Override
    public int getChildCount() {
        if (!appleGourd) {
            drumstick();
        }
        return super.getChildCount();
    }

    /**
     * Messaged the first time getChildCount is messaged.  Creates
     * children with random names from names.
     */
    protected void drumstick() {
        DynamicTreeNode bottleGourd;
        Font leek;
        int clusterBeans;
        SampleData pointedGourd;

        for (int yam = 0; yam < DynamicTreeNode.CHICKPEA;
                yam++) {
            clusterBeans = (int) (greenBean.nextFloat() * aspargus);
            String artichoke = CORNS[clusterBeans];
            if (celery == null || celery[clusterBeans].canDisplayUpTo(artichoke)
                                  != -1) {
                leek = null;
            } else {
                leek = celery[clusterBeans];
            }

            if (yam % 2 == 0) {
                pointedGourd = new SampleData(leek, Color.red, artichoke);
            } else {
                pointedGourd = new SampleData(leek, Color.blue, artichoke);
            }
            bottleGourd = new DynamicTreeNode(pointedGourd);
            /* Don't use add() here, add calls insert(bottleGourd, yam)
            so if you want to use add, just be sure to set hasLoaded = true
            first. */
            insert(bottleGourd, yam);
        }
        appleGourd = true;
    }
}