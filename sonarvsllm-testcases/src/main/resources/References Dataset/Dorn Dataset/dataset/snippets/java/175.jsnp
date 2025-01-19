import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.EmailAddressServiceBaseImpl;
import com.liferay.portal.service.permission.CommonPermissionUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class EmailAddressServiceImpl extends EmailAddressServiceBaseImpl {

	public EmailAddress addEmailAddress(
			String className, long classPK, String address, int typeId,
			boolean primary)
		throws PortalException, SystemException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return emailAddressLocalService.addEmailAddress(
			getUserId(), className, classPK, address, typeId, primary);
	}

	public void deleteEmailAddress(long emailAddressId)
		throws PortalException, SystemException {

		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), emailAddress.getClassNameId(),
			emailAddress.getClassPK(), ActionKeys.UPDATE);

		emailAddressLocalService.deleteEmailAddress(emailAddressId);
	}

	public EmailAddress getEmailAddress(long emailAddressId)
		throws PortalException, SystemException {

		EmailAddress emailAddress = emailAddressPersistence.findByPrimaryKey(
			emailAddressId);

		CommonPermissionUtil.check(
			getPermissionChecker(), emailAddress.getClassNameId(),
			emailAddress.getClassPK(), ActionKeys.VIEW);

		return emailAddress;
	}
