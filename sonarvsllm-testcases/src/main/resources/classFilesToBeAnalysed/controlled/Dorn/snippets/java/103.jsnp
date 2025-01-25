			DB db = DBFactoryUtil.getDB();

			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			String tablesSQL = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/portal-tables.sql");

			String indexesSQL = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/indexes.sql");

			String indexesProperties = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/indexes.properties");

			db.updateIndexes(
				tablesSQL, indexesSQL, indexesProperties, _dropIndexes);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void upgradeProcess(int buildNumber) throws UpgradeException {
		if (buildNumber == ReleaseInfo.getBuildNumber()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping upgrade process from " + buildNumber + " to " +
						ReleaseInfo.getBuildNumber());
			}

			return;
		}

		String[] upgradeProcessClassNames = getUpgradeProcessClassNames(
			PropsKeys.UPGRADE_PROCESSES);

		if (upgradeProcessClassNames.length == 0) {
			upgradeProcessClassNames = getUpgradeProcessClassNames(
				PropsKeys.UPGRADE_PROCESSES + StringPool.PERIOD + buildNumber);

			if (upgradeProcessClassNames.length == 0) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Upgrading from " + buildNumber + " to " +
							ReleaseInfo.getBuildNumber() + " is not supported");
				}
