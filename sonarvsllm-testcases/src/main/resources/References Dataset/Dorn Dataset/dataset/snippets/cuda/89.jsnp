                                                int **mask_d,
                                                int activeVoxelNumber,
                                                double *entropies,
                                                int binning)
{
	const int voxelNumber = targetImage->nvox;
	const int binNumber = binning*(binning+2);
	const float4 entropies_h=make_float4(entropies[0],entropies[1],entropies[2],entropies[3]);
	const float NMI = (entropies[0]+entropies[1])/entropies[2];

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
