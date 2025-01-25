class PyDateTimeTest(test.TestCase):
    def testReplaceCannotBeEasilyUsedToFindTheLastDayofTheMonth(self):
        testDate = datetime.date(2004, 4, 1) # April 1st, 2004
        try:
            lastDayOfApril = testDate.replace(day=31)
            self.fail('Surprise! datetime.date.replace works as we want!') # pragma: no cover
            self.assertEqual(datetime.date(2004, 4, 30), lastDayOfApril) # pragma: no cover
        except ValueError:
            pass


class DateTimeTest(test.TestCase):
    def testWeekNumber(self):
        self.assertEqual(53, date.DateTime(2005,1,1).weeknumber())
        self.assertEqual(1, date.DateTime(2005,1,3).weeknumber())   
        
    def testStartOfDay(self):
        startOfDay = date.DateTime(2005,1,1,0,0,0,0)
        noonish = date.DateTime(2005,1,1,12,30,15,400)
        self.assertEqual(startOfDay, noonish.startOfDay())
        
    def testEndOfDay(self):
        endOfDay = date.DateTime(2005,1,1,23,59,59,999999)
        noonish = date.DateTime(2005,1,1,12,30,15,400)
        self.assertEqual(endOfDay, noonish.endOfDay())
        
    def testStartOfWeek(self):
        startOfWeek = date.DateTime(2005,3,28,0,0,0,0)
        midweek = date.DateTime(2005,3,31,12,30,15,400)
        self.assertEqual(startOfWeek, midweek.startOfWeek())

    def testEndOfWeek(self):
        endOfWeek = date.DateTime(2005,4,3,23,59,59,999999)
        midweek = date.DateTime(2005,3,31,12,30,15,400)
        self.assertEqual(endOfWeek, midweek.endOfWeek())
        
    def testStartOfWorkWeekOnWednesday(self):
        startOfWorkWeek = date.DateTime(2011,7,25,0,0,0,0)
        wednesday = date.DateTime(2011,7,27,8,39,10)
        self.assertEqual(startOfWorkWeek, wednesday.startOfWorkWeek())
        
    def testStartOfWorkWeekOnMonday(self):
        startOfWorkWeek = date.DateTime(2011,7,25,0,0,0,0)
        monday = date.DateTime(2011,7,25,8,39,10)
        self.assertEqual(startOfWorkWeek, monday.startOfWorkWeek())

    def testStartOfWorkWeekOnSunday(self):
        startOfWorkWeek = date.DateTime(2011,7,18,0,0,0,0)
        sunday = date.DateTime(2011,7,24,8,39,10)
        self.assertEqual(startOfWorkWeek, sunday.startOfWorkWeek())
