	for (int n = threadIdx.x; n < numNeurons; n += blockDim.x) {
		int idx = SAMPLE * numNeurons + n;

		cudafloat o = inputs[idx];

		if (IsInfOrNaN(o)) {
			o = CUDA_VALUE(0.0);
		} else {
			cudafloat w = weights[n];
			cudafloat b = bias[n];
