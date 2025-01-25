			"8", "com.liferay.portlet.calendar",
			new String[] {"ADD_EVENT", "EXPORT_ALL_EVENTS"});

		updatePortletPermissions(
			"20", "com.liferay.portlet.documentlibrary",
			new String[] {"ADD_FOLDER"});

		updatePortletPermissions(
			"31", "com.liferay.portlet.imagegallery",
			new String[] {"ADD_FOLDER"});

		updatePortletPermissions(
			"15", "com.liferay.portlet.journal",
			new String[] {
				"ADD_ARTICLE", "ADD_FEED", "ADD_STRUCTURE", "ADD_TEMPLATE",
				"APPROVE_ARTICLE"
			});

		updatePortletPermissions(
			"19", "com.liferay.portlet.messageboards",
			new String[] {"ADD_CATEGORY", "BAN_USER"});

		updatePortletPermissions(
			"25", "com.liferay.portlet.polls", new String[] {"ADD_QUESTION"});

		updatePortletPermissions(
			"34", "com.liferay.portlet.shopping",
			new String[] {"ADD_CATEGORY", "MANAGE_COUPONS", "MANAGE_ORDERS"});

		updatePortletPermissions(
			"98", "com.liferay.portlet.softwarecatalog",
			new String[] {"ADD_FRAMEWORK_VERSION", "ADD_PRODUCT_ENTRY"});

		updatePortletPermissions(
			"99", "com.liferay.portlet.tags",
			new String[] {"ADD_ENTRY", "ADD_VOCABULARY"});

		updatePortletPermissions(
			"36", "com.liferay.portlet.wiki", new String[] {"ADD_NODE"});
	}

	protected Object[] getLayout(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);
