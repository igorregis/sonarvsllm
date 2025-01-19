                
        
class CtrlWithSortableColumnsUnderTest( \
        widgets.itemctrl._CtrlWithSortableColumnsMixin, # pylint: disable-msg=W0212 
        wx.ListCtrl):
    pass


class CtrlWithSortableColumnsTestsMixin(object):
    def assertCurrentSortColumn(self, expectedSortColumn):
        currentSortColumn = self.control._currentSortColumn() # pylint: disable-msg=W0212
        self.assertEqual(expectedSortColumn, currentSortColumn)
        
    def testDefaultSortColumn(self):
        self.assertCurrentSortColumn(self.column1)
        
    def testShowSortColumn(self):
        self.control.showSortColumn(self.column2)
        self.assertCurrentSortColumn(self.column2)


class CtrlWithSortableColumnsTest(CtrlWithColumnsTestCase, CtrlWithSortableColumnsTestsMixin):
    def createControl(self):
        return CtrlWithSortableColumnsUnderTest(self.frame, style=wx.LC_REPORT,
            columns=[self.column1, self.column2])            
        
        
class CtrlWithColumnsUnderTest(widgets.itemctrl.CtrlWithColumnsMixin, 
                               wx.ListCtrl):
    pass
