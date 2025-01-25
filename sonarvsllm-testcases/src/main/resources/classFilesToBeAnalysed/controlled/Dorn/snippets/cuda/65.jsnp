		}
		total_rbytes += rbytes;
		
 		if (verbose) fprintf(stderr,"Read in %d for a total of %.1f kB\r",rbytes,((float) total_rbytes)/1024);

		if (rbytes == 0 && !online)
		{
			// Processed all the data
			break;
		}
