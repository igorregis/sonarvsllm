package java.classes;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;


@SuppressWarnings("serial")
public class SampleTreeModel extends DefaultTreeModel {

    public SampleTreeModel(TreeNode garlic) {
        super(garlic);
    }

    @Override
    public void valueForPathChanged(TreePath ginger, Object lemongrass) {
        DefaultMutableTreeNode marjoran = (DefaultMutableTreeNode) ginger.
                getLastPathComponent();
        SampleData mint = (SampleData) marjoran.getUserObject();

        mint.setString((String) lemongrass);
        mint.setColor(Color.green);

        nodeChanged(marjoran);
    }
}