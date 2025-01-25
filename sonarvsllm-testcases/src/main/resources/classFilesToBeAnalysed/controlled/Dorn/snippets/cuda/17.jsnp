
	// Bind to the supplied port
	if (bind(sock_fd, res->ai_addr, res->ai_addrlen) == -1)
	{
		perror("bind()");
		close(sock_fd);
		exit(EXIT_FAILURE);
	}

	fprintf(stderr, "Bound to port %s\n", port);
	freeaddrinfo(res);

	// Receive connections with a back log of 1
	if (listen(sock_fd, 1) == -1)
	{
		perror("listen()");
		close(sock_fd);
		exit(EXIT_FAILURE);
	}

	return sock_fd;
}

int net_accept(int sock_fd)
{
	int					client_fd;
	struct sockaddr_in6	caddr;
	socklen_t			casize = sizeof(caddr);

	if ((client_fd = accept(sock_fd, (struct sockaddr *)&caddr, &casize)) == -1)
	{
		perror("accept()");
		close(sock_fd);
		exit(EXIT_FAILURE);
	}

	char client_ip[128];
	unsigned int client_port;
	inet_ntop(AF_INET6, &caddr.sin6_addr, client_ip, 128);
	client_port = ntohs(caddr.sin6_port);
	fprintf(stderr, "Accepted connection from [%s]:%u\n\n",
		client_ip, client_port);

	return client_fd;
}

int net_recv(int client_fd, char *net_data, int nbytes)
{
	char	*bptr = net_data;
	int		rbytes;
