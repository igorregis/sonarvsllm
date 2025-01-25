	
	const unsigned int grid = (unsigned int)ceil(array_size/(float)BLOCK);
	dim3 B(BLOCK,1,1);
	dim3 G(grid,1,1);
	
	et_joint_histogram_gpu_kernel <<<G,B>>> (*d_array_A, *d_array_B, *d_jont_hist, array_size, hist_size, min_A, max_A, min_B, max_B);
	
	CUDA_SAFE_CALL(cudaThreadSynchronize());
}

