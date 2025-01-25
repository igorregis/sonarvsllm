import mikro


class MenuHooks(object):
    """
    This class lets extension-scripts hook into the main menu of Scribus.    
    """


    def __init__(self, window=None):
        self.window = window or ScripterNG.dialogs.mainWindow.qt
        self.menubar = self.window.menuBar()
        self.menus = []


    def createMenu(self, title):
        m = QMenu(title)
        self.menus.append(m)
        self.menubar.addAction(m)
        return m

        
    def iter_menus(self):
        for action in self.menubar.actions():
            menu = action.menu()
            if menu:
                yield menu


    def findMenu(self, title):
        """
        find a menu with a given title

        @type  title: string
        @param title: English title of the menu
        @rtype:       QMenu
        @return:      None if no menu was found, else the menu with title
        """
        # See also http://www.riverbankcomputing.co.uk/static/Docs/PyQt4/pyqt4ref.html#differences-between-pyqt-and-qt
        title = QApplication.translate(mikro.classname(self.window), title) 
        for menu in self.iter_menus():
            if menu.title() == title:
                return menu


    def actionForMenu(self, menu):
        for action in self.menubar.actions():
            if action.menu() == menu:
                return action

