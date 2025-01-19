		}
		__syncthreads();
	}

	if (y < n && x < r) {
		WMATRIX(y, x, n, r) *= (sum1 / sumH[x]);
		x += 16;
		if (x < r) WMATRIX(y, x, n, r) *= (sum2 / sumH[x]);
	}
}
