
			// Synchronize to make sure that the preceding
			// computation is done before loading two new
			// sub-matrices of A and B in the next iteration
			__syncthreads();
	}

	// Write the block sub-matrix to device memory;
	// each thread writes one element
	if(idnx < wC && idny < hC){
		int c = wC * BLOCK_SIZE * by + BLOCK_SIZE * bx;
		C[c + wC * ty + tx] = sqrt(Csub);
	}
}

extern "C" void KernelEuclidianDistance(cudafloat *d_C, cudafloat* d_A, cudafloat* d_B,int uiWA,int uiWB, int uiWC, int uiHC)
{

	int blockSize = 16;

	int wBlocks = uiWC/blockSize + ((uiWC%blockSize == 0)?0:1);
	int hBlocks = uiHC/blockSize + ((uiHC%blockSize == 0)?0:1);

	dim3 grid(wBlocks,hBlocks);
	dim3 threads(blockSize,blockSize);

	EuclidianDistance<<< grid, threads >>>(d_C, d_A, d_B, uiWA, uiWB, uiWC, uiHC);

}

