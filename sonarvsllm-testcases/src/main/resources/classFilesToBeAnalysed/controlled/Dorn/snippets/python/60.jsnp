from docutils.transforms import universal


class Writer(Component):

    """
    Abstract base class for docutils Writers.

    Each writer module or package must export a subclass also called 'Writer'.
    Each writer must support all standard node types listed in
    `docutils.nodes.node_class_names`.

    The `write()` method is the main entry point.
    """

    component_type = 'writer'
    config_section = 'writers'

    def get_transforms(self):
        return Component.get_transforms(self) + [
            universal.Messages,
            universal.FilterMessages,
            universal.StripClassesAndElements,]

    document = None
    """The document to write (Docutils doctree); set by `write`."""

    output = None
    """Final translated form of `document` (Unicode string for text, binary
    string for other forms); set by `translate`."""
