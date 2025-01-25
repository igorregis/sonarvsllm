        return newKwargs

    def hasSameArgs(self, *argNames):
        '''Returns true if self has all the message arguments given, no
        more and no less. Order does not matter. So if getArgs()
        returns ('arg1', 'arg2') then self.hasSameArgs('arg2', 'arg1')
        will return true. '''
        return set(argNames) == set( self.getArgs() )

    def hasParent(self, argsInfo):
        '''return True if self has argsInfo object as parent'''
        return self.parentAI() is argsInfo

    def getCompleteAI(self):
        '''Get the closest arg spec, starting from self and moving to parent,
        that is complete. So if self.isComplete() is True, then returns self,
        otherwise returns parent (if parent.isComplete()), etc. '''
        AI = self
        while AI is not None:
            if AI.isComplete():
                return AI
            AI = AI.parentAI() # dereference weakref
        return None

    def updateAllArgsFinal(self, topicDefn):
        '''This can only be called once, if the construction was done
        with ArgSpecGiven.SPEC_GIVEN_NONE'''
        assert not self.isComplete()
        assert topicDefn.isComplete()
        self.__setAllArgs(topicDefn)
