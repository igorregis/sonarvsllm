# characters.

def escape(s):
    """Return a string with Texinfo command characters escaped."""
    s = s.replace('@', '@@')
    s = s.replace('{', '@{')
    s = s.replace('}', '@}')
    # Prevent "--" from being converted to an "em dash"
    # s = s.replace('-', '@w{-}')
    return s

def escape_arg(s):
    """Return an escaped string suitable for use as an argument
    to a Texinfo command."""
    s = escape(s)
    # Commas are the argument delimeters
    s = s.replace(',', '@comma{}')
    # Normalize white space
    s = ' '.join(s.split()).strip()
    return s

def escape_id(s):
    """Return an escaped string suitable for node names, menu entries,
    and xrefs anchors."""
    bad_chars = ',:.()@{}'
    for bc in bad_chars:
        s = s.replace(bc, ' ')
    s = ' '.join(s.split()).strip()
    return s

