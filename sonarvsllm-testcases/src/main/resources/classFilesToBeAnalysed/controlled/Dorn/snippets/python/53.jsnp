            while colordict.has_key(name):# check if color already exists - then add PREFIX to name
                name = PREFIX+name
            
            scribus.defineColor(name, c, m, y, k)
            i=i+1
            scribus.progressSet(i)

def main(argv):
    """Main method for importing colors."""
    if not scribus.haveDoc(): #do we have a doc?
        scribus.messageBox("csv2color", "No document to import colors \n Please open one, first.")
        sys.exit()
    else:
        filename=scribus.fileDialog("csv2color",  "CSV files(*.csv *.CSV *.txt *.TXT)")
        while os.path.isdir(filename):
            filename=scribus.fileDialog("csv2color",  "CSV files(*.csv *.CSV *.txt *.TXT)") #proper filename?
        else:
            try:
                colorlist=getColorsFromCsv(filename)
                messagestring = "You are going to import %i colors \n This may take a while" % len(colorlist)
                answer = scribus.messageBox("csv2color", messagestring, button1=scribus.BUTTON_OK,  button2=scribus.BUTTON_CANCEL)
                if answer != scribus.BUTTON_OK:
                    sys.exit()
                else:
                    importColors(colorlist)
                    scribus.docChanged(True)
                    scribus.messageBox("csv2color", "Colors imported! \n Thank you for using csv2color and Scribus!")
            except:
                scribus.messageBox("csv2color", "Could not import file!", icon=scribus.ICON_WARNING)
                sys.exit()
            


def main_wrapper(argv):
    """The main_wrapper() function disables redrawing, sets a sensible generic
    status bar message, and optionally sets up the progress bar. It then runs
    the main() function. Once everything finishes it cleans up after the main()
    function, making sure everything is sane before the script terminates."""
    try:
        #scribus.statusMessage("Running script...")
        scribus.progressReset()
        main(argv)
    finally:
        # Exit neatly even if the script terminated with an exception,
        # so we leave the progress bar and status bar blank and make sure
        # drawing is enabled.
        if scribus.haveDoc():
            scribus.setRedraw(True)
        scribus.statusMessage("")
        scribus.progressReset()
