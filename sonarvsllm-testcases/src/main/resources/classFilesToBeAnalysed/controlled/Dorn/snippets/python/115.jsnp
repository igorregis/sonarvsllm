    def getFirstNewItem(self):
        self.callback.pulse()

        self.newObjectsListCopy = self.newObjectsList[:]
        return self._getItem(self.newObjectsListCopy)

    def getNextNewItem(self):
        self.callback.pulse()

        return self._getItem(self.newObjectsListCopy)

    def getFirstUpdatedItem(self):
        self.callback.pulse()

        self.changedObjectsListCopy = self.changedObjectsList[:]
        return self._getItem(self.changedObjectsListCopy)

    def getNextUpdatedItem(self):
        self.callback.pulse()

        return self._getItem(self.changedObjectsListCopy)

    def getFirstDeletedItem(self):
        self.callback.pulse()

        self.deletedObjectsListCopy = self.deletedObjectsList[:]
        return self._getItem(self.deletedObjectsListCopy)

    def getNextDeletedItem(self):
        self.callback.pulse()

        return self._getItem(self.deletedObjectsListCopy)

    def addItem(self, item):
        self.callback.onAddItem()

        obj = self._parseObject(item)
        self.objectList.append(obj)
        item.key = obj.id().encode('UTF-8')

        return self.doAddItem(obj)

    def doAddItem(self, obj):
        """Called after a domain object has been added; use this to
        set up the object if it needs to."""

        return 201

    def updateItem(self, item):
        self.callback.onUpdateItem()
