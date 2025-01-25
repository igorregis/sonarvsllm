    
    if(hostPtr == NULL || backPtr == NULL){
        fprintf(stderr, "unable to allocate memory\n");
        return -1;
    }

    for(i = 0; i < TRANSFER_SIZE; i++)
        ((uint8_t*)hostPtr)[i] = (i % 126) + 1;
/*
    if(mlock(hostPtr, TRANSFER_SIZE) != 0){
        fprintf(stderr, "Unable to pin memory to ram!\n");
        return -1;
    }
*/
    void* devPtr;
    if(cudaMalloc(&devPtr, TRANSFER_SIZE) != cudaSuccess){
    
        fprintf(stderr, "Unable to allocate device memory!\n");
        return -1;
    }

    cudaMemcpy(devPtr, (const void*)hostPtr, TRANSFER_SIZE, cudaMemcpyHostToDevice);
    cudaMemcpy(backPtr, (const void*)devPtr, TRANSFER_SIZE, cudaMemcpyDeviceToHost);
/*
    if(munlock(hostPtr, TRANSFER_SIZE) != 0){
        fprintf(stderr, "Unable to unpin memory!\n");
        return -1;
    }
*/
    free(hostPtr);
