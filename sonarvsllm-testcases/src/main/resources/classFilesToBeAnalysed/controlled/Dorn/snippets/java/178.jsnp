   			return true;
   		}
   	}
      return false;
   }

	@Test
   public void testGetValueAt()
   {
   	for (int rowIdx = 0; rowIdx < classUnderTest.getRowCount(); rowIdx++) {
   		for (int colIdx = 0; colIdx < classUnderTest.getColumnCount(); colIdx++) {
   			assertNotNull(classUnderTest.getValueAt(rowIdx, colIdx));
   		}
   	}
   }

	@Test
   public void testGetValueAt_InvalidColumn()
   {
		try {
			classUnderTest.getValueAt(0, classUnderTest.getColumnCount());
			fail("Expected an exception for call to getValue with a column that is one higher that the " +
					"highest column index allowed by this table model");
		} catch (IndexOutOfBoundsException e) {
			// This is acceptable
		} catch (IllegalArgumentException e) {
			// This is acceptable
		}
		
   }
