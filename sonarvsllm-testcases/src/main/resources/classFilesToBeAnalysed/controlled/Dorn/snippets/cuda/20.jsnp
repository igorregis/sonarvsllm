
    //Convolve slices one by one
    for (int slice=0; slice<n_slices; slice++)
        {
        //Determine slice pointer
        d_Data = (*d_data) + slice * data_slice_size; 
        d_Kernel = (*d_kernel) + slice * kernel_slice_size;
        d_Result = (*d_result) + slice * data_slice_size;

        //Zero pad
//        fprintf_verbose("Padding convolution kernel and input data...\n");
        cutilSafeCall( cudaMemset(d_PaddedKernel, 0, fftH * fftW * sizeof(float)) );
        cutilSafeCall( cudaMemset(d_PaddedData,   0, fftH * fftW * sizeof(float)) );
        padKernel(d_PaddedKernel,d_Kernel,fftH,fftW,kernelH,kernelW,kernelY,kernelX);
cutilSafeCall( cudaThreadSynchronize() );
	if (!d_PaddedData || !d_PaddedKernel) fprintf_verbose("NULL arguments!\n");
//        fprintf_verbose( "%d %d %d %d %d %d %d %d %d %d\n", d_PaddedData,d_Data,fftH,fftW,dataH,dataW,kernelH,kernelW,kernelY,kernelX);
        padDataClampToBorder(d_PaddedData,d_Data,fftH,fftW,dataH,dataW,kernelH,kernelW,kernelY,kernelX);
cutilSafeCall( cudaThreadSynchronize() );
        //Convolve
//        fprintf_verbose("Transforming convolution kernel...\n");
        cufftSafeCall( cufftExecR2C(fftPlanFwd, d_PaddedKernel, (cufftComplex *)d_KernelSpectrum) );

//        fprintf_verbose("Running GPU FFT convolution...\n");
        cutilSafeCall( cudaThreadSynchronize() );
        cufftSafeCall( cufftExecR2C(fftPlanFwd, d_PaddedData, (cufftComplex *)d_DataSpectrum) );
        modulateAndNormalize(d_DataSpectrum, d_KernelSpectrum, fftH, fftW);
        cufftSafeCall( cufftExecC2R(fftPlanInv, (cufftComplex *)d_DataSpectrum, d_PaddedData) );
        cutilSafeCall( cudaThreadSynchronize() );
      

        //Crop result
//        fprintf_verbose("Cropping result image...\n");
        //cutilSafeCall( cudaMemset(d_Result, 11, dataH * dataW * sizeof(float)) ); //FIXME do the real thing
        crop_image(d_Result,d_PaddedData,fftH,fftW,dataH,dataW,kernelH,kernelW);
        }

    //Destroy cuFFT plan and free memory
//    fprintf_verbose("Shutting down...\n");
    cufftSafeCall( cufftDestroy(fftPlanInv) );
    cufftSafeCall( cufftDestroy(fftPlanFwd) );
    cutilSafeCall( cudaFree(d_DataSpectrum)   );
    cutilSafeCall( cudaFree(d_KernelSpectrum) );
    cutilSafeCall( cudaFree(d_PaddedData)   );
    cutilSafeCall( cudaFree(d_PaddedKernel) );

    status = 0;
    return status;
}

