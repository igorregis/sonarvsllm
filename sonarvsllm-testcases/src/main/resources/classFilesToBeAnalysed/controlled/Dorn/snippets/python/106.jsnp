                    if not self._mReversed:
                        self._mSeqCounters[self._mModel['precedenceMatrix'][(self._mLastOrder * SAMPLE_SIZE) + order]] += 1
                    else: # reverse the order of the letters in the lookup
                        self._mSeqCounters[self._mModel['precedenceMatrix'][(order * SAMPLE_SIZE) + self._mLastOrder]] += 1
            self._mLastOrder = order

        if self.get_state() == constants.eDetecting:
            if self._mTotalSeqs > SB_ENOUGH_REL_THRESHOLD:
                cf = self.get_confidence()
                if cf > POSITIVE_SHORTCUT_THRESHOLD:
                    if constants._debug:
                        sys.stderr.write('%s confidence = %s, we have a winner\n' % (self._mModel['charsetName'], cf))
                    self._mState = constants.eFoundIt
                elif cf < NEGATIVE_SHORTCUT_THRESHOLD:
                    if constants._debug:
                        sys.stderr.write('%s confidence = %s, below negative shortcut threshhold %s\n' % (self._mModel['charsetName'], cf, NEGATIVE_SHORTCUT_THRESHOLD))
                    self._mState = constants.eNotMe

        return self.get_state()

    def get_confidence(self):
        r = 0.01
        if self._mTotalSeqs > 0:
#            print self._mSeqCounters[POSITIVE_CAT], self._mTotalSeqs, self._mModel['mTypicalPositiveRatio']
            r = (1.0 * self._mSeqCounters[POSITIVE_CAT]) / self._mTotalSeqs / self._mModel['mTypicalPositiveRatio']
#            print r, self._mFreqChar, self._mTotalChar
            r = r * self._mFreqChar / self._mTotalChar
            if r >= 1.0:
                r = 0.99
