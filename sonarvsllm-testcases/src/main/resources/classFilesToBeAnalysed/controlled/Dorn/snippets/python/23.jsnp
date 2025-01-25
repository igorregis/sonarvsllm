        self.note.addChild(self.child)
        self.note.removeChild(self.child)
        self.assertEqual([], self.note.children())
        
    def testAddChildNotification(self):
        patterns.Publisher().registerObserver(self.onEvent, 
            note.Note.addChildEventType())
        self.note.addChild(self.child)
        self.assertEqual(patterns.Event(note.Note.addChildEventType(), 
            self.note, self.child), self.events[0])
