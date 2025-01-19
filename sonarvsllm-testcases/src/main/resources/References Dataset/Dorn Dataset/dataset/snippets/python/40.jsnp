                               close_button_width, tab_height)

            # Indent the button if it is pressed down:
            rect = IndentPressedBitmap(rect, close_button_state)
            dc.DrawBitmap(bmp, rect.x, rect.y, True)

            out_button_rect = rect        

        out_tab_rect = wx.Rect(tab_x, tab_y, tab_width, tab_height)
        dc.DestroyClippingRegion()

        return out_tab_rect, out_button_rect, x_extent


class FF2TabArt(AuiDefaultTabArt):
    """ A class to draw tabs using the Firefox 2 (FF2) style. """

    def __init__(self):
        """ Default class constructor. """

        AuiDefaultTabArt.__init__(self)


    def Clone(self):
        """ Clones the art object. """

        art = type(self)()
        art.SetNormalFont(self.GetNormalFont())
        art.SetSelectedFont(self.GetSelectedFont())
        art.SetMeasuringFont(self.GetMeasuringFont())
