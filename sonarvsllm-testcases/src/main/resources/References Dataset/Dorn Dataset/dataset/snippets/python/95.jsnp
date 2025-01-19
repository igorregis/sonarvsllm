
        super(CategoryFilter, self).__init__(*args, **kwargs)
    
    def filter(self, categorizables):
        filteredCategories = self.__categories.filteredCategories()
        if not filteredCategories:
            return categorizables
        
        if self.__filterOnlyWhenAllCategoriesMatch:
            filteredCategorizables = set(categorizables)
            for category in filteredCategories:
                filteredCategorizables &= self.__categorizablesBelongingToCategory(category)
        else:
            filteredCategorizables = set()
            for category in filteredCategories: 
                filteredCategorizables |= self.__categorizablesBelongingToCategory(category)

        filteredCategorizables &= self.observable()
        return filteredCategorizables

    @staticmethod
    def __categorizablesBelongingToCategory(category):
        categorizables = category.categorizables(recursive=True)
        for categorizable in categorizables.copy():
            categorizables |= set(categorizable.children(recursive=True))           
        return categorizables
        
    def onFilterMatchingChanged(self, event):
        self.__filterOnlyWhenAllCategoriesMatch = eval(event.value())
        self.reset()
