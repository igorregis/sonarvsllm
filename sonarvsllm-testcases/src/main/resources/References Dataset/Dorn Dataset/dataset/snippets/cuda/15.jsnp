
		float a;
		float b;

		for(int i = 0; i < centers_width; i++){

			a = Centers[idnx * centers_width + i];
			b = Input[idny * input_width + i];

			sum = sum + pow( a - b , 2);

		}

		Output[idnx + idny * output_width] = sqrt(sum);
	}
}

extern "C" void KernelEuclidianDistance(cudafloat *Output, int output_height, int output_width, cudafloat *Input, int input_width, cudafloat *Centers, int centers_width)
{
	int blockSize = 16;

	int wBlocks = output_width/blockSize + ((output_width%blockSize == 0)?0:1);
	int hBlocks = output_height/blockSize + ((output_height%blockSize == 0)?0:1);

	dim3 grid(wBlocks,hBlocks);
	dim3 threads(blockSize,blockSize);
	EuclidianDistance<<<grid,threads>>>(Output, output_height, output_width, Input, input_width, Centers, centers_width);
}

KERNEL FindMinKernel(cudafloat *Output, int output_height, int output_width, float *min_array, int* min_idx, cudafloat* Targets){
