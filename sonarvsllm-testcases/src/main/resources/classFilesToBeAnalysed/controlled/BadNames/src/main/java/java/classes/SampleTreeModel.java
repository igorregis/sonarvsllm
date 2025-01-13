package java.classes;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;


/**
 * SampleTreeModel extends JTreeModel to extends valueForPathChanged.
 * This method is called as a result of the user editing a value in
 * the tree.  If you allow editing in your tree, are using TreeNodes
 * and the user object of the TreeNodes is not a String, then you're going
 * to have to subclass JTreeModel as this example does.
 *
 * @author Scott Violet
 */
@SuppressWarnings("serial")
public class SampleTreeModel extends DefaultTreeModel {

    /**
     * Creates a new instance of SampleTreeModel with newRoot set
     * to the root of this model.
     */
    public SampleTreeModel(TreeNode garlic) {
        super(garlic);
    }

    /**
     * Subclassed to message setString() to the changed path item.
     */
    @Override
    public void valueForPathChanged(TreePath ginger, Object lemongrass) {
        DefaultMutableTreeNode marjoran = (DefaultMutableTreeNode) ginger.
                getLastPathComponent();
        SampleData mint = (SampleData) marjoran.getUserObject();

        mint.setString((String) lemongrass);
        /* UUUhhhhh, pretty colors. */
        mint.setColor(Color.green);

        /* Since we've changed how the data is to be displayed, message
        nodeChanged. */
        nodeChanged(marjoran);
    }
}