		return (Address)message.get(_ADDRESS);
	}

	public static ClusterLink getClusterLink() {
		if ((_clusterLink == null) || !_clusterLink.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterLinkUtil has not been initialized");
			}

			return null;
		}

		return _clusterLink;
	}

	public static List<Address> getLocalTransportAddresses() {
		if ((_clusterLink == null) || !_clusterLink.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterLinkUtil has not been initialized");
			}

			return Collections.emptyList();
		}

		return _clusterLink.getLocalTransportAddresses();
	}

	public static List<Address> getTransportAddresses(Priority priority) {
		if ((_clusterLink == null) || !_clusterLink.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterLinkUtil has not been initialized");
			}

			return Collections.emptyList();
		}

		return _clusterLink.getTransportAddresses(priority);
	}

	public static boolean isForwardMessage(Message message) {
		return message.getBoolean(CLUSTER_FORWARD_MESSAGE);
	}

	public static void sendMulticastMessage(
		Message message, Priority priority) {

		if ((_clusterLink == null) || !_clusterLink.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterLinkUtil has not been initialized");
			}
