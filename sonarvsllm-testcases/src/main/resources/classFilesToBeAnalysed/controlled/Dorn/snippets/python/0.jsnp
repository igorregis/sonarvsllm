        gsz.AddGrowableCol(1)
        self.interior.SetSizer(gsz)
        gsz.Layout()
        
    def findFieldName(self, fieldName, fieldNames):
        def fieldNameIndex(fieldName, fieldNames):
            return fieldNames.index(fieldName) if fieldName in fieldNames else 0
        
        index = fieldNameIndex(fieldName, fieldNames)
        return index if index else fieldNameIndex(fieldName[:6], [fieldName[:6] for fieldName in fieldNames])
        
    def CanGoNext(self):
        wrongFields = []
        countNotNone = 0

        for index, (fieldName, canMultiple) in enumerate(self.fields):
            count = 0
            for choice in self.choices:
                if choice.GetSelection() == index:
                    count += 1
                if choice.GetSelection() != 0:
                    countNotNone += 1
            if count > 1 and not canMultiple:
                wrongFields.append(fieldName)

        if countNotNone == 0:
            return False, _('No field mapping.')

        if len(wrongFields) == 1:
            return False, _('The "%s" field cannot be selected several times.') % wrongFields[0]
