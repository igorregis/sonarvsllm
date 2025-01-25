        self.assertEqual(None, adate.weeknumber())
        self.assertEqual('', str(adate))

    def testCreateInfiniteDateWithMaxValues(self):
        maxDate = datetime.date.max
        infinite = date.Date(maxDate.year, maxDate.month, maxDate.day)
        self.failUnless(infinite is date.Date())

    def testInfiniteDateIsSingleton(self):
        self.failUnless(date.Date() is date.Date())
