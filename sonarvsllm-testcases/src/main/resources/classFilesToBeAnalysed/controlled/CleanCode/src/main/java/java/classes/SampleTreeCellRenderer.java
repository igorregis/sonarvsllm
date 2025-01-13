package java.classes;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;


@SuppressWarnings("serial")
public class SampleTreeCellRenderer extends JLabel implements TreeCellRenderer {

    /** Font used if the string to be displayed isn't a font. */
    protected static Font defaultFont;
    /** Icon to use when the item is collapsed. */
    protected static ImageIcon collapsedIcon;
    /** Icon to use when the item is expanded. */
    protected static ImageIcon expandedIcon;
    /** Color to use for the background when selected. */
    protected static final Color SELECTED_BACKGROUND_COLOR;

    static {
        if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
            SELECTED_BACKGROUND_COLOR = new Color(0, 0,
                    0, 0);
        } else {
            SELECTED_BACKGROUND_COLOR = Color.YELLOW;
        }
        try {
            defaultFont = new Font("SansSerif", 0, 12);
        } catch (Exception e) {
            //No need to deal with this error
        }
        try {
            collapsedIcon = new ImageIcon(SampleTreeCellRenderer.class.
                    getResource("/resources/images/collapsed.gif"));
            expandedIcon = new ImageIcon(SampleTreeCellRenderer.class.
                    getResource("/resources/images/expanded.gif"));
        } catch (Exception e) {
            System.out.println("Couldn't load images: " + e);
        }
    }
    /** Whether or not the item that was last configured is selected. */
    protected boolean selected;

    /**
     * This is messaged from JTree whenever it needs to get the size
     * of the component or it wants to draw it.
     * This attempts to set the font based on value, which will be
     * a TreeNode.
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, selected,
                expanded, leaf, row, hasFocus);

        /* Set the text. */
        setText(stringValue);
        /* Tooltips used by the tree. */
        setToolTipText(stringValue);

        /* Set the image. */
        if (expanded) {
            setIcon(expandedIcon);
        } else if (!leaf) {
            setIcon(collapsedIcon);
        } else {
            setIcon(null);
        }

        /* Set the color and the font based on the SampleData userObject. */
        SampleData userObject = (SampleData) ((DefaultMutableTreeNode) value).
                getUserObject();
        if (hasFocus) {
            setForeground(UIManager.getColor("Tree.selectionForeground"));
        } else {
            setForeground(userObject.getColor());
        }
        if (userObject.getFont() == null) {
            setFont(defaultFont);
        } else {
            setFont(userObject.getFont());
        }

        /* Update the selected flag for the next paint. */
        this.selected = selected;

        return this;
    }

    /**
     * paint is subclassed to draw the background correctly.  JLabel
     * currently does not allow backgrounds other than white, and it
     * will also fill behind the icon.  Something that isn't desirable.
     */
    @Override
    public void paint(Graphics g) {
        Color bColor;
        Icon currentI = getIcon();

        if (selected) {
            bColor = SELECTED_BACKGROUND_COLOR;
        } else if (getParent() != null) /* Pick background color up from parent (which will come from
        the JTree we're contained in). */ {
            bColor = getParent().getBackground();
        } else {
            bColor = getBackground();
        }
        g.setColor(bColor);
        if (currentI != null && getText() != null) {
            int offset = (currentI.getIconWidth() + getIconTextGap());

            if (getComponentOrientation().isLeftToRight()) {
                g.fillRect(offset, 0, getWidth() - 1 - offset,
                        getHeight() - 1);
            } else {
                g.fillRect(0, 0, getWidth() - 1 - offset, getHeight() - 1);
            }
        } else {
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        super.paint(g);
    }
}