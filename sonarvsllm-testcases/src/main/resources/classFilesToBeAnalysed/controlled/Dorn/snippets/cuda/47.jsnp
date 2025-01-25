__host__ void h_cudaPSO_firstBestsUpdate(float* g_bestFitnesses, float* g_fitnesses, uint32_t* g_localBestIDs, int particlesNumber, dim3 bestsUpdateGrid, dim3 bestsUpdateBlock)
{
	const unsigned int bestsUpdateSharedAmount = (particlesNumber + 2) * sizeof(float);
	g_firstBestsUpdate<<<bestsUpdateGrid, bestsUpdateBlock, bestsUpdateSharedAmount>>>(g_bestFitnesses, g_fitnesses, g_localBestIDs);
	optimizerCudaCheckError("h_cudaPSO_firstBestsUpdate: execution failed\n", __FILE__, __LINE__);
}

__host__ void h_cudaPSO_positionsUpdate(float W, float C1, float C2, float* g_positions, float* g_velocities, float* g_bestPositions,
										 uint32_t* g_updateFlags,  uint32_t* g_localBestIDs, curandState* devStates, 
										 float2* g_bounds, dim3 updateGrid, dim3 updateBlock)
{
	g_positionsUpdate<<<updateGrid, updateBlock>>>(W, C1, C2, g_positions, g_velocities, g_bestPositions, g_updateFlags, g_localBestIDs, devStates, g_bounds);
	optimizerCudaCheckError("h_cudaPSO_Optimize: g_positionsUpdate() execution failed\n", __FILE__, __LINE__);
}

__host__ void h_cudaPSO_bestsUpdate(float* g_bestFitnesses,float*  g_fitnesses, uint32_t* g_localBestIDs, uint32_t* g_updateFlags,
										int particlesNumber, dim3 bestsUpdateGrid, dim3 bestsUpdateBlock)
{
	const unsigned int bestsUpdateSharedAmount = (particlesNumber + 2) * sizeof(float);
	g_bestsUpdate<<<bestsUpdateGrid, bestsUpdateBlock, bestsUpdateSharedAmount>>>(g_bestFitnesses, g_fitnesses, g_localBestIDs, g_updateFlags);
	optimizerCudaCheckError("h_cudaPSO_bestsUpdate: execution failed\n", __FILE__, __LINE__);
}

/// Initialization of the GPU...
/// Here global variables pointing to device memory are initialized...
/// @param particlesNumber number of particles in the swarm
/// @param problemDimension dimensionality of the problem
/// @param numberOfGenerations number of generation to be performed during the optimization
__host__ void h_cudaPSO_BindTextures(int swarmsNumber, int particlesNumber, int problemDimension,
									float* g_positions, float* g_bestPositions, float* g_velocities, 
									float* g_fitnesses, float* g_bestFitnesses, uint32_t* g_localBestIDs, uint32_t* g_update)
{
	int dim, actualParticleSize = iAlignUp(problemDimension, 16);


	dim = swarmsNumber * particlesNumber * actualParticleSize * sizeof(float);
	
	cudaBindTexture(NULL, t_texPositions, g_positions, dim);
	optimizerCudaCheckError("h_init_cudaPSO: cudaBindTexture() execution failed\n", __FILE__, __LINE__);
	cudaBindTexture(NULL, t_texBestPositions, g_bestPositions, dim);
	optimizerCudaCheckError("h_cudaPSOBindBestPositionsTextures: cudaBindTexture() execution failed\n", __FILE__, __LINE__);
	cudaBindTexture(NULL, t_texVelocities, g_velocities, dim);
	optimizerCudaCheckError("h_cudaPSOBindPositionsTextures: cudaBindTexture() execution failed\n", __FILE__, __LINE__);

	dim = swarmsNumber * particlesNumber * sizeof(float);

	cudaBindTexture(NULL, t_texFitnesses, g_fitnesses, dim);
	optimizerCudaCheckError("h_cudaPSOBindFitnessesTextures: cudaBindTexture() execution failed\n", __FILE__, __LINE__);
	cudaBindTexture(NULL, t_texBestFitnesses, g_bestFitnesses, dim);
	optimizerCudaCheckError("h_cudaPSOBindBestFitnessesTextures: cudaBindTexture() execution failed\n", __FILE__, __LINE__);
