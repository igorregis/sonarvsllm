	extern __shared__ cudafloat iw[];
	
	iw[threadIdx.x] = CUDA_VALUE(0.0);
	for(int i = threadIdx.x; i <= numInputs; i += blockDim.x) {
		cudafloat i_w = weights[NEURON * (numInputs + 1) + i];
		if (i > BIAS) i_w *= inputs[PATTERN * numInputs + (i - 1)];
		iw[threadIdx.x] += i_w;
	}
	__syncthreads();

	SumBeforeWarp<blockSize>(iw);

	if (threadIdx.x < 32) {
		SumWarp<blockSize>(iw);
		
		if (threadIdx.x == 0) {
			int n = PATTERN * NUM_NEURONS + NEURON;
			int nSelAct = PATTERN * totalNeuronsWithSelectiveActivation + NEURON + mOffset;

			cudafloat output = CUDA_SIGMOID(iw[0]);
			cudafloat M = (m != nullptr) ? m[nSelAct] : CUDA_VALUE(1.0);
			cudafloat outn = output * M;
			
			cudafloat error = (desiredOutputs[n] - outn);
			
			if (m != nullptr) localGradientSpaceNet[nSelAct] = error * output * CUDA_SIGMOID_DERIVATE(M);
			
			outputs[n] = outn;
			
			localGradient[n] = error * M * CUDA_SIGMOID_DERIVATE(output);
