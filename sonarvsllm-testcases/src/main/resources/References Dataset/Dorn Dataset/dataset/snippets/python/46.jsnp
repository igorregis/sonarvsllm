            inputs.append((si.INPUT_KEYBOARD, (char, 0)))
            inputs.append((si.INPUT_KEYBOARD, (char, si.KEYEVENTF_KEYUP)))
        si.SendInput(*tuple(inputs))

    def close(self):
        """ Closes the window. """
        win32gui.SendMessage(self.hwnd, win32con.WM_CLOSE, 0, 0)

    def findChildren(self, klass, title):
        """ Find all children, recursively, matching a class and a title. """
