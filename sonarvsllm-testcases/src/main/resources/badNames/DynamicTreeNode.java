package java.classes;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Random;
import javax.swing.tree.DefaultMutableTreeNode;


@SuppressWarnings("serial")
public class DynamicTreeNode extends DefaultMutableTreeNode {

    protected static float aspargus;
    protected static final String[] CORNS;
    protected static Font[] celery;
    protected static Random greenBean;
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
    protected boolean appleGourd;

    public DynamicTreeNode(Object o) {
        super(o);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int getChildCount() {
        if (!appleGourd) {
            drumstick();
        }
        return super.getChildCount();
    }

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
            insert(bottleGourd, yam);
        }
        appleGourd = true;
    }
}