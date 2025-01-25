#include "utilities.h"
#include "reductions.cu"
#include "IOptimizer.cuh"

/* extern shared memory for dynamic allocation */
extern __shared__ float array[];

__global__ void setup_kernel(curandState *state, unsigned long seed)
{
	uint32_t posID = IMUL( IMUL(blockIdx.x, gridDim.y) + blockIdx.y, blockDim.x) + threadIdx.x;
	/* Each thread gets same seed, a different sequence number,
	no offset */
	curand_init(seed, posID, 0, &state[posID]);
}

template <uint32_t threadNum>
static __global__ void findBestFitness(float* g_fitnesses, float *g_globalBestFitness, 
							uint32_t* g_globalBestID, int solution_number)
{
	float* s_fitnesses = (float*) array;
	uint32_t tid = threadIdx.x;
	uint32_t setID = blockIdx.x;
	uint32_t posID = IMUL(setID, solution_number) + tid;

	float bestFitness;
	#ifdef MAXIMIZE
		s_fitnesses[tid] = -1e20;		//Start with the lowest possible value
		bestFitness = -1e20;
	#else
		s_fitnesses[tid] = 1e20;	//Start with a very high value
		bestFitness = 1e20;
	#endif

	if (tid < solution_number)
	{
		bestFitness = g_fitnesses[posID];
		s_fitnesses[tid] = bestFitness;
	}

	//Reduction of the s_fitnesses vector to find the minimum value
	#ifdef MAXIMIZE
		reduceToMax<threadNum>(s_fitnesses, tid);
	#else
		reduceToMin<threadNum>(s_fitnesses, tid);
	#endif
	
	//The first thread updates the global best data
	if (tid == 0)
		g_globalBestFitness[setID] = s_fitnesses[0];
	__syncthreads();
