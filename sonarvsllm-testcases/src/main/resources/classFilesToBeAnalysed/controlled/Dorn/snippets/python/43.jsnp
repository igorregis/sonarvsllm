
    def testAddCompositeWithParentTriggersNotificationByParent(self):
        self.registerObserver(self.composite.addChildEventType())
        self.collection.append(self.composite)
        self.composite2.setParent(self.composite)
        self.collection.append(self.composite2)
        expectedEvent = patterns.Event(self.composite.addChildEventType(),
                                       self.composite, self.composite2)
        self.assertEqual([expectedEvent], self.events)
    
    def testRemoveChildFromCollectionRemovesChildFromParent(self):
        self.collection.extend([self.composite, self.composite2])
        self.composite.addChild(self.composite2)
        self.collection.remove(self.composite2)
        self.failIf(self.composite.children())
        
    def testRemoveChildFromCollectionTriggersNotificationByParent(self):
        self.registerObserver(self.composite.removeChildEventType())
        self.collection.extend([self.composite, self.composite2])
        self.composite.addChild(self.composite2)
        self.collection.remove(self.composite2)
        expectedEvent = patterns.Event(self.composite.removeChildEventType(),
                                       self.composite, self.composite2)
        self.assertEqual([expectedEvent], self.events)

    def testRemoveCompositeWithChildRemovesChildToo(self):
        self.composite.addChild(self.composite2)
        grandChild = patterns.ObservableComposite()
        self.composite2.addChild(grandChild)
        self.collection.append(self.composite)
        self.collection.remove(self.composite2)
        self.assertEqual([self.composite], self.collection)

    def testRemoveCompositeAndChildRemovesBoth(self):
        self.composite.addChild(self.composite2)
        grandChild = patterns.ObservableComposite()
        self.composite2.addChild(grandChild)
        self.collection.append(self.composite)
        self.collection.removeItems([self.composite2, grandChild])
        self.assertEqual([self.composite], self.collection)
        
    def testRemoveChildWithChildren_CollectionNotificationContainsParentAndChild(self):
        self.registerObserver(self.collection.removeItemEventType())
        self.composite.addChild(self.composite2)
        grandChild = patterns.ObservableComposite()
        self.composite2.addChild(grandChild)
        self.collection.append(self.composite)
        self.collection.remove(self.composite2)
        self.assertEqualLists([self.composite2, grandChild],
                              self.events[0].values(type=self.collection.removeItemEventType()))
