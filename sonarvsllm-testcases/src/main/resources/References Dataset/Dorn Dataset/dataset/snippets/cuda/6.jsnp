#include "MBPkernels.h"

namespace GPUMLib {

KERNEL RobustLearning(cudafloat * rmsF, cudafloat * bestRMS, cudafloat maxErrorGrowth, int layers, int * numberWeights, cudafloat ** weights, cudafloat ** bestWeights, cudafloat ** learningRate, cudafloat r, cudafloat ** lastDeltaWithoutLearningMomentum, cudafloat ** lastDelta) {
	__shared__ cudafloat rms;
	__shared__ cudafloat bRMS;
	
	rms = *rmsF;
	bRMS = *bestRMS;
