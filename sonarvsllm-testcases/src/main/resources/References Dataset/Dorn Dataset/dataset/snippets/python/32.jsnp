# Craig Bradney, Scribus Team
# 10/3/08: Added to Scribus 1.3.3.12svn distribution "as was" from Scribus wiki for bug #6826, script is GPLd

import sys


try:
    from scribus import *
    
except ImportError:
    print "This script only runs from within Scribus."
    sys.exit(1)
try:
    from PIL import Image
except ImportError:
    print "Unable to import the Python Imaging Library module."
    sys.exit(1)
    
def main():

    pageX,pageY = getPageSize()
    ImageFileName = fileDialog("Image Import", "*","" ,True, False)
    im = Image.open(ImageFileName)
    xsize, ysize = im.size

    if (pageX < pageY):
        Breite = pageX * 0.8
    else:
        Breite = pageY * 0.8
    Hoehe = Breite * ysize/xsize
