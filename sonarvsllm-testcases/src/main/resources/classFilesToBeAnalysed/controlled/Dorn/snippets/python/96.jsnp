        self.__timer = wx.Timer(self)
        self.Bind(wx.EVT_TIMER, self.onUpdateStatus, self.__timer)
        pub.subscribe(self.onViewerStatusChanged, 'viewer.status')
        self.scheduledStatusDisplay = None
        self.onViewerStatusChanged()
        self.wxEventTypes = (wx.EVT_MENU_HIGHLIGHT_ALL, wx.EVT_TOOL_ENTER)
        for eventType in self.wxEventTypes:
            parent.Bind(eventType, self.resetStatusBar)

    def resetStatusBar(self, event):
        ''' Unfortunately, the menu's and toolbar don't restore the
            previous statusbar text after they have displayed their help
            text, so we have to do it by hand. '''
        try:
            toolOrMenuId = event.GetSelection() # for CommandEvent from the Toolbar
        except AttributeError:
            toolOrMenuId = event.GetMenuId() # for MenuEvent
        if toolOrMenuId == -1:
            self._displayStatus()
        event.Skip()

    def onViewerStatusChanged(self):
        # Give viewer a chance to update first and only update when the viewer
        # hasn't changed status for 0.5 seconds.
        self.__timer.Start(500, oneShot=True)
              
    def onUpdateStatus(self, event): # pylint: disable-msg=W0613
        if self.__timer:
            self.__timer.Stop()
        self._displayStatus()
