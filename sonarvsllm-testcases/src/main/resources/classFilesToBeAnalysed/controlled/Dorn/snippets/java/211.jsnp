public class ComponentTag extends BaseComponentTag {

	protected void setAttributes(HttpServletRequest request) {
		Map<String, Object> options = getOptions();
		HashMap<String, Object> optionsJSON = new HashMap<String, Object>();

		_proccessAttributes(options, optionsJSON);

		super.setAttributes(request);

		setNamespacedAttribute(request, "options", options);
		setNamespacedAttribute(request, "optionsJSON", optionsJSON);
	}

	private boolean _isEventAttribute(String key) {
		Matcher afterMatcher = _EVENT_AFTER_REGEX.matcher(key);
		Matcher onMatcher = _EVENT_ON_REGEX.matcher(key);

		return (afterMatcher.find() || onMatcher.find());
	}

	private boolean _isValidAttribute(String key) {
		List<String> excludeAttributes = Collections.EMPTY_LIST;

		if (getExcludeAttributes() != null) {
			excludeAttributes = Arrays.asList(
				getExcludeAttributes().split(StringPool.COMMA));
		}

		return !(excludeAttributes.contains(key) ||
			key.equals(_DYNAMIC_ATTRIBUTES));
	}

	private void _proccessAttributes(Map<String, Object> options,
		Map<String, Object> newOptions) {

		Map<String, String> afterEventOptionsMap =
			new HashMap<String, String>();

		Map<String, String> onEventOptionsMap =	new HashMap<String, String>();

		for (String key : options.keySet()) {
			if (!_isValidAttribute(key)) {
				continue;
			}

			Object value = options.get(key);

			String originalKey =
				ReservedAttributeUtil.getOriginalName(getName(), key);
