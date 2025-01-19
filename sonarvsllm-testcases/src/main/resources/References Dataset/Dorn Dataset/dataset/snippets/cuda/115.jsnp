	// Texture binding
	const int numBlocks = bDim.x*bDim.y*bDim.z;
	CUDA_SAFE_CALL(cudaBindTexture(0, targetImageArray_texture, *targetImageArray_d, targetImage->nvox*sizeof(float)));
	CUDA_SAFE_CALL(cudaBindTexture(0, resultImageArray_texture, *resultImageArray_d, targetImage->nvox*sizeof(float)));	
    CUDA_SAFE_CALL(cudaBindTexture(0, activeBlock_texture, *activeBlock_d, numBlocks*sizeof(int)));    
	
	// Copy the sform transformation matrix onto the device memort
	mat44 *xyz_mat;
	if(targetImage->sform_code>0)
		xyz_mat=&(targetImage->sto_xyz);
	else xyz_mat=&(targetImage->qto_xyz);
	float4 t_m_a_h = make_float4(xyz_mat->m[0][0],xyz_mat->m[0][1],xyz_mat->m[0][2],xyz_mat->m[0][3]);
	float4 t_m_b_h = make_float4(xyz_mat->m[1][0],xyz_mat->m[1][1],xyz_mat->m[1][2],xyz_mat->m[1][3]);
	float4 t_m_c_h = make_float4(xyz_mat->m[2][0],xyz_mat->m[2][1],xyz_mat->m[2][2],xyz_mat->m[2][3]);
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(t_m_a, &t_m_a_h,sizeof(float4)));
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(t_m_b, &t_m_b_h,sizeof(float4)));
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(t_m_c, &t_m_c_h,sizeof(float4)));	
	// We need to allocate some memory to keep track of overlap areas and values for blocks
	unsigned memSize = BLOCK_SIZE * params->activeBlockNumber;    
	float * targetValues;CUDA_SAFE_CALL(cudaMalloc((void **)&targetValues, memSize * sizeof(float)));    
    memSize = BLOCK_SIZE * params->activeBlockNumber;
	float * resultValues;CUDA_SAFE_CALL(cudaMalloc((void **)&resultValues, memSize * sizeof(float)));        
    unsigned int Grid_block_matching = (unsigned int)ceil((float)params->activeBlockNumber/(float)Block_target_block);
    unsigned int Grid_block_matching_2 = 1;

    // We have hit the limit in one dimension
    if (Grid_block_matching > 65335) {
        Grid_block_matching_2 = (unsigned int)ceil((float)Grid_block_matching/65535.0f);
        Grid_block_matching = 65335;
    }
