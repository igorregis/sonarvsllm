
		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);
		Map<String, Fields> fieldsMap = getFieldsMap(
			serviceContext, fileEntryTypeId);

		DLFileEntry dlFileEntry = dlFileEntryLocalService.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, fileEntryTypeId, fieldsMap, null, is, size,
			serviceContext);
