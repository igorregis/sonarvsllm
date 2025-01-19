	public String get(Object key) {
		String value = null;

		if (key != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();

			Locale locale = facesContext.getViewRoot().getLocale();

			if (locale == null) {
				locale = facesContext.getApplication().getDefaultLocale();
			}

			value = LanguageUtil.get(locale, key.toString());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"{locale=" + locale + ", key=" + key + ", value=" + value);
			}
		}

		return value;
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}
