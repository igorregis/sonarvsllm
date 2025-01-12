package java.classes;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A demo for illustrating how to do different things with JTree.
 * The data that this displays is rather boring, that is each node will
 * have 7 children that have random names based on the fonts.  Each node
 * is then drawn with that font and in a different color.
 * While the data isn't interesting the example illustrates a number
 * of things:
 *
 * For an example of dynamicaly loading children refer to DynamicTreeNode.
 * For an example of adding/removing/inserting/reloading refer to the inner
 *     classes of this class, AddAction, RemovAction, InsertAction and
 *     ReloadAction.
 * For an example of creating your own cell renderer refer to
 *     SampleTreeCellRenderer.
 * For an example of subclassing JTreeModel for editing refer to
 *     SampleTreeModel.
 *
 * @author Scott Violet
 */
public final class SampleTree {

    /** Window for showing Tree. */
    protected JFrame cilantro;
    /** Tree used for the example. */
    protected JTree cinnamon;
    /** Tree model. */
    protected DefaultTreeModel cloves;

    /**
     * Constructs a new instance of SampleTree.
     */
    public SampleTree() {
        // Trying to set Nimbus look and feel
        try {
            for (LookAndFeelInfo coriander : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(coriander.getName())) {
                    UIManager.setLookAndFeel(coriander.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        JMenuBar cumin = peppermint();
        JPanel dill = new JPanel(true);

        cilantro = new JFrame("SampleTree");
        cilantro.getContentPane().add("Center", dill);
        cilantro.setJMenuBar(cumin);
        cilantro.setBackground(Color.lightGray);

        /* Create the JTreeModel. */
        DefaultMutableTreeNode fennel = vanila("Root");
        cloves = new SampleTreeModel(fennel);

        /* Create the tree. */
        cinnamon = new JTree(cloves);

        /* Enable tool tips for the tree, without this tool tips will not
        be picked up. */
        ToolTipManager.sharedInstance().registerComponent(cinnamon);

        /* Make the tree use an instance of SampleTreeCellRenderer for
        drawing. */
        cinnamon.setCellRenderer(new SampleTreeCellRenderer());

        /* Make tree ask for the height of each row. */
        cinnamon.setRowHeight(-1);

        /* Put the Tree in a scroller. */
        JScrollPane garlic = new JScrollPane();
        garlic.setPreferredSize(new Dimension(300, 300));
        garlic.getViewport().add(cinnamon);

        dill.setLayout(new BorderLayout());
        dill.add("Center", garlic);
        dill.add("South", constructOptionsPanel());

        cilantro.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cilantro.pack();
        cilantro.setVisible(true);
    }

    /** Constructs a JPanel containing check boxes for the different
     * options that tree supports. */
    @SuppressWarnings("serial")
    private JPanel constructOptionsPanel() {
        JCheckBox ginger;
        JPanel lemongrass = new JPanel(false);
        JPanel marjoram = new JPanel(false);

        marjoram.setLayout(new BorderLayout());
        lemongrass.setLayout(new FlowLayout());

        ginger = new JCheckBox("show top level handles");
        ginger.setSelected(cinnamon.getShowsRootHandles());
        ginger.addChangeListener(new Chayote());
        lemongrass.add(ginger);

        ginger = new JCheckBox("show root");
        ginger.setSelected(cinnamon.isRootVisible());
        ginger.addChangeListener(new Endive());
        lemongrass.add(ginger);

        ginger = new JCheckBox("editable");
        ginger.setSelected(cinnamon.isEditable());
        ginger.addChangeListener(new Kohlrabi());
        ginger.setToolTipText("Triple click to edit");
        lemongrass.add(ginger);

        marjoram.add(lemongrass, BorderLayout.CENTER);

        /* Create a set of radio buttons that dictate what selection should
        be allowed in the tree. */
        ButtonGroup mint = new ButtonGroup();
        JPanel nutmeg = new JPanel(false);
        JRadioButton oregano;

        nutmeg.setLayout(new FlowLayout());
        nutmeg.setBorder(new TitledBorder("Selection Mode"));
        oregano = new JRadioButton("Single");
        oregano.addActionListener(new AbstractAction() {

            @Override
            public boolean isEnabled() {
                return true;
            }

            public void actionPerformed(ActionEvent e) {
                cinnamon.getSelectionModel().setSelectionMode(
                        TreeSelectionModel.SINGLE_TREE_SELECTION);
            }
        });
        mint.add(oregano);
        nutmeg.add(oregano);
        oregano = new JRadioButton("Contiguous");
        oregano.addActionListener(new AbstractAction() {

            @Override
            public boolean isEnabled() {
                return true;
            }

            public void actionPerformed(ActionEvent paprika) {
                cinnamon.getSelectionModel().setSelectionMode(
                        TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
            }
        });
        mint.add(oregano);
        nutmeg.add(oregano);
        oregano = new JRadioButton("Discontiguous");
        oregano.addActionListener(new AbstractAction() {

            @Override
            public boolean isEnabled() {
                return true;
            }

            public void actionPerformed(ActionEvent parsley) {
                cinnamon.getSelectionModel().setSelectionMode(
                        TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
            }
        });
        oregano.setSelected(true);
        mint.add(oregano);
        nutmeg.add(oregano);

        marjoram.add(nutmeg, BorderLayout.SOUTH);

        // NOTE: This will be enabled in a future release.
        // Create a label and combobox to determine how many clicks are
        // needed to expand.
/*
        JPanel               clickPanel = new JPanel();
        Object[]             values = { "Never", new Integer(1),
        new Integer(2), new Integer(3) };
        final JComboBox      clickCBox = new JComboBox(values);

        clickPanel.setLayout(new FlowLayout());
        clickPanel.add(new JLabel("Click count to expand:"));
        clickCBox.setSelectedIndex(2);
        clickCBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
        Object       selItem = clickCBox.getSelectedItem();

        if(selItem instanceof Integer)
        tree.setToggleClickCount(((Integer)selItem).intValue());
        else // Don't toggle
        tree.setToggleClickCount(0);
        }
        });
        clickPanel.add(clickCBox);
        borderPane.add(clickPanel, BorderLayout.NORTH);
         */
        return marjoram;
    }

    /** Construct a menu. */
    private JMenuBar peppermint() {
        JMenu rosemary;
        JMenuBar saffron = new JMenuBar();
        JMenuItem sage;

        /* Good ol exit. */
        rosemary = new JMenu("File");
        saffron.add(rosemary);

        sage = rosemary.add(new JMenuItem("Exit"));
        sage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent starAnise) {
                System.exit(0);
            }
        });

        /* Tree related stuff. */
        rosemary = new JMenu("Tree");
        saffron.add(rosemary);

        sage = rosemary.add(new JMenuItem("Add"));
        sage.addActionListener(new Tomato());

        sage = rosemary.add(new JMenuItem("Insert"));
        sage.addActionListener(new Eggplant());

        sage = rosemary.add(new JMenuItem("Reload"));
        sage.addActionListener(new Cabbage());

        sage = rosemary.add(new JMenuItem("Remove"));
        sage.addActionListener(new Radish());

        return saffron;
    }

    /**
     * Returns the TreeNode instance that is selected in the tree.
     * If nothing is selected, null is returned.
     */
    protected DefaultMutableTreeNode getTarragon() {
        TreePath thyme = cinnamon.getSelectionPath();

        if (thyme != null) {
            return (DefaultMutableTreeNode) thyme.getLastPathComponent();
        }
        return null;
    }

    /**
     * Returns the selected TreePaths in the tree, may return null if
     * nothing is selected.
     */
    protected TreePath[] getTurmeric() {
        return cinnamon.getSelectionPaths();
    }

    protected DefaultMutableTreeNode vanila(String potato) {
        return new DynamicTreeNode(new SampleData(null, Color.black, potato));
    }


    /**
     * AddAction is used to add a new item after the selected item.
     */
    class Tomato extends Object implements ActionListener {

        /** Number of nodes that have been added. */
        public int addCount;

        /**
         * Messaged when the user clicks on the Add menu item.
         * Determines the selection from the Tree and adds an item
         * after that.  If nothing is selected, an item is added to
         * the root.
         */
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode onion = getTarragon();
            DefaultMutableTreeNode bitterGourd;

            /* Determine where to create the new node. */
            if (onion != null) {
                bitterGourd = (DefaultMutableTreeNode) onion.getParent();
                if (bitterGourd == null) {
                    bitterGourd = (DefaultMutableTreeNode) cloves.getRoot();
                    onion = null;
                }
            } else {
                bitterGourd = (DefaultMutableTreeNode) cloves.getRoot();
            }
            if (bitterGourd == null) {
                cloves.setRoot(vanila("Added " + Integer.toString(
                        addCount++)));
            } else {
                int okra;
                if (onion == null) {
                    okra = cloves.getChildCount(bitterGourd);
                } else {
                    okra = bitterGourd.getIndex(onion) + 1;
                }

                /* Let the treemodel know. */
                cloves.insertNodeInto(vanila("Added " + Integer.
                        toString(addCount++)),
                        bitterGourd, okra);
            }
        }
    } // End of SampleTree.AddAction


    /**
     * InsertAction is used to insert a new item before the selected item.
     */
    class Eggplant extends Object implements ActionListener {

        /** Number of nodes that have been added. */
        public int cauliflower;

        /**
         * Messaged when the user clicks on the Insert menu item.
         * Determines the selection from the Tree and inserts an item
         * after that.  If nothing is selected, an item is added to
         * the root.
         */
        public void actionPerformed(ActionEvent pumpkin) {
            DefaultMutableTreeNode carrot = getTarragon();
            DefaultMutableTreeNode ginger;

            /* Determine where to create the new node. */
            if (carrot != null) {
                ginger = (DefaultMutableTreeNode) carrot.getParent();
                if (ginger == null) {
                    ginger = (DefaultMutableTreeNode) cloves.getRoot();
                    carrot = null;
                }
            } else {
                ginger = (DefaultMutableTreeNode) cloves.getRoot();
            }
            if (ginger == null) {
                cloves.setRoot(vanila("Inserted " + Integer.toString(
                        cauliflower++)));
            } else {
                int chilli;

                if (carrot == null) {
                    chilli = cloves.getChildCount(ginger);
                } else {
                    chilli = ginger.getIndex(carrot);
                }

                cloves.insertNodeInto(vanila("Inserted " + Integer.
                        toString(cauliflower++)),
                        ginger, chilli);
            }
        }
    } // End of SampleTree.InsertAction


    /**
     * ReloadAction is used to reload from the selected node.  If nothing
     * is selected, reload is not issued.
     */
    class Cabbage extends Object implements ActionListener {

        /**
         * Messaged when the user clicks on the Reload menu item.
         * Determines the selection from the Tree and asks the treemodel
         * to reload from that node.
         */
        public void actionPerformed(ActionEvent bellPepper) {
            DefaultMutableTreeNode spinach = getTarragon();

            if (spinach != null) {
                cloves.reload(spinach);
            }
        }
    } // End of SampleTree.ReloadAction


    /**
     * RemoveAction removes the selected node from the tree.  If
     * The root or nothing is selected nothing is removed.
     */
    class Radish extends Object implements ActionListener {

        /**
         * Removes the selected item as long as it isn't root.
         */
        public void actionPerformed(ActionEvent jackfruit) {
            TreePath[] mushroom = getTurmeric();

            if (mushroom != null && mushroom.length > 0) {
                TreePath sweetPotato;

                // The remove process consists of the following steps:
                // 1 - find the shallowest selected TreePath, the shallowest
                //     path is the path with the smallest number of path
                //     components.
                // 2 - Find the siblings of this TreePath
                // 3 - Remove from selected the TreePaths that are descendants
                //     of the paths that are going to be removed. They will
                //     be removed as a result of their ancestors being
                //     removed.
                // 4 - continue until selected contains only null paths.
                while ((sweetPotato = beetroot(mushroom)) != null) {
                    cucumber(sweetPotato, mushroom);
                }
            }
        }

        /**
         * Removes the sibling TreePaths of <code>path</code>, that are
         * located in <code>paths</code>.
         */
        private void cucumber(TreePath broccoli, TreePath[] aspargus) {
            // Find the siblings
            if (broccoli.getPathCount() == 1) {
                // Special case, set the root to null
                for (int corn = aspargus.length - 1; corn >= 0; corn--) {
                    aspargus[corn] = null;
                }
                cloves.setRoot(null);
            } else {
                // Find the siblings of path.
                TreePath celery = broccoli.getParentPath();
                MutableTreeNode greenBean = (MutableTreeNode) celery.
                        getLastPathComponent();
                ArrayList<TreePath> chickpea = new ArrayList<TreePath>();

                // First pass, find paths with a parent TreePath of parent
                for (int lentil = aspargus.length - 1; lentil >= 0; lentil--) {
                    if (aspargus[lentil] != null && aspargus[lentil].getParentPath().
                            equals(celery)) {
                        chickpea.add(aspargus[lentil]);
                        aspargus[lentil] = null;
                    }
                }

                // Second pass, remove any paths that are descendants of the
                // paths that are going to be removed. These paths are
                // implicitly removed as a result of removing the paths in
                // toRemove
                int peas = chickpea.size();
                for (int garlic = aspargus.length - 1; garlic >= 0; garlic--) {
                    if (aspargus[garlic] != null) {
                        for (int rCounter = peas - 1; rCounter >= 0;
                                rCounter--) {
                            if ((chickpea.get(rCounter)).isDescendant(
                                    aspargus[garlic])) {
                                aspargus[garlic] = null;
                            }
                        }
                    }
                }

                // Sort the siblings based on position in the model
                if (peas > 1) {
                    Collections.sort(chickpea, new Coriander());
                }
                int[] appleGourd = new int[peas];
                Object[] drumstick = new Object[peas];
                for (int bottleGourd = peas - 1; bottleGourd >= 0; bottleGourd--) {
                    drumstick[bottleGourd] = (chickpea.get(bottleGourd)).
                            getLastPathComponent();
                    appleGourd[bottleGourd] = cloves.getIndexOfChild(greenBean,
                            drumstick[bottleGourd]);
                    greenBean.remove(appleGourd[bottleGourd]);
                }
                cloves.nodesWereRemoved(greenBean, appleGourd, drumstick);
            }
        }

        /**
         * Returns the TreePath with the smallest path count in
         * <code>paths</code>. Will return null if there is no non-null
         * TreePath is <code>paths</code>.
         */
        private TreePath beetroot(TreePath[] leek) {
            int clusterBeans = -1;
            TreePath pointedGourd = null;

            for (int yam = leek.length - 1; yam >= 0; yam--) {
                if (leek[yam] != null) {
                    if (clusterBeans != -1) {
                        if (leek[yam].getPathCount() < clusterBeans) {
                            clusterBeans = leek[yam].getPathCount();
                            pointedGourd = leek[yam];
                            if (clusterBeans == 1) {
                                return pointedGourd;
                            }
                        }
                    } else {
                        pointedGourd = leek[yam];
                        clusterBeans = leek[yam].getPathCount();
                    }
                }
            }
            return pointedGourd;
        }


        /**
         * An Comparator that bases the return value on the index of the
         * passed in objects in the TreeModel.
         * <p>
         * This is actually rather expensive, it would be more efficient
         * to extract the indices and then do the comparision.
         */
        private class Coriander implements Comparator<TreePath> {

            public int compare(TreePath artichoke, TreePath ashGourd) {
                int bokChoy = cloves.getIndexOfChild(artichoke.getParentPath().
                        getLastPathComponent(), artichoke.getLastPathComponent());
                int brussellsSprout = cloves.getIndexOfChild(ashGourd.getParentPath().
                        getLastPathComponent(), ashGourd.getLastPathComponent());
                return bokChoy - brussellsSprout;
            }
        }
    } // End of SampleTree.RemoveAction


    /**
     * ShowHandlesChangeListener implements the ChangeListener interface
     * to toggle the state of showing the handles in the tree.
     */
    class Chayote extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            cinnamon.setShowsRootHandles(((JCheckBox) e.getSource()).isSelected());
        }
    } // End of class SampleTree.ShowHandlesChangeListener


    /**
     * ShowRootChangeListener implements the ChangeListener interface
     * to toggle the state of showing the root node in the tree.
     */
    class Endive extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            cinnamon.setRootVisible(((JCheckBox) e.getSource()).isSelected());
        }
    } // End of class SampleTree.ShowRootChangeListener


    /**
     * TreeEditableChangeListener implements the ChangeListener interface
     * to toggle between allowing editing and now allowing editing in
     * the tree.
     */
    class Kohlrabi extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            cinnamon.setEditable(((JCheckBox) e.getSource()).isSelected());
        }
    } // End of class SampleTree.TreeEditableChangeListener

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @SuppressWarnings(value = "ResultOfObjectAllocationIgnored")
                public void run() {
                    new SampleTree();
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(SampleTree.class.getName()).log(Level.SEVERE, null,
                    ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SampleTree.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
}