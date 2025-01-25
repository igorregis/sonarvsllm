
		String emailMessageAddedSubjectPrefix = getParameter(
			actionRequest, "emailMessageAddedSubjectPrefix");
		String emailMessageAddedBody = getParameter(
			actionRequest, "emailMessageAddedBody");

		if (Validator.isNull(emailMessageAddedSubjectPrefix)) {
			SessionErrors.add(actionRequest, "emailMessageAddedSubjectPrefix");
		}
		else if (Validator.isNull(emailMessageAddedBody)) {
			SessionErrors.add(actionRequest, "emailMessageAddedBody");
		}
	}

	protected void validateEmailMessageUpdated(ActionRequest actionRequest)
		throws Exception {

		String emailMessageUpdatedSubjectPrefix = getParameter(
			actionRequest, "emailMessageUpdatedSubjectPrefix");
		String emailMessageUpdatedBody = getParameter(
			actionRequest, "emailMessageUpdatedBody");

		if (Validator.isNull(emailMessageUpdatedSubjectPrefix)) {
			SessionErrors.add(
				actionRequest, "emailMessageUpdatedSubjectPrefix");
		}
		else if (Validator.isNull(emailMessageUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailMessageUpdatedBody");
		}
	}
