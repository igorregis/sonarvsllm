			if((a+9) <= aEnd) Csub += pow(AS(ty, 9) - BS(tx, 9),2);
			if((a+10) <= aEnd) Csub += pow(AS(ty, 10) - BS(tx, 10),2);
			if((a+11) <= aEnd) Csub += pow(AS(ty, 11) - BS(tx, 11),2);
			if((a+12) <= aEnd) Csub += pow(AS(ty, 12) - BS(tx, 12),2);
			if((a+13) <= aEnd) Csub += pow(AS(ty, 13) - BS(tx, 13),2);
			if((a+14) <= aEnd) Csub += pow(AS(ty, 14) - BS(tx, 14),2);
			if((a+15) <= aEnd) Csub += pow(AS(ty, 15) - BS(tx, 15),2);

			// Synchronize to make sure that the preceding
			// computation is done before loading two new
			// sub-matrices of A and B in the next iteration
			__syncthreads();
	}

	// Write the block sub-matrix to device memory;
	// each thread writes one element
	if(idnx < wC && idny < hC){
		int c = hC * BLOCK_SIZE * bx + BLOCK_SIZE * by;
		C[c + hC * tx + ty] = exp(-(Csub/(scalingfactor*pow(c_width[idnx],2))));         
	}
}


extern "C" void KernelActivationMatrix(cudafloat *d_C, cudafloat* d_A, cudafloat* d_B,int uiWA,int uiWB, int uiWC, int uiHC, float scalingfactor, float* c_width)
{

	int blockSize = 16;

	int wBlocks = uiWC/blockSize + ((uiWC%blockSize == 0)?0:1);
	int hBlocks = uiHC/blockSize + ((uiHC%blockSize == 0)?0:1);
