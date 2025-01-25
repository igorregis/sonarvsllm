	cudafloat * lgNextLayer = (lg + (NUM_OUTPUTS * NUM_NEURONS));

	if (NEURON == 0) lgNextLayer[OUTPUT_NEURON] = localGradientNextLayer[SAMPLE * NUM_OUTPUTS + OUTPUT_NEURON];

	int connection = OUTPUT_NEURON * NUM_INPUTS_OUTPUT_NEURON + NEURON + 1;    
	int threadId = (NEURON * NUM_OUTPUTS + OUTPUT_NEURON);

	__syncthreads();    

	lg[threadId] = weights[connection] * lgNextLayer[OUTPUT_NEURON];
	__syncthreads();

	int numberElemSum = NUM_OUTPUTS;
	for(int sumUpTo = (numberElemSum >> 1); numberElemSum > 1; sumUpTo = (numberElemSum >> 1)) {
		int nextNumberElemSum = sumUpTo;
		if (numberElemSum & 1) nextNumberElemSum++;
	
		if (OUTPUT_NEURON < sumUpTo) lg[threadId] += lg[threadId + nextNumberElemSum];
		
		numberElemSum = nextNumberElemSum;
		
		__syncthreads();
	}
	
	if (OUTPUT_NEURON == 0) {
		cudafloat lgn = CUDA_VALUE(0.0);

		int n = SAMPLE * NUM_NEURONS + NEURON;

		cudafloat i = inputs[n];
