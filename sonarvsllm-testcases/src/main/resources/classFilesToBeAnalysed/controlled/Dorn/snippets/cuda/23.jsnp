
        cudaChannelFormatDesc channelDesc = cudaCreateChannelDesc<float>();
        CUDA_SAFE_CALL(cudaBindTextureToArray(sourceTexture, *sourceImageArray_d, channelDesc));

        //Bind positionField to texture
        CUDA_SAFE_CALL(cudaBindTexture(0, positionFieldTexture, *positionFieldImageArray_d, activeVoxelNumber*sizeof(float4)));

        //Bind positionField to texture
        CUDA_SAFE_CALL(cudaBindTexture(0, maskTexture, *mask_d, activeVoxelNumber*sizeof(int)));

        // Bind the real to voxel matrix to texture
        mat44 *sourceMatrix;
        if(sourceImage->sform_code>0)
                sourceMatrix=&(sourceImage->sto_ijk);
        else sourceMatrix=&(sourceImage->qto_ijk);
        float4 *sourceRealToVoxel_h;CUDA_SAFE_CALL(cudaMallocHost((void **)&sourceRealToVoxel_h, 3*sizeof(float4)));
        float4 *sourceRealToVoxel_d;
        CUDA_SAFE_CALL(cudaMalloc((void **)&sourceRealToVoxel_d, 3*sizeof(float4)));
        for(int i=0; i<3; i++){
                sourceRealToVoxel_h[i].x=sourceMatrix->m[i][0];
                sourceRealToVoxel_h[i].y=sourceMatrix->m[i][1];
                sourceRealToVoxel_h[i].z=sourceMatrix->m[i][2];
                sourceRealToVoxel_h[i].w=sourceMatrix->m[i][3];
        }
        CUDA_SAFE_CALL(cudaMemcpy(sourceRealToVoxel_d, sourceRealToVoxel_h, 3*sizeof(float4), cudaMemcpyHostToDevice));
        CUDA_SAFE_CALL(cudaFreeHost((void *)sourceRealToVoxel_h));
        CUDA_SAFE_CALL(cudaBindTexture(0, sourceMatrixTexture, sourceRealToVoxel_d, 3*sizeof(float4)));

        const unsigned int Grid_reg_resampleSourceImage = (unsigned int)ceil((float)activeVoxelNumber/(float)Block_reg_resampleSourceImage);
        dim3 B1(Block_reg_resampleSourceImage,1,1);
        dim3 G1(Grid_reg_resampleSourceImage,1,1);

        reg_resampleSourceImage_kernel <<< G1, B1 >>> (*resultImageArray_d);
        CUDA_SAFE_CALL(cudaThreadSynchronize());
#if _VERBOSE
        printf("[VERBOSE] reg_resampleSourceImage_kernel kernel: %s - Grid size [%i %i %i] - Block size [%i %i %i]\n",
                cudaGetErrorString(cudaGetLastError()),G1.x,G1.y,G1.z,B1.x,B1.y,B1.z);
#endif
        // Unbind 
        CUDA_SAFE_CALL(cudaUnbindTexture(sourceTexture));
        CUDA_SAFE_CALL(cudaUnbindTexture(positionFieldTexture));
        CUDA_SAFE_CALL(cudaUnbindTexture(maskTexture));
        CUDA_SAFE_CALL(cudaUnbindTexture(sourceMatrixTexture));

        cudaFree(sourceRealToVoxel_d);
}




