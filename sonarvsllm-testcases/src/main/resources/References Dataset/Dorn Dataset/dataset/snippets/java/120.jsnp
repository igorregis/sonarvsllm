	public void testCreate() throws Exception {
		long pk = nextLong();

		SCProductVersion scProductVersion = _persistence.create(pk);

		assertNotNull(scProductVersion);

		assertEquals(scProductVersion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		_persistence.remove(newSCProductVersion);

		SCProductVersion existingSCProductVersion = _persistence.fetchByPrimaryKey(newSCProductVersion.getPrimaryKey());

		assertNull(existingSCProductVersion);
	}

	public void testUpdateNew() throws Exception {
		addSCProductVersion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SCProductVersion newSCProductVersion = _persistence.create(pk);

		newSCProductVersion.setCompanyId(nextLong());

		newSCProductVersion.setUserId(nextLong());

		newSCProductVersion.setUserName(randomString());

		newSCProductVersion.setCreateDate(nextDate());

		newSCProductVersion.setModifiedDate(nextDate());

		newSCProductVersion.setProductEntryId(nextLong());

		newSCProductVersion.setVersion(randomString());

		newSCProductVersion.setChangeLog(randomString());

		newSCProductVersion.setDownloadPageURL(randomString());

		newSCProductVersion.setDirectDownloadURL(randomString());

		newSCProductVersion.setRepoStoreArtifact(randomBoolean());
