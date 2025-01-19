    def GetActiveChild(self):

        return self._pActiveChild


    def SetActiveChild(self, pChildFrame):

        self._pActiveChild = pChildFrame


    def GetClientWindow(self):

        return self._pClientWindow


    def OnCreateClient(self):

        return AuiMDIClientWindow(self)


    def ActivateNext(self):

        if self._pClientWindow and self._pClientWindow.GetSelection() != wx.NOT_FOUND:
        
            active = self._pClientWindow.GetSelection() + 1
            if active >= self._pClientWindow.GetPageCount():
                active = 0

            self._pClientWindow.SetSelection(active)
        

    def ActivatePrevious(self):

        if self._pClientWindow and self._pClientWindow.GetSelection() != wx.NOT_FOUND:
        
            active = self._pClientWindow.GetSelection() - 1
            if active < 0:
                active = self._pClientWindow.GetPageCount() - 1

            self._pClientWindow.SetSelection(active)
    

    def Init(self):

        self._pLastEvt = None

        self._pClientWindow = None
        self._pActiveChild = None
        self._pWindowMenu = None
        self._pMyMenuBar = None
