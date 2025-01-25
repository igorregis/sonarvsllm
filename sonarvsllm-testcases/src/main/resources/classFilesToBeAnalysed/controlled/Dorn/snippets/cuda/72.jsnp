
	//float *attenuationIntegralPlaneArray_d;    //stores partial integral on planes parallel to the camera
	//CUDA_SAFE_CALL(cudaMalloc((void **)&attenuationIntegralPlaneArray_d, img->dim[1]*img->dim[3]*sizeof(float)));
	
	et_line_integral_attenuated_gpu_kernel <<<G1,B1>>> (*d_activity, *d_attenuation, currentCamPointer);

	CUDA_SAFE_CALL(cudaThreadSynchronize());
}

