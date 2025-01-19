	__syncthreads();

	SumBeforeWarp<blockSize>(sum);

	if (threadIdx.x < 32) {
		SumWarp<blockSize>(sum);

		if (threadIdx.x == 0) output[blockIdx.x] = sum[0] * multiplyFactor;
	}
}

void KernelSumSmallArray(cudaStream_t stream, int blockSize, cudafloat * inputs, cudafloat * output, int numInputs, cudafloat multiplyFactor) {
	switch(blockSize) {
		#ifdef FERMI
		case 1024:
			SumSmallArray<1024><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		#endif
		case 512:
			SumSmallArray<512><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 256:
			SumSmallArray<256><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 128:
			SumSmallArray<128><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 64:
			SumSmallArray<64><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 32:
			SumSmallArray<32><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 16:
			SumSmallArray<16><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 8:
			SumSmallArray<8><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 4:
			SumSmallArray<4><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 2:
			SumSmallArray<2><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
		case 1:
			SumSmallArray<1><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(inputs, output, numInputs, multiplyFactor);
			break;
	}
}
