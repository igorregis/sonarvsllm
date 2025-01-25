			p->x = matrix[i][query[j]];
			p->y = matrix[i][query[j + 1]];
			p->w = matrix[i][query[j + 2]];
			p->z = matrix[i][query[j + 3]];
			//increase the pointer
			p++;
		}
	}
	pMemcpy2DToArray(cudaInterQueryPrf, 0, 0, hostQueryPrf, prfLength * sizeof(int4), prfLength * sizeof(int4), 32, pMemcpyHostToDevice);
	pFreeHost(hostQueryPrf);
