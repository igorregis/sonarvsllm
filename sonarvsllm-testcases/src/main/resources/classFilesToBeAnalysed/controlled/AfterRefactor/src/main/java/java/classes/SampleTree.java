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


public final class SampleTree {

    protected JFrame frame;
    protected JTree tree;
    protected DefaultTreeModel treeModel;

    public SampleTree() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        JMenuBar menuBar = constructMenuBar();
        JPanel panel = new JPanel(true);

        frame = new JFrame("SampleTree");
        frame.getContentPane().add("Center", panel);
        frame.setJMenuBar(menuBar);
        frame.setBackground(Color.lightGray);

        DefaultMutableTreeNode root = createNewNode("Root");
        treeModel = new SampleTreeModel(root);

        tree = new JTree(treeModel);

        ToolTipManager.sharedInstance().registerComponent(tree);

        tree.setCellRenderer(new SampleTreeCellRenderer());

        tree.setRowHeight(-1);

        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(300, 300));
        sp.getViewport().add(tree);

        panel.setLayout(new BorderLayout());
        panel.add("Center", sp);
        panel.add("South", constructOptionsPanel());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @SuppressWarnings("serial")
    private JPanel constructOptionsPanel() {
        JPanel retPanel = new JPanel(false);
        JPanel borderPane = new JPanel(false);

        borderPane.setLayout(new BorderLayout());
        retPanel.setLayout(new FlowLayout());

        addCheckBoxes(retPanel);
        borderPane.add(retPanel, BorderLayout.CENTER);

        addRadioButtons(borderPane);

        return borderPane;
    }

    private void addCheckBoxes(JPanel panel) {
        addCheckBox(panel, "show top level handles", tree.getShowsRootHandles(), new ShowHandlesChangeListener());
        addCheckBox(panel, "show root", tree.isRootVisible(), new ShowRootChangeListener());
        addCheckBox(panel, "editable", tree.isEditable(), new TreeEditableChangeListener(), "Triple click to edit");
    }

    private void addCheckBox(JPanel panel, String text, boolean isSelected, ChangeListener listener) {
        addCheckBox(panel, text, isSelected, listener, null);
    }

    private void addCheckBox(JPanel panel, String text, boolean isSelected, ChangeListener listener, String toolTip) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setSelected(isSelected);
        checkBox.addChangeListener(listener);
        if (toolTip != null) {
            checkBox.setToolTipText(toolTip);
        }
        panel.add(checkBox);
    }

    private void addRadioButtons(JPanel borderPane) {
        ButtonGroup group = new ButtonGroup();
        JPanel buttonPane = new JPanel(false);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.setBorder(new TitledBorder("Selection Mode"));

        addRadioButton(buttonPane, group, "Single", TreeSelectionModel.SINGLE_TREE_SELECTION);
        addRadioButton(buttonPane, group, "Contiguous", TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        addRadioButton(buttonPane, group, "Discontiguous", TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION, true);

        borderPane.add(buttonPane, BorderLayout.SOUTH);
    }

    private void addRadioButton(JPanel panel, ButtonGroup group, String text, int selectionMode) {
        addRadioButton(panel, group, text, selectionMode, false);
    }

    private void addRadioButton(JPanel panel, ButtonGroup group, String text, int selectionMode, boolean isSelected) {
        JRadioButton button = new JRadioButton(text);
        button.addActionListener(e -> tree.getSelectionModel().setSelectionMode(selectionMode));
        button.setSelected(isSelected);
        group.add(button);
        panel.add(button);
    }


    private JMenuBar constructMenuBar() {
        JMenu menu;
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem;

        menu = new JMenu("File");
        menuBar.add(menu);

        menuItem = menu.add(new JMenuItem("Exit"));
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu = new JMenu("Tree");
        menuBar.add(menu);

        menuItem = menu.add(new JMenuItem("Add"));
        menuItem.addActionListener(new AddAction());

        menuItem = menu.add(new JMenuItem("Insert"));
        menuItem.addActionListener(new InsertAction());

        menuItem = menu.add(new JMenuItem("Reload"));
        menuItem.addActionListener(new ReloadAction());

        menuItem = menu.add(new JMenuItem("Remove"));
        menuItem.addActionListener(new RemoveAction());

        return menuBar;
    }

    protected DefaultMutableTreeNode getSelectedNode() {
        TreePath selPath = tree.getSelectionPath();

        if (selPath != null) {
            return (DefaultMutableTreeNode) selPath.getLastPathComponent();
        }
        return null;
    }

    protected TreePath[] getSelectedPaths() {
        return tree.getSelectionPaths();
    }

    protected DefaultMutableTreeNode createNewNode(String name) {
        return new DynamicTreeNode(new SampleData(null, Color.black, name));
    }


    class AddAction extends Object implements ActionListener {

        public int addCount;

        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode lastItem = getSelectedNode();
            DefaultMutableTreeNode parent;

            if (lastItem != null) {
                parent = (DefaultMutableTreeNode) lastItem.getParent();
                if (parent == null) {
                    parent = (DefaultMutableTreeNode) treeModel.getRoot();
                    lastItem = null;
                }
            } else {
                parent = (DefaultMutableTreeNode) treeModel.getRoot();
            }
            if (parent == null) {
                treeModel.setRoot(createNewNode("Added " + Integer.toString(
                        addCount++)));
            } else {
                int newIndex;
                if (lastItem == null) {
                    newIndex = treeModel.getChildCount(parent);
                } else {
                    newIndex = parent.getIndex(lastItem) + 1;
                }

                treeModel.insertNodeInto(createNewNode("Added " + Integer.
                                toString(addCount++)),
                        parent, newIndex);
            }
        }
    }


    class InsertAction extends Object implements ActionListener {

        public int insertCount;

        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode lastItem = getSelectedNode();
            DefaultMutableTreeNode parent;

            if (lastItem != null) {
                parent = (DefaultMutableTreeNode) lastItem.getParent();
                if (parent == null) {
                    parent = (DefaultMutableTreeNode) treeModel.getRoot();
                    lastItem = null;
                }
            } else {
                parent = (DefaultMutableTreeNode) treeModel.getRoot();
            }
            if (parent == null) {
                treeModel.setRoot(createNewNode("Inserted " + Integer.toString(
                        insertCount++)));
            } else {
                int newIndex;

                if (lastItem == null) {
                    newIndex = treeModel.getChildCount(parent);
                } else {
                    newIndex = parent.getIndex(lastItem);
                }

                treeModel.insertNodeInto(createNewNode("Inserted " + Integer.
                                toString(insertCount++)),
                        parent, newIndex);
            }
        }
    }


    class ReloadAction extends Object implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode lastItem = getSelectedNode();

            if (lastItem != null) {
                treeModel.reload(lastItem);
            }
        }
    }


    class RemoveAction extends Object implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            TreePath[] selected = getSelectedPaths();

            if (selected != null && selected.length > 0) {
                TreePath shallowest;

                while ((shallowest = findShallowestPath(selected)) != null) {
                    removeSiblings(shallowest, selected);
                }
            }
        }

        private void removeSiblings(TreePath path, TreePath[] paths) {
            if (path.getPathCount() == 1) {
                for (int counter = paths.length - 1; counter >= 0; counter--) {
                    paths[counter] = null;
                }
                treeModel.setRoot(null);
            } else {
                TreePath parent = path.getParentPath();
                MutableTreeNode parentNode = (MutableTreeNode) parent.
                        getLastPathComponent();
                ArrayList<TreePath> toRemove = new ArrayList<TreePath>();

                for (int counter = paths.length - 1; counter >= 0; counter--) {
                    if (paths[counter] != null && paths[counter].getParentPath().
                            equals(parent)) {
                        toRemove.add(paths[counter]);
                        paths[counter] = null;
                    }
                }

                int rCount = toRemove.size();
                for (int counter = paths.length - 1; counter >= 0; counter--) {
                    if (paths[counter] != null) {
                        for (int rCounter = rCount - 1; rCounter >= 0;
                             rCounter--) {
                            if ((toRemove.get(rCounter)).isDescendant(
                                    paths[counter])) {
                                paths[counter] = null;
                            }
                        }
                    }
                }

                if (rCount > 1) {
                    Collections.sort(toRemove, new PositionComparator());
                }
                int[] indices = new int[rCount];
                Object[] removedNodes = new Object[rCount];
                for (int counter = rCount - 1; counter >= 0; counter--) {
                    removedNodes[counter] = (toRemove.get(counter)).
                            getLastPathComponent();
                    indices[counter] = treeModel.getIndexOfChild(parentNode,
                            removedNodes[counter]);
                    parentNode.remove(indices[counter]);
                }
                treeModel.nodesWereRemoved(parentNode, indices, removedNodes);
            }
        }

        private TreePath findShallowestPath(TreePath[] paths) {
            int shallowest = -1;
            TreePath shallowestPath = null;

            for (int counter = paths.length - 1; counter >= 0; counter--) {
                if (paths[counter] != null) {
                    if (shallowest != -1) {
                        if (paths[counter].getPathCount() < shallowest) {
                            shallowest = paths[counter].getPathCount();
                            shallowestPath = paths[counter];
                            if (shallowest == 1) {
                                return shallowestPath;
                            }
                        }
                    } else {
                        shallowestPath = paths[counter];
                        shallowest = paths[counter].getPathCount();
                    }
                }
            }
            return shallowestPath;
        }


        private class PositionComparator implements Comparator<TreePath> {

            public int compare(TreePath p1, TreePath p2) {
                int p1Index = treeModel.getIndexOfChild(p1.getParentPath().
                        getLastPathComponent(), p1.getLastPathComponent());
                int p2Index = treeModel.getIndexOfChild(p2.getParentPath().
                        getLastPathComponent(), p2.getLastPathComponent());
                return p1Index - p2Index;
            }
        }
    }

    class ShowHandlesChangeListener extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            tree.setShowsRootHandles(((JCheckBox) e.getSource()).isSelected());
        }
    }


    class ShowRootChangeListener extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            tree.setRootVisible(((JCheckBox) e.getSource()).isSelected());
        }
    }


    class TreeEditableChangeListener extends Object implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            tree.setEditable(((JCheckBox) e.getSource()).isSelected());
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