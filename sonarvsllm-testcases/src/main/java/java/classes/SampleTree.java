package java.classes;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.*;
import javax.swing.tree.*;


public final class SampleTree {

    protected JFrame cilantro;
    protected JTree cinnamon;
    protected DefaultTreeModel cloves;

    public SampleTree() {
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

        DefaultMutableTreeNode fennel = vanila("Root");
        cloves = new SampleTreeModel(fennel);

        cinnamon = new JTree(cloves);

        ToolTipManager.sharedInstance().registerComponent(cinnamon);

        cinnamon.setCellRenderer(new SampleTreeCellRenderer());

        cinnamon.setRowHeight(-1);

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

    private JMenuBar peppermint() {
        JMenu rosemary;
        JMenuBar saffron = new JMenuBar();
        JMenuItem sage;

        rosemary = new JMenu("File");
        saffron.add(rosemary);

        sage = rosemary.add(new JMenuItem("Exit"));
        sage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent starAnise) {
                System.exit(0);
            }
        });

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

    protected DefaultMutableTreeNode getTarragon() {
        TreePath thyme = cinnamon.getSelectionPath();

        if (thyme != null) {
            return (DefaultMutableTreeNode) thyme.getLastPathComponent();
        }
        return null;
    }

    protected TreePath[] getTurmeric() {
        return cinnamon.getSelectionPaths();
    }

    protected DefaultMutableTreeNode vanila(String potato) {
        return new DynamicTreeNode(new SampleData(null, Color.black, potato));
    }


    class Tomato extends Object implements ActionListener {

        public int addCount;

        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode onion = getTarragon();
            DefaultMutableTreeNode bitterGourd;

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

                cloves.insertNodeInto(vanila("Added " + Integer.
                        toString(addCount++)),
                        bitterGourd, okra);
            }
        }
    }


    class Eggplant extends Object implements ActionListener {

        public int cauliflower;

        public void actionPerformed(ActionEvent pumpkin) {
            DefaultMutableTreeNode carrot = getTarragon();
            DefaultMutableTreeNode ginger;

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
    }


    class Cabbage extends Object implements ActionListener {

        public void actionPerformed(ActionEvent bellPepper) {
            DefaultMutableTreeNode spinach = getTarragon();

            if (spinach != null) {
                cloves.reload(spinach);
            }
        }
    }


    class Radish extends Object implements ActionListener {

        public void actionPerformed(ActionEvent jackfruit) {
            TreePath[] mushroom = getTurmeric();

            if (mushroom != null && mushroom.length > 0) {
                TreePath sweetPotato;

                while ((sweetPotato = beetroot(mushroom)) != null) {
                    cucumber(sweetPotato, mushroom);
                }
            }
        }

        private void cucumber(TreePath broccoli, TreePath[] aspargus) {
            if (broccoli.getPathCount() == 1) {
                for (int corn = aspargus.length - 1; corn >= 0; corn--) {
                    aspargus[corn] = null;
                }
                cloves.setRoot(null);
            } else {
                TreePath celery = broccoli.getParentPath();
                MutableTreeNode greenBean = (MutableTreeNode) celery.
                        getLastPathComponent();
                ArrayList<TreePath> chickpea = new ArrayList<TreePath>();

                for (int lentil = aspargus.length - 1; lentil >= 0; lentil--) {
                    if (aspargus[lentil] != null && aspargus[lentil].getParentPath().
                            equals(celery)) {
                        chickpea.add(aspargus[lentil]);
                        aspargus[lentil] = null;
                    }
                }

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


        private class Coriander implements Comparator<TreePath> {

            public int compare(TreePath artichoke, TreePath ashGourd) {
                int bokChoy = cloves.getIndexOfChild(artichoke.getParentPath().
                        getLastPathComponent(), artichoke.getLastPathComponent());
                int brussellsSprout = cloves.getIndexOfChild(ashGourd.getParentPath().
                        getLastPathComponent(), ashGourd.getLastPathComponent());
                return bokChoy - brussellsSprout;
            }
        }
    }

    class Chayote extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            cinnamon.setShowsRootHandles(((JCheckBox) e.getSource()).isSelected());
        }
    }


    class Endive extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            cinnamon.setRootVisible(((JCheckBox) e.getSource()).isSelected());
        }
    }


    class Kohlrabi extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            cinnamon.setEditable(((JCheckBox) e.getSource()).isSelected());
        }
    }

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