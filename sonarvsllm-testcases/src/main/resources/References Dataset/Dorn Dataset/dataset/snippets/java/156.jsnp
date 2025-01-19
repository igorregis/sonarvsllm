		throws InstanceNotFoundException, MBeanRegistrationException {

		synchronized (_objectNameCache) {
			ObjectName objectName = _objectNameCache.get(objectNameCacheKey);

			if (objectName == null) {
				_mBeanServer.unregisterMBean(defaultObjectName);
			}
			else {
				_objectNameCache.remove(objectNameCacheKey);
