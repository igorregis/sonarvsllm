		StringBundler sb = new StringBundler(17);

		sb.append("{resourcePermissionId=");
		sb.append(resourcePermissionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", scope=");
		sb.append(scope);
		sb.append(", primKey=");
		sb.append(primKey);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", actionIds=");
		sb.append(actionIds);
		sb.append("}");

		return sb.toString();
	}

	public ResourcePermission toEntityModel() {
		ResourcePermissionImpl resourcePermissionImpl = new ResourcePermissionImpl();

		resourcePermissionImpl.setResourcePermissionId(resourcePermissionId);
		resourcePermissionImpl.setCompanyId(companyId);

		if (name == null) {
			resourcePermissionImpl.setName(StringPool.BLANK);
		}
		else {
			resourcePermissionImpl.setName(name);
		}

		resourcePermissionImpl.setScope(scope);

		if (primKey == null) {
			resourcePermissionImpl.setPrimKey(StringPool.BLANK);
		}
		else {
			resourcePermissionImpl.setPrimKey(primKey);
		}

		resourcePermissionImpl.setRoleId(roleId);
		resourcePermissionImpl.setOwnerId(ownerId);
		resourcePermissionImpl.setActionIds(actionIds);

		resourcePermissionImpl.resetOriginalValues();
