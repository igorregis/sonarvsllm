__global__ void sa_step(const uint32_t solutionNumber, const uint32_t problemDimension, const uint32_t actualSolutionSize,
						float* positions, float* potentialPositions, float2* bounds, curandState* devStates)
{
	const uint32_t tid = threadIdx.x;
	const uint32_t solutionID = IMUL(blockIdx.x, gridDim.y) + blockIdx.y;
	const uint32_t posID = IMUL( solutionID, actualSolutionSize) + tid;
	curandState localState = devStates[posID];

	if(tid < problemDimension)
	{	
		float y = positions[posID] + (-0.1f + (curand_uniform(&localState) * 0.2f));
		cropPosition(bounds, y, blockIdx.x, tid, actualSolutionSize);
		potentialPositions[posID] = y;
	}

	devStates[posID] = localState;
}

__global__ void sa_selection (float* positions, float* potentialPositions, float temp, float* fitnesses, float* potentialFitnesses,
							 unsigned int problemDimension, unsigned int actualSolutionSize, curandState* devStates)
{
	uint32_t  tid = threadIdx.x;
	const uint32_t solutionPosID = IMUL(blockIdx.x, gridDim.y)+blockIdx.y;
	uint32_t posID = IMUL( solutionPosID, actualSolutionSize) + tid;

	__shared__ bool update;
	curandState localState = devStates[posID];

	if (tid == 0)
	{
		float potentialFitness = potentialFitnesses[solutionPosID];
		float oldFitness = fitnesses[solutionPosID];

		if ((potentialFitness BETTER_THAN oldFitness) || 
		#ifdef MAXIMIZE
			(exp((potentialFitness - oldFitness)/temp) > curand_uniform(&localState) ) )
		#else
			(exp((oldFitness - potentialFitness)/temp) > curand_uniform(&localState) ) )
		#endif
		{
			update = true;
			fitnesses[solutionPosID] = potentialFitness;
		}
		else
			update = false;
	}
	__syncthreads ();

	if (update && tid<problemDimension)
		positions[posID] = potentialPositions[posID];
