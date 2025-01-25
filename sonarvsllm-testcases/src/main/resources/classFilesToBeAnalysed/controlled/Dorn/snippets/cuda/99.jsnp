	}

	if (blockSize >= 512) {
		if (threadIdx.x < 256 && minvalue[threadIdx.x] > minvalue[threadIdx.x + 256]) {
			minvalue[threadIdx.x] = minvalue[threadIdx.x + 256];
			minpos[threadIdx.x] = minpos[threadIdx.x + 256];
		}
		__syncthreads();
	}

	if (blockSize >= 256) {
		if (threadIdx.x < 128 && minvalue[threadIdx.x] > minvalue[threadIdx.x + 128]) {
			minvalue[threadIdx.x] = minvalue[threadIdx.x + 128];
			minpos[threadIdx.x] = minpos[threadIdx.x + 128];
		}
		__syncthreads();
	}

	if (blockSize >= 128) {
		if (threadIdx.x < 64 && minvalue[threadIdx.x] > minvalue[threadIdx.x + 64]) {
			minvalue[threadIdx.x] = minvalue[threadIdx.x + 64];
			minpos[threadIdx.x] = minpos[threadIdx.x + 64];
		}
		__syncthreads();
	}

	if (threadIdx.x < 32) {
		volatile cudafloat * _minvalue = minvalue;
		volatile int * _minpos = minpos;

		if (blockSize >= 64) {
			if (_minvalue[threadIdx.x] > _minvalue[threadIdx.x + 32]) {
				_minvalue[threadIdx.x] = _minvalue[threadIdx.x + 32];
				_minpos[threadIdx.x] = _minpos[threadIdx.x + 32];
			}
		}
	
		if (blockSize >= 32) {
			if (_minvalue[threadIdx.x] > _minvalue[threadIdx.x + 16]) {
				_minvalue[threadIdx.x] = _minvalue[threadIdx.x + 16];
				_minpos[threadIdx.x] = _minpos[threadIdx.x + 16];
			}
		}
	
		if (blockSize >= 16) {
			if (_minvalue[threadIdx.x] > _minvalue[threadIdx.x + 8]) {
				_minvalue[threadIdx.x] = _minvalue[threadIdx.x + 8];
				_minpos[threadIdx.x] = _minpos[threadIdx.x + 8];
			}
		}
