
		Date createDate = getCreateDate();

		if (createDate != null) {
			passwordPolicyCacheModel.createDate = createDate.getTime();
		}
		else {
			passwordPolicyCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			passwordPolicyCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			passwordPolicyCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		passwordPolicyCacheModel.defaultPolicy = getDefaultPolicy();

		passwordPolicyCacheModel.name = getName();

		String name = passwordPolicyCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			passwordPolicyCacheModel.name = null;
		}

		passwordPolicyCacheModel.description = getDescription();
