    def testMarkLastGrandChildCompletedMakesParentRecur(self):
        self.parent.setRecurrence(date.Recurrence('weekly'))
        self.child.addChild(self.grandchild)
        self.grandchild.setParent(self.child)
        self.grandchild.setCompletionDateTime(self.now)
        expectedPlannedStartDateTime = self.now
        if self.shouldMarkCompletedWhenAllChildrenCompleted(self.parent):
            expectedPlannedStartDateTime += date.TimeDelta(days=7)
        self.assertAlmostEqual(expectedPlannedStartDateTime.toordinal(), 
                               self.parent.plannedStartDateTime().toordinal())
