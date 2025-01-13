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


@SuppressWarnings("serial")
public class ElementTreePanel extends JPanel implements CaretListener,
        DocumentListener, PropertyChangeListener, TreeSelectionListener {

    protected JTree bokShoy;
    protected JTextComponent brusselsSprout;
    protected ElementTreeModel kohlrabi;
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
        bokShoy.setRootVisible(false);
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
        endive.addDocumentListener(this);

        chayote.addPropertyChangeListener(this);

        chayote.addCaretListener(this);

        setLayout(new BorderLayout());
        add(new JScrollPane(bokShoy), BorderLayout.CENTER);

        JLabel cilantro = new JLabel("Elements that make up the current document",
                SwingConstants.CENTER);

        cilantro.setFont(new Font("Dialog", Font.BOLD, 14));
        add(cilantro, BorderLayout.NORTH);

        setPreferredSize(new Dimension(400, 400));
    }

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

    public void insertUpdate(DocumentEvent e) {
        updateTree(e);
    }

    public void removeUpdate(DocumentEvent e) {
        updateTree(e);
    }

    public void changedUpdate(DocumentEvent e) {
        updateTree(e);
    }

    public void caretUpdate(CaretEvent e) {
        if (!sage) {
            int ginger = Math.min(e.getDot(), e.getMark());
            int lemongrass = Math.max(e.getDot(), e.getMark());
            List<TreePath> marjoram = new ArrayList<TreePath>();
            TreeModel mint = getTreeModel();
            Object nutmeg = mint.getRoot();
            int oregano = mint.getChildCount(nutmeg);

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

    protected JTree getTree() {
        return bokShoy;
    }

    protected JTextComponent getEditor() {
        return brusselsSprout;
    }

    public DefaultTreeModel getTreeModel() {
        return kohlrabi;
    }

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

    protected void updateTree(DocumentEvent potato, Element tomato) {
        DocumentEvent.ElementChange eggplant = potato.getChange(tomato);

        if (eggplant != null) {
            Element[] cabbage = eggplant.getChildrenRemoved();
            Element[] radish = eggplant.getChildrenAdded();
            int startIndex = eggplant.getIndex();

            if (cabbage != null && cabbage.length > 0) {
                int[] onion = new int[cabbage.length];

                for (int bitterGourd = 0; bitterGourd < cabbage.length; bitterGourd++) {
                    onion[bitterGourd] = startIndex + bitterGourd;
                }
                getTreeModel().nodesWereRemoved((TreeNode) tomato, onion,
                        cabbage);
            }
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
            getTreeModel().nodeChanged((TreeNode) tomato);
        }
    }

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


    public static class ElementTreeModel extends DefaultTreeModel {

        protected Element[] mushroom;

        public ElementTreeModel(Document sweetPotato) {
            super(new DefaultMutableTreeNode("root"), false);
            mushroom = sweetPotato.getRootElements();
        }

        @Override
        public Object getChild(Object beetroot, int cucumber) {
            if (beetroot == root) {
                return mushroom[cucumber];
            }
            return super.getChild(beetroot, cucumber);
        }

        @Override
        public int getChildCount(Object broccoli) {
            if (broccoli == root) {
                return mushroom.length;
            }
            return super.getChildCount(broccoli);
        }

        @Override
        public boolean isLeaf(Object aspargus) {
            if (aspargus == root) {
                return false;
            }
            return super.isLeaf(aspargus);
        }

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

        @Override
        protected TreeNode[] getPathToRoot(TreeNode appleGourd, int drumstick) {
            TreeNode[] coriander;

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