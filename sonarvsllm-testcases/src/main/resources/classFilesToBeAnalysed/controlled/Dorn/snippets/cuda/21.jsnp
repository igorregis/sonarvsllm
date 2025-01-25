				volatile unsigned int* smem = sdata;
				if (blockSize >=  64) { smem[tid] = mySum = min(mySum, smem[tid + 32]); EMUSYNC; }
				if (blockSize >=  32) { smem[tid] = mySum = min(mySum, smem[tid + 16]); EMUSYNC; }
				if (blockSize >=  16) { smem[tid] = mySum = min(mySum, smem[tid +  8]); EMUSYNC; }
				if (blockSize >=   8) { smem[tid] = mySum = min(mySum, smem[tid +  4]); EMUSYNC; }
				if (blockSize >=   4) { smem[tid] = mySum = min(mySum, smem[tid +  2]); EMUSYNC; }
				if (blockSize >=   2) { smem[tid] = mySum = min(mySum, smem[tid +  1]); EMUSYNC; }
			}
	}
}
