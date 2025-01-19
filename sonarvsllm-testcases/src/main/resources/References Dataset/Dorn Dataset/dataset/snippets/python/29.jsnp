#!/usr/bin/env python
# -*- coding: utf-8 -*-

import wx

wxSCHEDULER_VERSION = '1.3'
	
wxSCHEDULER_DAILY	 = 1
wxSCHEDULER_WEEKLY	 = 2
wxSCHEDULER_MONTHLY	 = 3
wxSCHEDULER_TODAY	 = 4
wxSCHEDULER_TO_DAY	 = 5
wxSCHEDULER_PREV	 = 6
wxSCHEDULER_NEXT	 = 7
wxSCHEDULER_PREVIEW	 = 8

wxSCHEDULER_WEEKSTART_MONDAY = 1
wxSCHEDULER_WEEKSTART_SUNDAY = 0

# Not actually a constant :)
def SCHEDULER_BACKGROUND_BRUSH():
    _bg = wx.SystemSettings.GetColour( wx.SYS_COLOUR_WINDOW )
    _r, _g, _b = _bg.Red(), _bg.Green(), _bg.Blue()
    return wx.Color( max(0, _r - 15), max(0, _g - 15), max(0, _b - 15) )  
def DAY_BACKGROUND_BRUSH():
    _bg = wx.SystemSettings.GetColour( wx.SYS_COLOUR_WINDOW )
    _r, _g, _b = _bg.Red(), _bg.Green(), _bg.Blue()
    return wx.Color( _r, _g, _b )
FOREGROUND_PEN				= wx.LIGHT_GREY_PEN
