TABLE_MODE_NONE = 0
TABLE_MODE_HEAD = 1
TABLE_MODE_BODY = 2

class TableSpec:
    def __init__(self):
        self.columnCount = 0
        self.mode = TABLE_MODE_NONE
    def getColumnCount(self): return self.columnCount
    def setColumnCount(self, columnCount): self.columnCount = columnCount
    def getMode(self): return self.mode
    def setMode(self, mode): self.mode = mode


class Writer(writers.Writer):

    supported = ('latex','latex2e')
    """Formats this writer supports."""

    settings_spec = (
        'Documenting Python LaTeX Specific Options',
        'The LaTeX "--output-encoding" default is "latin-1:strict".',
        (('Specify documentclass (one of howto, manual, module).  Default is "howto".',
#        (('Specify documentclass (one of howto, manual).  Default is "howto".',
          ['--documentclass'],
          {'default': 'howto', }),
    ))

    #settings_defaults = {}
    settings_defaults = {'output_encoding': 'latin-1'}
