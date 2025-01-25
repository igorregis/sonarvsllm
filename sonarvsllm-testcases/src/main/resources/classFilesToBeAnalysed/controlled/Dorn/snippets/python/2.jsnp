    dlg.exec_()



def show_current_error(title=None):
    """
    Call this function to show the current error.
    It can be used inside an except-block.
    """
    dlg = ExceptHookDialog(sys.exc_type, sys.exc_value, sys.exc_traceback, title)
    dlg.show()
    dlg.exec_()


def install():
    "activates the error handler"
    sys.excepthook = on_error


    
def uninstall():
    "removes the error handler"
    sys.excepthook = sys.__excepthook__

atexit.register(uninstall)


class ExceptHookDialog(QDialog):


    def __init__(self, exc_type, exc_obj, exc_tb, title=None):
        QDialog.__init__(self)
        self.ui = Ui_ExceptHookDialog()
        self.ui.setupUi(self)
        if title:
            self.setWindowTitle(self.windowTitle() + ": " + title)
        self.ui.detailsButton.setCheckable(True)
        self.setExtension(self.ui.tracebackBrowser)
        self.setOrientation(Qt.Vertical)
        msg = "%s: %s" % (exc_type.__name__, exc_obj)
        self.ui.exceptionLabel.setText(msg)
        html = cgitb.html((exc_type, exc_obj, exc_tb))
        self.ui.tracebackBrowser.setText(html)
        self.resize(self.sizeHint())


    @pyqtSignature("")
    def on_closeButton_clicked(self):
        self.close()

