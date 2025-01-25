		find.append("\\s*</array>.*");
		value.append("\n\t\t\t");
		
		String match = "(?s)(<key>" + key + "</key>\\s*<array>)(.*?)(</array>)";
		
		setValue(find.toString(),match,value.toString());
	}
	
	private boolean 
	isValuePresent(
		String match )
	
		throws IOException
	{
		String fileContent = getFileContent();
		
		//System.out.println("Searching for:\n" + match);
		return fileContent.matches(match);
	}
	

	/**
	 * 
	 * @param find the regex expression to find if the value is already present
	 * @param match the regex expression that will match for the replace, it needs to capture 3 groups, the 2nd one being replaced by value
	 * @param value the value that replaces the 2nd match group
	 */
	private void 
	setValue(
		String find,
		String match,
		String value)
	
		throws IOException
	{
		String fileContent = getFileContent();
		
		if( !isValuePresent(find)) {
			//System.out.println("Changing " +plistFile);
			fileContent = fileContent.replaceFirst(match, "$1"+value + "$3");
			setFileContent(fileContent);
			touchFile();
		}
	}
	
	private String 
	getFileContent()
		throws IOException
	{
		FileReader fr = null;
