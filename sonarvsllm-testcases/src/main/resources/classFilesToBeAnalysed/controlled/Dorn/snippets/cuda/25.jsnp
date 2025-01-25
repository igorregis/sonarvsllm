	// Bind Symbols
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_VoxelNumber,&voxelNumber,sizeof(int)));
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_Binning,&binning,sizeof(int)));
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_Entropies,&entropies_h,sizeof(float4)));
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_NMI,&NMI,sizeof(float)));
    CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_ActiveVoxelNumber,&activeVoxelNumber,sizeof(int)));

	// Texture binding
	CUDA_SAFE_CALL(cudaBindTexture(0, targetImageTexture, *targetImageArray_d, voxelNumber*sizeof(float)));
	CUDA_SAFE_CALL(cudaBindTexture(0, resultImageTexture, *resultImageArray_d, voxelNumber*sizeof(float)));
	CUDA_SAFE_CALL(cudaBindTexture(0, resultImageGradientTexture, *resultGradientArray_d, voxelNumber*sizeof(float4)));
    CUDA_SAFE_CALL(cudaBindTexture(0, histogramTexture, *logJointHistogram_d, binNumber*sizeof(float)));
    CUDA_SAFE_CALL(cudaBindTexture(0, maskTexture, *mask_d, activeVoxelNumber*sizeof(int)));

    CUDA_SAFE_CALL(cudaMemset(*voxelNMIGradientArray_d, 0, voxelNumber*sizeof(float4)));

	const unsigned int Grid_reg_getVoxelBasedNMIGradientUsingPW = 
		(unsigned int)ceil((float)activeVoxelNumber/(float)Block_reg_getVoxelBasedNMIGradientUsingPW);
	dim3 B1(Block_reg_getVoxelBasedNMIGradientUsingPW,1,1);
	dim3 G1(Grid_reg_getVoxelBasedNMIGradientUsingPW,1,1);

	reg_getVoxelBasedNMIGradientUsingPW_kernel <<< G1, B1 >>> (*voxelNMIGradientArray_d);
	CUDA_SAFE_CALL(cudaThreadSynchronize());
#ifndef NDEBUG
	printf("[DEBUG] reg_getVoxelBasedNMIGradientUsingPW_kernel: %s - Grid size [%i %i %i] - Block size [%i %i %i]\n",
	       cudaGetErrorString(cudaGetLastError()),G1.x,G1.y,G1.z,B1.x,B1.y,B1.z);
#endif
}

