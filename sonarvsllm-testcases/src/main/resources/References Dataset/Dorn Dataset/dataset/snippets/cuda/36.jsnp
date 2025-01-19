		if (threadIdx.x == 0) {
			cudafloat fRMS = CUDA_SQRT(shared_rms[0] / numberPatternsNeurons) / CUDA_VALUE(2.0);
			if (IsInfOrNaN(fRMS)) fRMS = numberPatternsNeurons;
			*rmsF = fRMS;
		}
	}
}

void KernelCalculateRMS(cudaStream_t stream, int blockSize, cudafloat * rms, cudafloat * rmsOut, int numberPatterns, cudafloat numberPatternsNeurons) {
	switch(blockSize) {
		#ifdef FERMI
		case 1024:
			CalculateRMS<1024><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;
		#endif
		case 512:
			CalculateRMS<512><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 256:
			CalculateRMS<256><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 128:
			CalculateRMS<128><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 64:
			CalculateRMS<64><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 32:
			CalculateRMS<32><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 16:
			CalculateRMS<16><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 8:
			CalculateRMS<8><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 4:
			CalculateRMS<4><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;

		case 2:
			CalculateRMS<2><<<1, blockSize, blockSize * sizeof(cudafloat), stream>>>(rms, rmsOut, numberPatterns, numberPatternsNeurons);
			break;
