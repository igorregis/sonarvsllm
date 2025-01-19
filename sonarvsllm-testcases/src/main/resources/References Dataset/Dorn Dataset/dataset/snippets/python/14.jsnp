    # - - - - - - PDF drawing helpers - - - - - - -
    def _line(self, x1, y1, x2, y2, thick):
        """Draw a line, coordinates given as four decimal numbers"""
        self.drawing.add(Line(
            self._num(x1),  self._num(self.height-y1),
            self._num(x2),  self._num(self.height-y2),
            strokeColor=self._color(self.foreground),
            strokeWidth=self.line_width*(1+0.5*bool(thick))
        ))

    def _rectangle(self, x1, y1, x2, y2, style=''):
        """Draw a rectangle, coordinates given as four decimal numbers."""
        if x1 > x2: x1, x2 = x2, x1
        if y1 > y2: y1, y2 = y2, y1
        self.drawing.add(Rect(
            self._num(x1),  self._num(self.height-y2),
            self._num(x2-x1),  self._num(y2-y1),
            fillColor=self._color(self.fillcolor),
            strokeWidth=self.line_width
        ))

    # - - - - - - visitor function for the different shape types - - - - - - -

    def visit_point(self, point):
        self.drawing.add(Circle(
            self._num(point.x),  self._num(self.height-point.y),
            self._num(0.2),
            fillColor=self._color(self.foreground),
            strokeWidth=self.line_width
        ))
