
    def testDurationForSingleEffort(self):
        self.task.addEffort(self.effort1)
        self.assertEqual(self.effort1.duration(), self.composite.duration())

    def testAddEffortOutsidePeriodToTask(self):
        effortOutsidePeriod = effort.Effort(self.task, 
            date.DateTime(2004,1,11,13,0,0), date.DateTime(2004,1,11,14,0,0))
        self.task.addEffort(effortOutsidePeriod)
        self.assertEqual(date.TimeDelta(), self.composite.duration())
