        if hasattr(self, handler):
            getattr(self, handler)(name)

    def CharacterDataHandler(self, data):
        handler = self.elements[-1] + '_data'
        if hasattr(self, handler):
            getattr(self, handler)(data)

    def character_start(self, name, attributes):
        self.charid = attributes['id']
