 */
public class AuthTokenWrapper implements AuthToken {

	public AuthTokenWrapper(AuthToken authToken) {
		_authToken = authToken;
		_originalAuthToken = authToken;
	}

	public void check(HttpServletRequest request) throws PortalException {
		_authToken.check(request);
	}

	public String getToken(HttpServletRequest request) {
		return _authToken.getToken(request);
	}

	public String getToken(
		HttpServletRequest request, long plid, String portletId) {

		return _authToken.getToken(request, plid, portletId);
	}

	public void setAuthToken(AuthToken authToken) {
		if (authToken == null) {
			_authToken = _originalAuthToken;
		}
		else {
			_authToken = authToken;
		}
	}
