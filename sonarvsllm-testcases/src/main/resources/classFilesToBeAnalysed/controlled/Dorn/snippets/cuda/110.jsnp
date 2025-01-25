		(unsigned int)ceil((float)nodeNumber/(float)Block_reg_convertNMIGradientFromVoxelToRealSpace);
	dim3 B1(Grid_reg_convertNMIGradientFromVoxelToRealSpace,1,1);
	dim3 G1(Block_reg_convertNMIGradientFromVoxelToRealSpace,1,1);

	_reg_convertNMIGradientFromVoxelToRealSpace_kernel <<< G1, B1 >>> (*nodeNMIGradientArray_d);
	CUDA_SAFE_CALL(cudaThreadSynchronize());
#ifndef NDEBUG
	printf("[DEBUG] reg_convertNMIGradientFromVoxelToRealSpace: %s - Grid size [%i %i %i] - Block size [%i %i %i]\n",
	       cudaGetErrorString(cudaGetLastError()),G1.x,G1.y,G1.z,B1.x,B1.y,B1.z);
#endif
	CUDA_SAFE_CALL(cudaFree(matrix_d));
}


void reg_initialiseConjugateGradient(	float4 **nodeNMIGradientArray_d,
					float4 **conjugateG_d,
					float4 **conjugateH_d,
					int nodeNumber)
{
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_NodeNumber,&nodeNumber,sizeof(int)));
	CUDA_SAFE_CALL(cudaBindTexture(0, gradientImageTexture, *nodeNMIGradientArray_d, nodeNumber*sizeof(float4)));

	const unsigned int Grid_reg_initialiseConjugateGradient =
		(unsigned int)ceil((float)nodeNumber/(float)Block_reg_initialiseConjugateGradient);
	dim3 B1(Grid_reg_initialiseConjugateGradient,1,1);
	dim3 G1(Block_reg_initialiseConjugateGradient,1,1);

	reg_initialiseConjugateGradient_kernel <<< G1, B1 >>> (*conjugateG_d);
	CUDA_SAFE_CALL(cudaThreadSynchronize());
#ifndef NDEBUG
	printf("[DEBUG] reg_initialiseConjugateGradient: %s - Grid size [%i %i %i] - Block size [%i %i %i]\n",
	       cudaGetErrorString(cudaGetLastError()),G1.x,G1.y,G1.z,B1.x,B1.y,B1.z);
#endif
	CUDA_SAFE_CALL(cudaMemcpy(*conjugateH_d, *conjugateG_d, nodeNumber*sizeof(float4), cudaMemcpyDeviceToDevice));
}

void reg_GetConjugateGradient(	float4 **nodeNMIGradientArray_d,
				float4 **conjugateG_d,
				float4 **conjugateH_d,
				int nodeNumber)
{
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_NodeNumber,&nodeNumber,sizeof(int)));
	CUDA_SAFE_CALL(cudaBindTexture(0, conjugateGTexture, *conjugateG_d, nodeNumber*sizeof(float4)));
	CUDA_SAFE_CALL(cudaBindTexture(0, conjugateHTexture, *conjugateH_d, nodeNumber*sizeof(float4)));
	CUDA_SAFE_CALL(cudaBindTexture(0, gradientImageTexture, *nodeNMIGradientArray_d, nodeNumber*sizeof(float4)));

	// gam = sum((grad+g)*grad)/sum(HxG);
	const unsigned int Grid_reg_GetConjugateGradient1 = (unsigned int)ceil((float)nodeNumber/(float)Block_reg_GetConjugateGradient1);
	dim3 B1(Block_reg_GetConjugateGradient1,1,1);
	dim3 G1(Grid_reg_GetConjugateGradient1,1,1);
