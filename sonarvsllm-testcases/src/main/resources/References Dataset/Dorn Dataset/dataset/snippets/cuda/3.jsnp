*/

#include "RBMconfig.h"

namespace GPUMLib {

KERNEL InitBiasDeltasRBM(cudafloat * bias, cudafloat initialBias, cudafloat * lastDeltaW, cudafloat * lastDeltaB, cudafloat * lastDeltaWithoutLearningMomentumW, cudafloat * lastDeltaWithoutLearningMomentumB, cudafloat * learningRateW, cudafloat * learningRateB, cudafloat initialLearningRate, int weights, int J) {
	int idx = blockIdx.x * blockDim.x + threadIdx.x;

	if (idx < weights) {
		lastDeltaW[idx] = CUDA_VALUE(0.0);

		#ifdef USE_STEP_SIZE
		learningRateW[idx] = initialLearningRate;
		lastDeltaWithoutLearningMomentumW[idx] = CUDA_VALUE(0.0);				
		#endif

		if (idx < J) {
			bias[idx] = initialBias;
			lastDeltaB[idx] = CUDA_VALUE(0.0);
			#ifdef USE_STEP_SIZE
			lastDeltaWithoutLearningMomentumB[idx] = CUDA_VALUE(0.0);
			learningRateB[idx] = initialLearningRate;
			#endif
		}
	}
}

KERNEL InitInputBiasDeltasRBM(cudafloat * v, cudafloat * bias, cudafloat * lastDeltaA, cudafloat * lastDeltaWithoutLearningMomentumA, cudafloat * learningRateA, cudafloat initialLearningRate, int I, int samples) {
	int input = blockIdx.x * blockDim.x + threadIdx.x;
