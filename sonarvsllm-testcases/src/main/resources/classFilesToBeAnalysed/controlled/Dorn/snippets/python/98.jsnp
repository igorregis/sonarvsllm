QUOTE_START = u""
QUOTE_END = u""

def quote(textobj):
    quoted_re = re.compile('"[^"]*"')
    try:
        text = getText(textobj)
    except WrongFrameTypeError:
        messageBox("quote.py", "Cannot quote text in a non-text frame", ICON_INFORMATION);
        sys.exit(1)
    if len(text) == 0:
        return 0    # We can't very well change anything in an empty frame
    count = 0
    i = 0
    selectText(0, 0, textobj)
    while i < len(text):
        match = quoted_re.match(text[i:])
        if match:
            end = match.end()
            selectText(i, 1, textobj)
            deleteText(textobj)
            insertText(QUOTE_START, i, textobj)
            selectText(i + end - 1, 1, textobj)
            deleteText(textobj)
            insertText(QUOTE_END, i + end - 1, textobj)
            count += 1
            i = i + end
        else:
            i = i + 1
    return count
