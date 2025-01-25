            expectedStatus = task.status.late if planned < self.now else task.status.inactive
            self.assertTaskStatus(expectedStatus, plannedStartDateTime=planned,
                                  prerequisites=[prerequisite])
            
    def testMutualPrerequisites(self):
        taskA = task.Task()
        taskB = task.Task(prerequisites=[taskA])
        taskA.addPrerequisites([taskB])
        for eachTask in (taskA, taskB):
