		__syncthreads();

		deltaW += vd[threadIdx.x] * hd[threadIdx.y] - vr[threadIdx.x] * hr[threadIdx.y];
	}

	// update weights
	if (i < I && j < J) {
		deltaW /= samples;

		int w = j * I + i;

		cudafloat learningRate = UpdateLearningRate(learningRateW, lastDeltaWithoutLearningMomentumW, deltaW, w, u, d);
		UpdateWeight(learningRate, momentum, deltaW, lastDeltaW, lastDeltaWithoutLearningMomentumW, weights, w);
	}

	if(i < I && threadIdx.y == 0) {
		errors[i] = error;

		// Update a
		if (j == 0) {
			deltaA /= samples;

			cudafloat learningRate = UpdateLearningRate(learningRateA, lastDeltaWithoutLearningMomentumA, deltaA, i, u, d);
			UpdateWeight(learningRate, momentum, deltaA, lastDeltaA, lastDeltaWithoutLearningMomentumA, a, i);
		}
	}

	// Update b
	if (i == 0 && j < J) {
		deltaB /= samples;
