        strpage = str(page)
        T.append('Page '+ strpage + '\n\n')
        for item in d:
            if (item[1] == 4):
                contents = scribus.getAllText(item[0])
                if (contents in content):
                    contents = 'Duplication, perhaps linked-to frame'
                T.append(item[0]+': '+ contents + '\n\n')
                content.append(contents)
            elif (item[1] == 2):
                imgname = scribus.getImageFile(item[0])
                T.append(item[0]+': ' + imgname + '\n')
        page += 1
        T.append('\n')
    output_file = open(textfile,'w')
    output_file.writelines(T)
    output_file.close()
    endmessage = textfile + ' was created'
    scribus.messageBox("Finished", endmessage,icon=0,button1=1)


if scribus.haveDoc():
    textfile = scribus.fileDialog('Enter name of file to save to', \
                                  filter='Text Files (*.txt);;All Files (*)')
    try:
        if textfile == '':
            raise Exception
        exportText(textfile)
    except Exception, e:
        print e
