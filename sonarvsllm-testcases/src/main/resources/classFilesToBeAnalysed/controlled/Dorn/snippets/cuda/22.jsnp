    int count = 1000;
    int i;
    
    void* devPtr;
    void* hostPtr;

    if (cudaMalloc(&devPtr, count) != cudaSuccess)
        return -1;

    hostPtr = malloc(count);

    for(i = 0; i < count; i++){
        ((uint8_t*)hostPtr)[i] = i % 255;
    }
    for(i = 0; i < count; i++)
        fprintf(stderr, "In pos %d: %d\n", i, ((uint8_t*)hostPtr)[i]);

    if (cudaMemcpy(devPtr, hostPtr, count, cudaMemcpyHostToDevice) != cudaSuccess)
        return -1;

    for(i = 0; i < count; i++){
        
        ((uint8_t*)hostPtr)[i] = 0;
    }

    if (cudaMemcpy(hostPtr, devPtr, count, cudaMemcpyDeviceToHost) != cudaSuccess)
        return -1;

    for(i = 0; i < count; i++)
        fprintf(stderr, "In pos %d: %d\n", i, ((uint8_t*)hostPtr)[i]);
