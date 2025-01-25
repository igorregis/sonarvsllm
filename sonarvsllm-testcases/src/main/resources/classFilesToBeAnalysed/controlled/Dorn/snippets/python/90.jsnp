        mimedata = QtCore.QMimeData()

        # Deduce item name from its CustomType
        mimedata.setText(SYMBOLS[item.type()-1000]['name'])
        iconeSize = self.iconSize()
        icone = item.icon(0)
        drag.setMimeData(mimedata)
        drag.setHotSpot(QtCore.QPoint(iconeSize.width(), iconeSize.height()))
        drag.setPixmap(icone.pixmap(iconeSize))
        drag.start(QtCore.Qt.MoveAction)

    def retranslateItem(self, item):

        # Translate current item
        data = str(item.data(0, QtCore.Qt.UserRole).toString())
        if data:
            item.setText(0, translate('nodesDock', data))

            # Recurse for child-items translation
            childNum = 0
            childCount = item.childCount()
            while childNum < childCount:
                child_item = item.child(childNum)
                self.retranslateItem(child_item)
                childNum += 1

    def retranslateUi(self, MainWindow):

        self.populateNodeDock()
