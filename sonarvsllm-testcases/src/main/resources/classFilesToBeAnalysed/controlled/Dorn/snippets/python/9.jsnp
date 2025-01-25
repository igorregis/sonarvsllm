    def filter(self, items):
        return items
    
    def test(self):
        self.testcalled = 1 # pylint: disable-msg=W0201


class DummyItem(str):
    def ancestors(self):
        return []


class StackedFilterTest(test.TestCase):
    def setUp(self):
        self.list = patterns.ObservableList([DummyItem('a'), DummyItem('b'), 
                                             DummyItem('c'), DummyItem('d')])
        self.filter1 = DummyFilter(self.list)
        self.filter2 = TestFilter(self.filter1)

    def testDelegation(self):
        self.filter2.test()
        self.assertEqual(1, self.filter1.testcalled)
        
    def testSetTreeMode_True(self):
        self.filter2.setTreeMode(True)
        self.failUnless(self.filter1.treeMode())
        
    def testSetTreeMode_False(self):
        self.filter2.setTreeMode(False)
        self.failIf(self.filter1.treeMode())
        

class SearchFilterTest(test.TestCase):
    def setUp(self):
        task.Task.settings = config.Settings(load=False)
        self.parent = task.Task(subject='*ABC', description='Parent description')
        self.child = task.Task(subject='DEF', description='Child description')
        self.parent.addChild(self.child)
        self.list = task.TaskList([self.parent, self.child])
        self.filter = base.SearchFilter(self.list)

    def setSearchString(self, searchString, matchCase=False,
                        includeSubItems=False, searchDescription=False):
        self.filter.setSearchFilter(searchString, matchCase=matchCase, 
                                    includeSubItems=includeSubItems,
                                    searchDescription=searchDescription)
        
    def testNoMatch(self):
        self.setSearchString('XYZ')
        self.assertEqual(0, len(self.filter))
