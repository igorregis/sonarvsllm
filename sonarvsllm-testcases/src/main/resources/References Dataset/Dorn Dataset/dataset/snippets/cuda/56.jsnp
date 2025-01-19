
	reg_getVoxelBasedNMIGradientUsingPW_kernel <<< G1, B1 >>> (*voxelNMIGradientArray_d);
	CUDA_SAFE_CALL(cudaThreadSynchronize());
#if _VERBOSE
	printf("[VERBOSE] reg_getVoxelBasedNMIGradientUsingPW_kernel: %s - Grid size [%i %i %i] - Block size [%i %i %i]\n",
	       cudaGetErrorString(cudaGetLastError()),G1.x,G1.y,G1.z,B1.x,B1.y,B1.z);
#endif
}

