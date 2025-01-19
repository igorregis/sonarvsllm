
import sys

try:
    from scribus import *
except ImportError:
    print "This script only runs from within Scribus."
    sys.exit(1)

import re

TITLE = "Word count"

def wordsplit(text):
    word_pattern = "([A-Za-zäöüÄÖÜß]+)"
    words = []
    for x in re.split(word_pattern, text):
        if re.match(word_pattern, x):
            words.append(x)
    return words


def main():
    words = 0
    sel_count = selectionCount()
    if sel_count:
        source = "selected textframe"
        if sel_count > 1: source += "s" #plural
        for i in range(sel_count):
            try:
                text = getText(getSelectedObject(i))
                words += len(wordsplit(text))
            except WrongFrameTypeError:
                if sel_count == 1:
                    # If there's only one object selected, display a message
                    messageBox(TITLE, "Can't count words in a non-text frame", ICON_INFORMATION);
                    sys.exit(1)
                else:
                    # otherwise ignore
                    pass
    else:
        source = "whole document"
        for page in range(1,pageCount() + 1):
            gotoPage(page)
            for obj in getAllObjects():
                try:
                    text = getText(obj)
                    words += len(wordsplit(text))
                except WrongFrameTypeError:
                    pass # ignore the error, it just wasn't a frame we can count
