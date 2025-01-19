	
void KernelComputeStatusVisibleUnitsRBM(dim3 & gridSize, int blockSize, cudafloat * h, cudafloat * weights, cudafloat * a, cudafloat * v, float * randomValues, int J);
void KernelComputeStatusHiddenUnitsRBM(dim3 & gridSize, int blockSize, cudafloat * v, cudafloat * weights, cudafloat * b, cudafloat * h, float * randomValues, int I);
KERNEL ComputeStatusHiddenUnitsSmallRBM(cudafloat * v, cudafloat * weights, cudafloat * b, cudafloat * h, float * randomValues);
KERNEL ComputeStatusVisibleUnitsSmallRBM(cudafloat * h, cudafloat * weights, cudafloat * a, cudafloat * v, float * randomValues);

void RBM::RandomizeWeights() {
	int nWeights = w.Elements();

	cudafloat * weights = w.HostPointer();
			
	for (int i = 0; i < nWeights; i++) weights[i] = CUDA_VALUE(2.0) * stdWeights * ((cudafloat) rand() / RAND_MAX) - stdWeights;
	w.UpdateDevice();

	int blockSize = NumberThreadsPerBlockThatBestFit(nWeights);
	int blocks = NumberBlocks(nWeights, blockSize);

	#ifdef USE_STEP_SIZE
		InitBiasDeltasRBM<<<blocks, blockSize>>>(b.DevicePointer(), INITIAL_BIAS_HIDDEN_UNITS, lastDeltaW.Pointer(), lastDeltaB.Pointer(), lastDeltaWithoutLearningMomentumW.Pointer(), lastDeltaWithoutLearningMomentumB.Pointer(), learningRateW.Pointer(), learningRateB.Pointer(), initialLearningRate, nWeights, J);
	#else
		InitBiasDeltasRBM<<<blocks, blockSize>>>(b.DevicePointer(), INITIAL_BIAS_HIDDEN_UNITS, lastDeltaW.Pointer(), lastDeltaB.Pointer(), nWeights, J);
		learningRate = initialLearningRate;
	#endif

	blocks = NumberBlocks(I, inputsBlockSize);

	#ifdef USE_STEP_SIZE
		InitInputBiasDeltasRBM<<<blocks, inputsBlockSize>>>(v.Pointer(),  a.DevicePointer(), lastDeltaA.Pointer(), lastDeltaWithoutLearningMomentumA.Pointer(), learningRateA.Pointer(), initialLearningRate, I, samples);
	#else
		InitInputBiasDeltasRBM<<<blocks, inputsBlockSize>>>(v.Pointer(), a.DevicePointer(), lastDeltaA.Pointer(), I, samples);
	#endif

	epoch = 0;
}

void RBM::ComputeStatusUnits(cudafloat * v, cudafloat * h, cudafloat * v_reconstructed) {
	if (proportionRandomValuesUsed == proportionRandomValuesGenerated) {
		Random::Fill(randomValues);
		proportionRandomValuesUsed = 0;
	}

	int connections = w.Elements();

	float * rnd = (v_reconstructed == nullptr) ? nullptr : (randomValues.Pointer() + (proportionRandomValuesUsed * randomValuesNeededPerEpoch));

	if(connections > MAX_THREADS_PER_BLOCK) {
		KernelComputeStatusHiddenUnitsRBM(dimJsamples, inputsBlockSize, v, w.DevicePointer(), b.DevicePointer(), h, rnd, I);
	} else {
		ComputeStatusHiddenUnitsSmallRBM<<<samples, dimIJ, connections * sizeof(cudafloat)>>>(v, w.DevicePointer(), b.DevicePointer(), h, rnd);
	}
