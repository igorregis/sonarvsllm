        try:
            children = [child for child in item.children(recursive=recursive) \
                        if child in self.presentation()]
            children.sort(key=lambda task: task.plannedStartDateTime())
            return children
        except AttributeError:
            return []

    def foreground_color(self, item, depth=0): # pylint: disable-msg=W0613
        return item.foregroundColor(recursive=True)
