            dx, dy, dw, dh = self.GetDisplayRect()

        w, h = frm.GetSizeTuple()
        w = w if w > self.notificationWidth else self.notificationWidth

        bottom = dy + dh - self.notificationMargin

        for otherFrame, height, tmo in self.displayedFrames:
            bottom -= height + self.notificationMargin
            if bottom - h < 0:
                self.waitingFrames.append((frm, timeout))
                return

        if frm.GetParent():
            x = min(self.GetDisplayRect()[2] - self.notificationMargin - w,
                    dx + dw + self.notificationMargin)
        else:
            x = dx + dw - w - self.notificationMargin

        frm.SetDimensions(x,
                          bottom - h - self.notificationMargin,
                          w, h)
        self.displayedFrames.append((frm, h, timeout))

        frm.Layout()

        AnimatedShow(frm)

    def CheckWaiting(self):
        waiting = self.waitingFrames
        self.waitingFrames = []
        for frm, tmo in waiting:
            self.NotifyFrame(frm, timeout=tmo)

    def Notify(self, title, msg, icon=None, timeout=None):
        """
        Present a new simple notification frame.

        @param title: Notification title
        @param msg: Notification message
        @param timeout: See L{NotifyFrame}.
        """

        frm = NotificationFrame(msg, title, icon=icon)
        self.NotifyFrame(frm, timeout=timeout)

    def HideFrame(self, frm):
        """
        Hide a notification frame.
        """
