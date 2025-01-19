
	public SetServerManagerContextPathCommand( IRemoteServerWorkingCopy server, String serverManagerContextPath ) {
		super( server, "Set Server Manager Context Path" );
		this.serverManagerContextPath = serverManagerContextPath;
	}

	public void execute() {
		oldServerManagerContextPath = server.getServerManagerContextPath();
		server.setServerManagerContextPath( serverManagerContextPath );
	}
