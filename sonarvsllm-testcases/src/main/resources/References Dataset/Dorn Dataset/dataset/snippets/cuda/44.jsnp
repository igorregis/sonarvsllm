
			vd[threadIdx.x] = dat;
			vr[threadIdx.x] = rec;

			cudafloat e = dat - rec;
			deltaA += e;

			error += e * e;
		}

		if (threadIdx.x == 0 && j < J) {
			cudafloat dat = h_data[s * J + j];
			cudafloat rec = h_recon[s * J + j];

			hd[threadIdx.y] = dat;
			hr[threadIdx.y] = rec;

			deltaB += dat - rec;
		}

		__syncthreads();

		deltaW += vd[threadIdx.x] * hd[threadIdx.y] - vr[threadIdx.x] * hr[threadIdx.y];
	}

	// update weights
	if (i < I && j < J) {
		deltaW /= samples;

		int w = j * I + i;
