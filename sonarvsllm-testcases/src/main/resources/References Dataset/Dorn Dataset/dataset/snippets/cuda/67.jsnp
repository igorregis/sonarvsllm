	CUDA_SAFE_CALL(cudaBindTexture(0, resultImageGradientTexture, *resultGradientArray_d, voxelNumber*sizeof(float4)));
    CUDA_SAFE_CALL(cudaBindTexture(0, histogramTexture, *logJointHistogram_d, binNumber*sizeof(float)));
    CUDA_SAFE_CALL(cudaBindTexture(0, maskTexture, *mask_d, activeVoxelNumber*sizeof(int)));

    CUDA_SAFE_CALL(cudaMemset(*voxelNMIGradientArray_d, 0, voxelNumber*sizeof(float4)));

	const unsigned int Grid_reg_getVoxelBasedNMIGradientUsingPW = 
		(unsigned int)ceil((float)activeVoxelNumber/(float)Block_reg_getVoxelBasedNMIGradientUsingPW);
	dim3 B1(Block_reg_getVoxelBasedNMIGradientUsingPW,1,1);
	dim3 G1(Grid_reg_getVoxelBasedNMIGradientUsingPW,1,1);
