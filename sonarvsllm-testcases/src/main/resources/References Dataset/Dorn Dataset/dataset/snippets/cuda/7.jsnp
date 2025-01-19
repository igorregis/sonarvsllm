
#define SVH(_R, _C) svh[(_R)][(_C)]
//#define SVH(_R, _C) (svh[(_C)][(_R)])

//#define SH(_R, _C) sh[(_R)][(_C)]
#define SH(_R, _C) sh[(_C)][(_R)]

#define SVW(_R, _C) svw[(_R)][(_C)]
//#define SVW(_R, _C) svw[(_C)][(_R)]

KERNEL UpdateW_MD(cudafloat * W, cudafloat * H, cudafloat * V, cudafloat * WH, cudafloat * sumH, int n, int m, int r) {
	__shared__ cudafloat SH(32, 32);
	__shared__ cudafloat SVW(32, 32);

	int x = blockIdx.x * 32 + threadIdx.x;	
	int y = blockIdx.y * 32 + threadIdx.y;

	cudafloat sum1 = CUDA_VALUE(0.0);
	cudafloat sum2 = CUDA_VALUE(0.0);

	for(int k = 0; k < m; k += 32) {
		int tx = threadIdx.x + 16;

		if (x < r && threadIdx.y + k < m) {
			int ky = k + threadIdx.y;
			SH(threadIdx.x, threadIdx.y) = HMATRIX(x, ky, r, m);
			SH(tx, threadIdx.y) = (x + 16 < r) ? HMATRIX(x + 16, ky, r, m) : CUDA_VALUE(0.0);
		} else {
			SH(threadIdx.x, threadIdx.y) = CUDA_VALUE(0.0);
			SH(tx, threadIdx.y) = CUDA_VALUE(0.0);
		}

		if (y < n && k + threadIdx.x < m) {
			int idx = (k + threadIdx.x) * n + y;
			SVW(threadIdx.y, threadIdx.x) = (V[idx] / (WH[idx] + SMALL_VALUE_TO_ADD_DENOMINATOR));

			idx += (n << 4);
			SVW(threadIdx.y, tx) = (k + tx < m) ? (V[idx] / (WH[idx] + SMALL_VALUE_TO_ADD_DENOMINATOR)) : CUDA_VALUE(0.0);
		} else {
			SVW(threadIdx.y, threadIdx.x) = CUDA_VALUE(0.0);
			SVW(threadIdx.y, tx) = CUDA_VALUE(0.0);
		}
		__syncthreads();

		for(int i = 0; i < 32; i++) {
			sum1 += SH(threadIdx.x, i) * SVW(threadIdx.y, i);
			sum2 += SH(tx, i) * SVW(threadIdx.y, i);
		}
		__syncthreads();
	}
