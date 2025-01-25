	g_positions[posID] = pos;
}


//********************************************
//Kernel code for the first local bests update
//********************************************

///@brief cuda kernel to update the index of the local best particle and the "update best position" flag for all particles in parallel (at once)
///This kernel uses only one thread-block to load the current fitness value of all particles and determine all the local best indices. This is called only the first time to force the update of the personal best fitness values.
///@param g_bestFitnesses pointer to the GPU's global-memory array containing the current personal best fitness of all particles
///@param g_localBestIDs pointer to the GPU's global-memory array containing the indexes (for all particles) of the best neighbour (for the ring topology in this case)
static __global__ void g_firstBestsUpdate(float*  g_bestFitnesses, float* g_fitnesses, uint32_t* g_localBestIDs)
{
	//threadIdx.x represents the index of the particle inside the swarm
	//blockDim.x represents the NUMBER_OF_PARTICLES
	uint32_t particleID = threadIdx.x;
	uint32_t posID = IMUL(blockIdx.x, blockDim.x) + particleID;

	float* s_fitnesses = (float*) s_addends;

	//Load the fitness value from global memory
	float fitness = tex1Dfetch(t_texFitnesses, posID);
	g_bestFitnesses[posID] = fitness;

	int bestID = particleID + 1;
	s_fitnesses[bestID] = fitness;

	//Toroidal ring topology...
	if (particleID == blockDim.x - 1)
		s_fitnesses[0 ] = fitness;
	if (particleID == 0)
		s_fitnesses[blockDim.x + 1] = fitness;
	//__syncthreads();

	//Find the local best (among the two neighbours of the ring)

	//Controls the left-neighbour
	if (s_fitnesses[particleID] BETTER_THAN s_fitnesses[bestID])
		bestID = particleID;

	//Controls the right-neighbour
	if (s_fitnesses[particleID+2] BETTER_THAN s_fitnesses[bestID])
		bestID = particleID+2;


	if (bestID == 0)
		bestID = blockDim.x;
	if (bestID == (blockDim.x + 1))
		bestID = 1;
