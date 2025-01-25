		g_globalBestID[setID] = s_fitnesses[0];
}

////////////////////////////////////////////////////HOST FUNCTIONS////////////////////////////////////////////////////

__host__  void initRandomStates(unsigned int m_setNumber, unsigned int solutionsNumber, unsigned int actualSolutionSize, curandState* devStates)
{ setup_kernel<<<dim3(m_setNumber, solutionsNumber,1), actualSolutionSize>>>(devStates, time(NULL)); }

__host__  int getThreadNumForReduction(int size) {return (int) rint( pow(2.0f, (int)ceil( log2( (float) size) ) ) );}

__host__ void h_findBestFitness(float* g_fitnesses, float* g_globalBestFitness, unsigned int * g_globalBestID, int solution_num, dim3 findBestFitnessGrid, dim3 findBestFitnessBlock)
{
	if(findBestFitnessBlock.x <= 8)
		findBestFitness<   8><<<findBestFitnessGrid,   8,   16*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 16)
		findBestFitness<  16><<<findBestFitnessGrid,  16,  32*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 32)
		findBestFitness<  32><<<findBestFitnessGrid,  32,  32*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 64)
		findBestFitness<  64><<<findBestFitnessGrid,  64,  64*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 128)
		findBestFitness< 128><<<findBestFitnessGrid, 128, 128*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 256)
		findBestFitness< 256><<<findBestFitnessGrid, 256, 256*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 512)
		findBestFitness< 512><<<findBestFitnessGrid, 512, 512*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	else if(findBestFitnessBlock.x <= 1024)
		findBestFitness<1024><<<findBestFitnessGrid,1024,1024*sizeof(float)>>>(g_fitnesses, g_globalBestFitness, g_globalBestID, solution_num);
	optimizerCudaCheckError("h_cudafindBestFitness: execution failed\n", __FILE__, __LINE__);
}
