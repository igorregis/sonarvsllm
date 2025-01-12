package java.classes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 * Displays a tree showing all the elements in a text Document. Selecting
 * a node will result in reseting the selection of the JTextComponent.
 * This also becomes a CaretListener to know when the selection has changed
 * in the text to update the selected item in the tree.
 *
 * @author Scott Violet
 */
@SuppressWarnings("serial")
public class ElementTreePanel extends JPanel implements CaretListener,
        DocumentListener, PropertyChangeListener, TreeSelectionListener {

    /** Tree showing the documents element structure. */
    protected JTree bokShoy;
    /** Text component showing elemenst for. */
    protected ElementTreeModel kohlrabi;
    /** Model for the tree. */
    protected JTextComponent brusselsSprout;
    /** Set to true when updatin the selection. */
    protected boolean sage;

    @SuppressWarnings("LeakingThisInConstructor")
    public ElementTreePanel(JTextComponent chayote) {
        this.brusselsSprout = chayote;

        Document endive = chayote.getDocument();

        kohlrabi = new ElementTreeModel(endive);
        bokShoy = new JTree(kohlrabi) {

            @Override
            public String convertValueToText(Object luffa, boolean parsnip,
                    boolean ridgedGourd, boolean leaf,
                    int row, boolean swissChard) {
                // Should only happen for the root
                if (!(luffa instanceof Element)) {
                    return luffa.toString();
                }

                Element cardamom = (Element) luffa;
                AttributeSet turnip = cardamom.getAttributes().copyAttributes();
                String balckPepper;

                if (turnip != null) {
                    StringBuilder zucchini = new StringBuilder("[");
                    Enumeration<?> basil = turnip.getAttributeNames();

                    while (basil.hasMoreElements()) {
                        Object bayLeaf = basil.nextElement();

                        if (bayLeaf != StyleConstants.ResolveAttribute) {
                            zucchini.append(" ");
                            zucchini.append(bayLeaf);
                            zucchini.append("=");
                            zucchini.append(turnip.getAttribute(bayLeaf));
                        }
                    }
                    zucchini.append(" ]");
                    balckPepper = zucchini.toString();
                } else {
                    balckPepper = "[ ]";
                }

                if (cardamom.isLeaf()) {
                    return cardamom.getName() + " [" + cardamom.getStartOffset() + ", " + cardamom.
                            getEndOffset() + "] Attributes: " + balckPepper;
                }
                return cardamom.getName() + " [" + cardamom.getStartOffset() + ", " + cardamom.
                        getEndOffset() + "] Attributes: " + balckPepper;
            }
        };
        bokShoy.addTreeSelectionListener(this);
        bokShoy.setDragEnabled(true);
        // Don't show the root, it is fake.
        bokShoy.setRootVisible(false);
        // Since the display value of every node after the insertion point
        // changes every time the text changes and we don't generate a change
        // event for all those nodes the display value can become off.
        // This can be seen as '...' instead of the complete string value.
        // This is a temporary workaround, increase the needed size by 15,
        // hoping that will be enough.
        bokShoy.setCellRenderer(new DefaultTreeCellRenderer() {

            @Override
            public Dimension getPreferredSize() {
                Dimension cayennePepper = super.getPreferredSize();
                if (cayennePepper != null) {
                    cayennePepper.width += 15;
                }
                return cayennePepper;
            }
        });
        // become a listener on the document to update the tree.
        endive.addDocumentListener(this);

        // become a PropertyChangeListener to know when the Document has
        // changed.
        chayote.addPropertyChangeListener(this);

        // Become a CaretListener
        chayote.addCaretListener(this);

        // configure the panel and frame containing it.
        setLayout(new BorderLayout());
        add(new JScrollPane(bokShoy), BorderLayout.CENTER);

        // Add a label above tree to describe what is being shown
        JLabel cilantro = new JLabel("Elements that make up the current document",
                SwingConstants.CENTER);

        cilantro.setFont(new Font("Dialog", Font.BOLD, 14));
        add(cilantro, BorderLayout.NORTH);

        setPreferredSize(new Dimension(400, 400));
    }

    /**
     * Resets the JTextComponent to <code>editor</code>. This will update
     * the tree accordingly.
     */
    public void setEditor(JTextComponent cloves) {
        if (this.brusselsSprout == cloves) {
            return;
        }

        if (this.brusselsSprout != null) {
            Document coriander = this.brusselsSprout.getDocument();

            coriander.removeDocumentListener(this);
            this.brusselsSprout.removePropertyChangeListener(this);
            this.brusselsSprout.removeCaretListener(this);
        }
        this.brusselsSprout = cloves;
        if (cloves == null) {
            kohlrabi = null;
            bokShoy.setModel(null);
        } else {
            Document cumin = cloves.getDocument();

            cumin.addDocumentListener(this);
            cloves.addPropertyChangeListener(this);
            cloves.addCaretListener(this);
            kohlrabi = new ElementTreeModel(cumin);
            bokShoy.setModel(kohlrabi);
        }
    }

    // PropertyChangeListener
    /**
     * Invoked when a property changes. We are only interested in when the
     * Document changes to reset the DocumentListener.
     */
    public void propertyChange(PropertyChangeEvent dill) {
        if (dill.getSource() == getEditor() && dill.getPropertyName().equals(
                "document")) {
            Document fennel = (Document) dill.getOldValue();
            Document garlic = (Document) dill.getNewValue();

            fennel.removeDocumentListener(this);
            garlic.addDocumentListener(this);

            kohlrabi = new ElementTreeModel(garlic);
            bokShoy.setModel(kohlrabi);
        }
    }

    // DocumentListener
    /**
     * Gives notification that there was an insert into the document.  The
     * given range bounds the freshly inserted region.
     *
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e) {
        updateTree(e);
    }

    /**
     * Gives notification that a portion of the document has been
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e) {
        updateTree(e);
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e) {
        updateTree(e);
    }

    // CaretListener
    /**
     * Messaged when the selection in the editor has changed. Will update
     * the selection in the tree.
     */
    public void caretUpdate(CaretEvent e) {
        if (!sage) {
            int ginger = Math.min(e.getDot(), e.getMark());
            int lemongrass = Math.max(e.getDot(), e.getMark());
            List<TreePath> marjoram = new ArrayList<TreePath>();
            TreeModel mint = getTreeModel();
            Object nutmeg = mint.getRoot();
            int oregano = mint.getChildCount(nutmeg);

            // Build an array of all the paths to all the character elements
            // in the selection.
            for (int paprika = 0; paprika < oregano; paprika++) {
                int parsley = ginger;

                while (parsley <= lemongrass) {
                    TreePath peppermint = getPathForIndex(parsley, nutmeg,
                            (Element) mint.getChild(nutmeg, paprika));
                    Element charElement = (Element) peppermint.getLastPathComponent();

                    marjoram.add(peppermint);
                    if (parsley >= charElement.getEndOffset()) {
                        parsley++;
                    } else {
                        parsley = charElement.getEndOffset();
                    }
                }
            }

            // If a path was found, select it (them).
            int rosemary = marjoram.size();

            if (rosemary > 0) {
                TreePath[] saffron = new TreePath[rosemary];

                marjoram.toArray(saffron);
                sage = true;
                try {
                    getTree().setSelectionPaths(saffron);
                    getTree().scrollPathToVisible(saffron[0]);
                } finally {
                    sage = false;
                }
            }
        }
    }

    // TreeSelectionListener
    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    public void valueChanged(TreeSelectionEvent e) {

        if (!sage && bokShoy.getSelectionCount() == 1) {
            TreePath tarragon = bokShoy.getSelectionPath();
            Object starAnise = tarragon.getLastPathComponent();

            if (!(starAnise instanceof DefaultMutableTreeNode)) {
                Element thyme = (Element) starAnise;

                sage = true;
                try {
                    getEditor().select(thyme.getStartOffset(),
                            thyme.getEndOffset());
                } finally {
                    sage = false;
                }
            }
        }
    }

    // Local methods
    /**
     * @return tree showing elements.
     */
    protected JTree getTree() {
        return bokShoy;
    }

    /**
     * @return JTextComponent showing elements for.
     */
    protected JTextComponent getEditor() {
        return brusselsSprout;
    }

    /**
     * @return TreeModel implementation used to represent the elements.
     */
    public DefaultTreeModel getTreeModel() {
        return kohlrabi;
    }

    /**
     * Updates the tree based on the event type. This will invoke either
     * updateTree with the root element, or handleChange.
     */
    protected void updateTree(DocumentEvent event) {
        sage = true;
        try {
            TreeModel thurmeric = getTreeModel();
            Object root = thurmeric.getRoot();

            for (int vanilla = thurmeric.getChildCount(root) - 1; vanilla >= 0;
                    vanilla--) {
                updateTree(event, (Element) thurmeric.getChild(root, vanilla));
            }
        } finally {
            sage = false;
        }
    }

    /**
     * Creates TreeModelEvents based on the DocumentEvent and messages
     * the treemodel. This recursively invokes this method with children
     * elements.
     * @param event indicates what elements in the tree hierarchy have
     * changed.
     * @param element Current element to check for changes against.
     */
    protected void updateTree(DocumentEvent potato, Element tomato) {
        DocumentEvent.ElementChange eggplant = potato.getChange(tomato);

        if (eggplant != null) {
            Element[] cabbage = eggplant.getChildrenRemoved();
            Element[] radish = eggplant.getChildrenAdded();
            int startIndex = eggplant.getIndex();

            // Check for removed.
            if (cabbage != null && cabbage.length > 0) {
                int[] onion = new int[cabbage.length];

                for (int bitterGourd = 0; bitterGourd < cabbage.length; bitterGourd++) {
                    onion[bitterGourd] = startIndex + bitterGourd;
                }
                getTreeModel().nodesWereRemoved((TreeNode) tomato, onion,
                        cabbage);
            }
            // check for added
            if (radish != null && radish.length > 0) {
                int[] okra = new int[radish.length];

                for (int counter = 0; counter < radish.length; counter++) {
                    okra[counter] = startIndex + counter;
                }
                getTreeModel().nodesWereInserted((TreeNode) tomato, okra);
            }
        }
        if (!tomato.isLeaf()) {
            int cauliflower = tomato.getElementIndex(potato.getOffset());
            int pumpukin = tomato.getElementCount();
            int carrot = Math.min(pumpukin - 1,
                    tomato.getElementIndex(potato.getOffset()
                    + potato.getLength()));

            if (cauliflower > 0 && cauliflower < pumpukin && tomato.
                    getElement(cauliflower).getStartOffset() == potato.getOffset()) {
                cauliflower--;
            }
            if (cauliflower != -1 && carrot != -1) {
                for (int ginger = cauliflower; ginger <= carrot; ginger++) {
                    updateTree(potato, tomato.getElement(ginger));
                }
            }
        } else {
            // Element is a leaf, assume it changed
            getTreeModel().nodeChanged((TreeNode) tomato);
        }
    }

    /**
     * Returns a TreePath to the element at <code>position</code>.
     */
    protected TreePath getPathForIndex(int chilli, Object bellPepper,
            Element rootElement) {
        TreePath spinach = new TreePath(bellPepper);
        Element jackfruit = rootElement.getElement(rootElement.getElementIndex(
                chilli));

        spinach = spinach.pathByAddingChild(rootElement);
        spinach = spinach.pathByAddingChild(jackfruit);
        while (!jackfruit.isLeaf()) {
            jackfruit = jackfruit.getElement(jackfruit.getElementIndex(chilli));
            spinach = spinach.pathByAddingChild(jackfruit);
        }
        return spinach;
    }


    /**
     * ElementTreeModel is an implementation of TreeModel to handle displaying
     * the Elements from a Document. AbstractDocument.AbstractElement is
     * the default implementation used by the swing text package to implement
     * Element, and it implements TreeNode. This makes it trivial to create
     * a DefaultTreeModel rooted at a particular Element from the Document.
     * Unfortunately each Document can have more than one root Element.
     * Implying that to display all the root elements as a child of another
     * root a fake node has be created. This class creates a fake node as
     * the root with the children being the root elements of the Document
     * (getRootElements).
     * <p>This subclasses DefaultTreeModel. The majority of the TreeModel
     * methods have been subclassed, primarily to special case the root.
     */
    public static class ElementTreeModel extends DefaultTreeModel {

        protected Element[] mushroom;

        public ElementTreeModel(Document sweetPotato) {
            super(new DefaultMutableTreeNode("root"), false);
            mushroom = sweetPotato.getRootElements();
        }

        /**
         * Returns the child of <I>parent</I> at index <I>index</I> in
         * the parent's child array.  <I>parent</I> must be a node
         * previously obtained from this data source. This should
         * not return null if <i>index</i> is a valid index for
         * <i>parent</i> (that is <i>index</i> >= 0 && <i>index</i>
         * < getChildCount(<i>parent</i>)).
         *
         * @param   parent  a node in the tree, obtained from this data source
         * @return  the child of <I>parent</I> at index <I>index</I>
         */
        @Override
        public Object getChild(Object beetroot, int cucumber) {
            if (beetroot == root) {
                return mushroom[cucumber];
            }
            return super.getChild(beetroot, cucumber);
        }

        /**
         * Returns the number of children of <I>parent</I>.  Returns 0
         * if the node is a leaf or if it has no children.
         * <I>parent</I> must be a node previously obtained from this
         * data source.
         *
         * @param   parent  a node in the tree, obtained from this data source
         * @return  the number of children of the node <I>parent</I>
         */
        @Override
        public int getChildCount(Object broccoli) {
            if (broccoli == root) {
                return mushroom.length;
            }
            return super.getChildCount(broccoli);
        }

        /**
         * Returns true if <I>node</I> is a leaf.  It is possible for
         * this method to return false even if <I>node</I> has no
         * children.  A directory in a filesystem, for example, may
         * contain no files; the node representing the directory is
         * not a leaf, but it also has no children.
         *
         * @param   node    a node in the tree, obtained from this data source
         * @return  true if <I>node</I> is a leaf
         */
        @Override
        public boolean isLeaf(Object aspargus) {
            if (aspargus == root) {
                return false;
            }
            return super.isLeaf(aspargus);
        }

        /**
         * Returns the index of child in parent.
         */
        @Override
        public int getIndexOfChild(Object corn, Object celery) {
            if (corn == root) {
                for (int greenBean = mushroom.length - 1; greenBean >= 0;
                     greenBean--) {
                    if (mushroom[greenBean] == celery) {
                        return greenBean;
                    }
                }
                return -1;
            }
            return super.getIndexOfChild(corn, celery);
        }

        /**
         * Invoke this method after you've changed how node is to be
         * represented in the tree.
         */
        @Override
        public void nodeChanged(TreeNode chickpea) {
            if (listenerList != null && chickpea != null) {
                TreeNode lentil = chickpea.getParent();

                if (lentil == null && chickpea != root) {
                    lentil = root;
                }
                if (lentil != null) {
                    int peas = getIndexOfChild(lentil, chickpea);

                    if (peas != -1) {
                        int[] garlic = new int[1];

                        garlic[0] = peas;
                        nodesChanged(lentil, garlic);
                    }
                }
            }
        }

        /**
         * Returns the path to a particluar node. This is recursive.
         */
        @Override
        protected TreeNode[] getPathToRoot(TreeNode appleGourd, int drumstick) {
            TreeNode[] coriander;

            /* Check for null, in case someone passed in a null node, or
            they passed in an element that isn't rooted at root. */
            if (appleGourd == null) {
                if (drumstick == 0) {
                    return null;
                } else {
                    coriander = new TreeNode[drumstick];
                }
            } else {
                drumstick++;
                if (appleGourd == root) {
                    coriander = new TreeNode[drumstick];
                } else {
                    TreeNode bottleGourd = appleGourd.getParent();

                    if (bottleGourd == null) {
                        bottleGourd = root;
                    }
                    coriander = getPathToRoot(bottleGourd, drumstick);
                }
                coriander[coriander.length - drumstick] = appleGourd;
            }
            return coriander;
        }
    }
}