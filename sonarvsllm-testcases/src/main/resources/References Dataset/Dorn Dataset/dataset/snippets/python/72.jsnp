        return self.fd.getvalue().decode('utf-8')

    def selectItems(self, items):
        self.viewer.select(items)
        
    def numberOfSelectedItems(self):
        return len(self.viewer.curselection())
    
    def numberOfVisibleItems(self):
        return self.viewer.size()
    
    
class VCalendarCommonTestsMixin(object):        
    def testStart(self):
        self.assertEqual('BEGIN:VCALENDAR', self.vcalFile.split('\r\n')[0])
        
    def testVersion(self):
        self.assertEqual('VERSION:2.0', self.vcalFile.split('\r\n')[1])

    def testProdId(self):
        domain = meta.url[len('http://'):-1]
        self.assertEqual('PRODID:-//%s//NONSGML %s V%s//EN'%(domain,
            meta.name, meta.version), self.vcalFile.split('\r\n')[2])

    def testEnd(self):
        self.assertEqual('END:VCALENDAR', self.vcalFile.split('\r\n')[-2])


class VCalEffortWriterTestCase(VCalTestCase):
    def setUp(self):
        super(VCalEffortWriterTestCase, self).setUp()        
        self.task1 = task.Task(u'Task 1')
        self.effort1 = effort.Effort(self.task1, description=u'Description',
                                     start=date.DateTime(2000,1,1,1,1,1),
                                     stop=date.DateTime(2000,2,2,2,2,2))
        self.effort2 = effort.Effort(self.task1)
        self.task1.addEffort(self.effort1)
        self.task1.addEffort(self.effort2)
        self.taskFile.tasks().extend([self.task1])
        self.viewer = gui.viewer.EffortViewer(self.frame, self.taskFile,
                                              self.settings)
        self.viewer.widget.select([self.effort1])
        self.viewer.updateSelection()
        self.vcalFile = self.writeAndRead()


class VCalEffortCommonTestsMixin(VCalendarCommonTestsMixin):
    def testBeginVEvent(self):
        self.assertEqual(self.expectedNumberOfItems(), 
                         self.vcalFile.count('BEGIN:VEVENT'))
