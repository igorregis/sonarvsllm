		throws PortalException, SystemException {

		for (PollsQuestion question :
				pollsQuestionPersistence.findByGroupId(groupId)) {

			deleteQuestion(question);
		}
	}

	public PollsQuestion getQuestion(long questionId)
		throws PortalException, SystemException {

		return pollsQuestionPersistence.findByPrimaryKey(questionId);
	}

	public List<PollsQuestion> getQuestions(long groupId)
		throws SystemException {

		return pollsQuestionPersistence.findByGroupId(groupId);
	}

	public List<PollsQuestion> getQuestions(long groupId, int start, int end)
		throws SystemException {

		return pollsQuestionPersistence.findByGroupId(groupId, start, end);
	}

	public int getQuestionsCount(long groupId) throws SystemException {
		return pollsQuestionPersistence.countByGroupId(groupId);
	}
