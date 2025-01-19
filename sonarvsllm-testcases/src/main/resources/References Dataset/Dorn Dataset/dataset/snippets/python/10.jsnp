    # Convenience methods:

    def assert_has_content(self):
        """
        Throw an ERROR-level DirectiveError if the directive doesn't
        have contents.
        """
        if not self.content:
            raise self.error('Content block expected for the "%s" directive; '
                             'none found.' % self.name)

    def add_name(self, node):
        """Append self.options['name'] to node['names'] if it exists.

        Also normalize the name string and register it as explicit target.
        """
        if 'name' in self.options:
            name = nodes.fully_normalize_name(self.options.pop('name'))
            if 'name' in node:
                del(node['name'])
            node['names'].append(name)
            self.state.document.note_explicit_target(node, node)


def convert_directive_function(directive_fn):
    """
    Define & return a directive class generated from `directive_fn`.

    `directive_fn` uses the old-style, functional interface.
    """
