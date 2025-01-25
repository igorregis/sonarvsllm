            extraWidth = self.__oldResizeColumnWidth - \
                             self.GetColumnWidth(self.ResizeColumn)
            self.DistributeWidthAcrossColumns(extraWidth)
        self.Bind(wx.EVT_SIZE, self.OnResize)
        wx.CallAfter(self.DoResize)
        event.Skip()
        
    def OnResize(self, event):
        event.Skip()
        if operating_system.isWindows():
            wx.CallAfter(self.DoResize)
        else:
            self.DoResize()

    def DoResize(self):
        if not self:
            return # Avoid a potential PyDeadObject error
        if not self.IsAutoResizing():
            return
        if self.GetSize().height < 32:
            return # Avoid an endless update bug when the height is small.
        if self.GetColumnCount() <= self.ResizeColumn:
            return # Nothing to resize.

        unusedWidth = max(self.AvailableWidth - self.NecessaryWidth, 0)
        resizeColumnWidth = self.ResizeColumnMinWidth + unusedWidth
        self.SetColumnWidth(self.ResizeColumn, resizeColumnWidth)
        
    def DistributeWidthAcrossColumns(self, extraWidth):
        # When the user resizes the ResizeColumn distribute the extra available
        # space across the other columns, or get the extra needed space from
        # the other columns. The other columns are resized proportionally to 
        # their previous width.
        otherColumns = [index for index in range(self.GetColumnCount())
                        if index != self.ResizeColumn]
        totalWidth = float(sum(self.GetColumnWidth(index) for index in 
                               otherColumns))
        for columnIndex in otherColumns:
            thisColumnWidth = self.GetColumnWidth(columnIndex)
            thisColumnWidth += thisColumnWidth / totalWidth * extraWidth
            self.SetColumnWidth(columnIndex, thisColumnWidth)
        
    def GetResizeColumn(self):
        if self._resizeColumn == -1:
            return self.GetColumnCount() - 1
        else:
            return self._resizeColumn
        
    def SetResizeColumn(self, columnIndex):
        self._resizeColumn = columnIndex # pylint: disable-msg=W0201
