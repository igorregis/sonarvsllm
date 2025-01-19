		return _dataType;
	}

	public int getPrecision()
	{
		return _precision;
	}

	public String getLiteralPrefix()
	{
		return _literalPrefix;
	}

	public String getLiteralSuffix()
	{
		return _literalSuffix;
	}

	public String getCreateParams()
	{
		return _createParams;
	}

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (_autoIncrement ? 1231 : 1237);
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DataTypeInfo other = (DataTypeInfo) obj;
        if (!getSimpleName().equals(other.getSimpleName()))
            return false;
        return true;
    }
