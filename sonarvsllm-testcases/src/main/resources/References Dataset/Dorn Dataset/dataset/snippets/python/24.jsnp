        '''
        if self._size_changes.has_key(SUBMIT):
            return self._er(data, SUBMIT, dtpref_cols, dtpref_rows, REQUEST)
        if data != self.source:
            self.source = data
            self.render()

        if REQUEST:
            message="Saved changes."
            return self.manage_main(self, REQUEST, manage_tabs_message=message)
