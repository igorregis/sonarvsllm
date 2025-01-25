		if (name == null) {
			throw new IllegalArgumentException();
		}

		_events.add(new EventImpl(name.getLocalPart(), name, value));
	}

	public void setEvent(String name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		setEvent(new QName(getDefaultNamespace(), name), value);
	}

	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (!_portletRequestImpl.isPortletModeAllowed(portletMode)) {
			throw new PortletModeException(portletMode.toString(), portletMode);
		}

		try {
			_portletMode = PortalUtil.updatePortletMode(
				_portletName, _user, _layout, portletMode,
				_portletRequestImpl.getHttpServletRequest());
