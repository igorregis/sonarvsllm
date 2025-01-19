        self.list.append(self.task)
        self.filter.hideTaskStatus(task.status.late)
        self.assertFilterIsEmpty()

    def testFilterDueSoonTask(self):
        self.task.setDueDateTime(date.Now() + date.oneHour)
        self.list.append(self.task)
        self.filter.hideTaskStatus(task.status.duesoon)
        self.assertFilterIsEmpty()
 
    def testFilterOverDueTask(self):
        self.task.setDueDateTime(date.Now() - date.oneHour)
        self.list.append(self.task)
        self.filter.hideTaskStatus(task.status.overdue)
        self.assertFilterIsEmpty()
        
    def testFilterOverDueTaskWithActiveChild(self):
        self.child.setActualStartDateTime(date.Now())
        self.task.setDueDateTime(date.Now() - date.oneHour)
        self.task.addChild(self.child)
        self.list.append(self.task)
        self.filter.hideTaskStatus(task.status.overdue)
        if self.treeMode:
            self.assertFilterShows(self.task, self.child)
        else:
            self.assertFilterShows(self.child)


class ViewFilterInListModeTest(ViewFilterTestsMixin, ViewFilterTestCase):
    treeMode = False
