
#define NUM_INPUTS_OUTPUT_NEURON (NUM_NEURONS + 1)

#define PATTERN blockIdx.x

namespace GPUMLib {

KERNEL CalculateLocalGradient(cudafloat * rmsF, cudafloat * bestRMS, cudafloat maxErrorGrowth, cudafloat * outputs, cudafloat * weights, cudafloat * m, int mOffset, int totalNeuronsWithSelectiveActivation, cudafloat * localGradientNextLayer, cudafloat * localGradient, cudafloat * localGradientSpaceNet) {
	extern __shared__ cudafloat lg[];
	
	if (bestRMS != nullptr) {
		__shared__ cudafloat rms;
		__shared__ cudafloat bRMS;
		
		rms = *rmsF;
		bRMS = *bestRMS;
		if (rms >= bRMS * maxErrorGrowth) return;
	}

	cudafloat * lgNextLayer = (lg + (NUM_OUTPUTS * NUM_NEURONS));
			
	if (NEURON == 0) lgNextLayer[OUTPUT_NEURON] = localGradientNextLayer[PATTERN * NUM_OUTPUTS + OUTPUT_NEURON];
	
	int connection = OUTPUT_NEURON * NUM_INPUTS_OUTPUT_NEURON + NEURON + 1;    
	int threadId = (NEURON * NUM_OUTPUTS + OUTPUT_NEURON);
	
	__syncthreads();    
	
	lg[threadId] = weights[connection] * lgNextLayer[OUTPUT_NEURON];
	__syncthreads();
