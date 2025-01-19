        self.assertEqualLists(expected, self.effortList)
        
        
class NoteContainerAssertsMixin(object):
    def assertNoteContainer(self, expected):
        for note in expected:
            self.failUnless(note in self.noteContainer)
        for note in self.noteContainer:
            self.failUnless(note in expected)

                
class EffortAssertsMixin(object):
    def assertEqualEfforts(self, effort1, effort2):
        self.assertEqual(effort1.task(), effort2.task())
        self.assertEqual(effort1.getStart(), effort2.getStart())
        self.assertEqual(effort1.getStop(), effort2.getStop())
        self.assertEqual(effort1.description(), effort2.description())
        
                
class TaskAssertsMixin(object):
    def failUnlessParentAndChild(self, parent, child):
        self.failUnless(child in parent.children())
        self.failUnless(child.parent() == parent)

    def assertTaskCopy(self, orig, copy):
        self.failIf(orig == copy)
        self.assertEqual(orig.subject(), copy.subject())
        self.assertEqual(orig.description(), copy.description())
        self.assertEqual(orig.plannedStartDateTime(), copy.plannedStartDateTime())
        self.assertEqual(orig.dueDateTime(), copy.dueDateTime())
        self.assertEqual(orig.actualStartDateTime(), copy.actualStartDateTime())
        self.assertEqual(orig.completionDateTime(), copy.completionDateTime())
        self.assertEqual(orig.recurrence(), copy.recurrence())
        self.assertEqual(orig.budget(), copy.budget())
        if orig.parent():
            self.failIf(copy in orig.parent().children()) 
        self.failIf(orig.id() == copy.id())
        self.assertEqual(orig.categories(), copy.categories())
        self.assertEqual(orig.priority(), copy.priority())
        self.assertEqual(orig.fixedFee(), copy.fixedFee())
        self.assertEqual(orig.hourlyFee(), copy.hourlyFee())
        self.assertEqual(orig.attachments(), copy.attachments())
        self.assertEqual(orig.reminder(), copy.reminder())
        self.assertEqual(orig.shouldMarkCompletedWhenAllChildrenCompleted(),
                         copy.shouldMarkCompletedWhenAllChildrenCompleted())
        self.assertEqual(len(orig.children()), len(copy.children()))
        for origChild, copyChild in zip(orig.children(), copy.children()):
            self.assertTaskCopy(origChild, copyChild)
        for origEffort, copyEffort in zip(orig.efforts(), copy.efforts()):
            self.assertEffortCopy(origEffort, copyEffort)
