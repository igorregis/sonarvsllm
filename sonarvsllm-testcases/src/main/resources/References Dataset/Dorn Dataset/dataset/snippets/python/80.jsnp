    # citations
    # ---------
    # Use definition list instead of table for bibliographic references.
    # Join adjacent citation entries.

    def visit_citation(self, node):
        if self.body[-1] == '<-- next citation -->':
            del(self.body[-1])
        else:
            self.body.append('<dl class="citation">')
        self.context.append(self.starttag(node, 'dd'))
        self.footnote_backrefs(node)

    def depart_citation(self, node):
        self.body.append('</dd>\n')
        if isinstance(node.next_node(), nodes.citation):
            self.body.append('<-- next citation -->')
        else:
            self.body.append('</dl>\n')

    # docinfo
    # -------
    # use definition list instead of table

    def visit_docinfo(self, node):
        classes = 'docinfo'
        if (self.is_compactable(node)):
            classes += ' simple'
        self.body.append(self.starttag(node, 'dl', CLASS=classes))

    def depart_docinfo(self, node):
        self.body.append('</dl>\n')

    def visit_docinfo_item(self, node, name, meta=1):
        if meta:
            meta_tag = '<meta name="%s" content="%s" />\n' \
                       % (name, self.attval(node.astext()))
            self.add_meta(meta_tag)
        self.body.append('<dt class="%s">%s</dt>\n'
                         % (name, self.language.labels[name]))
        self.body.append(self.starttag(node, 'dd', '', CLASS=name))

    def depart_docinfo_item(self):
        self.body.append('</dd>\n')


    # enumerated lists
    # ----------------
    # The 'start' attribute does not conform to HTML4/XHTML1 Strict
    # (it will resurface in HTML5)
