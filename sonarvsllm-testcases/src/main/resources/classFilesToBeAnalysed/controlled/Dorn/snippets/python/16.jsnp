        else:
            atts['class'] = 'field-name'
        if ( self.settings.field_name_limit
             and len(node.astext()) > self.settings.field_name_limit):
            atts['colspan'] = 2
            self.context.append('</tr>\n<tr><td>&nbsp;</td>')
        else:
            self.context.append('')
        self.body.append(self.starttag(node, 'th', '', align='left', **atts))
    
    def visit_footnote(self, node, CLASS='docutils footnote'):
        self.body.append(self.starttag(node, 'table', CLASS=CLASS,
                                       frame="void", rules="none"))
        self.body.append('<colgroup><col class="label" /><col /></colgroup>\n'
                         '<tbody>\n'
                         '<tr valign="top">')
        self.footnote_backrefs(node)
        
    def visit_line_block(self, node):
        """
        TODO:
        Einrueckungen selbst formatieren, entweder durch geschachtelte Tabellen
        oder indem Sie Zeilenumbrueche durch <nobr> ... </nobr> ("No Break")
        verhindern und die Einrueckungen durch Aneinandersetzen mehrerer
        geschuetzter Leerzeichen ";nbsp" realisieren.
        """
        self.body.append(self.starttag(node, 'div', CLASS='line-block'))

    # def depart_line_block(self, node):
    #     self.body.append('</div>\n')
