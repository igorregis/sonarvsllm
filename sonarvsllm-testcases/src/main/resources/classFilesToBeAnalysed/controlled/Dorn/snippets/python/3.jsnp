        self.assertDoUndoRedo(lambda: self.assertEqual([self.category], 
                                                       self.categories))

    def testNewSubCategory(self):
        self.newSubCategory([self.category])
        newSubCategory = self.category.children()[0]
        self.assertDoUndoRedo(lambda: self.assertEqual([newSubCategory], 
                                                       self.category.children()),
            lambda: self.assertEqual([self.category], self.categories))


class DragAndDropCategoryCommandTest(CategoryCommandTestCase):
    def setUp(self):
        super(DragAndDropCategoryCommandTest, self).setUp()
        self.parent = category.Category('parent')
        self.child = category.Category('child')
        self.grandchild = category.Category('grandchild')
        self.parent.addChild(self.child)
        self.child.addChild(self.grandchild)
        self.categories.extend([self.parent, self.child])
    
    def dragAndDrop(self, dropTarget, categories=None):
        command.DragAndDropCategoryCommand(self.categories, categories or [], 
                                       drop=dropTarget).do()
                                       
    def testCannotDropOnParent(self):
        self.dragAndDrop([self.parent], [self.child])
        self.failIf(patterns.CommandHistory().hasHistory())
        
    def testCannotDropOnChild(self):
        self.dragAndDrop([self.child], [self.parent])
        self.failIf(patterns.CommandHistory().hasHistory())
        
    def testCannotDropOnGrandchild(self):
        self.dragAndDrop([self.grandchild], [self.parent])
        self.failIf(patterns.CommandHistory().hasHistory())

    def testDropAsRootTask(self):
        self.dragAndDrop([], [self.grandchild])
        self.assertDoUndoRedo(lambda: self.assertEqual(None, 
            self.grandchild.parent()), lambda:
            self.assertEqual(self.child, self.grandchild.parent()))
        
        
class CopyAndPasteCommandTest(CategoryCommandTestCase):
    def setUp(self):
        super(CopyAndPasteCommandTest, self).setUp()
        self.original = category.Category('original')
        self.categories.append(self.original)
        self.task = task.Task()
