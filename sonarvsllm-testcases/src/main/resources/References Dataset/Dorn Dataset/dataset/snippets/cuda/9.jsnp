{
    const int tid=blockIdx.x*blockDim.x+threadIdx.x;
    if(tid < c_VoxelNumber){
        int3 imageSize = c_ImageDim;

        short z=(int)(tid/((imageSize.x)*(imageSize.y)));

        int radius = (windowSize-1)/2;
        int index = tid - imageSize.x*imageSize.y*radius;
        z -= radius;

        float4 finalValue = make_float4(0.0f, 0.0f, 0.0f, 0.0f);

        for(int i=0; i<windowSize; i++){
            if(-1<z && z<imageSize.z){
                float4 gradientValue = tex1Dfetch(gradientImageTexture,index);
                float windowValue = tex1Dfetch(convolutionKernelTexture,i);
                finalValue.x += gradientValue.x * windowValue;
                finalValue.y += gradientValue.y * windowValue;
                finalValue.z += gradientValue.z * windowValue;
            }
            index += imageSize.x*imageSize.y;
            z++;
        }

        smoothedImage[tid] = finalValue;
    }
    return;
}

