		calendarImpl.setCalendarId(getCalendarId());
		calendarImpl.setGroupId(getGroupId());
		calendarImpl.setCompanyId(getCompanyId());
		calendarImpl.setUserId(getUserId());
		calendarImpl.setUserName(getUserName());
		calendarImpl.setCreateDate(getCreateDate());
		calendarImpl.setModifiedDate(getModifiedDate());
		calendarImpl.setResourceBlockId(getResourceBlockId());
		calendarImpl.setCalendarResourceId(getCalendarResourceId());
		calendarImpl.setName(getName());
		calendarImpl.setDescription(getDescription());
		calendarImpl.setColor(getColor());
		calendarImpl.setDefaultCalendar(getDefaultCalendar());

		calendarImpl.resetOriginalValues();

		return calendarImpl;
	}

	public int compareTo(Calendar calendar) {
		int value = 0;

		value = getName().compareTo(calendar.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}
