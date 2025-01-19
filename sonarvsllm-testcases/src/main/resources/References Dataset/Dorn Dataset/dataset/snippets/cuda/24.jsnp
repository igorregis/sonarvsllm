__global__ void CUDA_MD4_Search_##length (unsigned char *OutputPassword, unsigned char *success,  \
			    int charsetLen, uint32_t numberOfPasswords, struct start_positions *DEVICE_Start_Positions, \
				unsigned int count, unsigned char * DEVICE_Hashes, unsigned char *DEVICE_HashTable) { \
  const int pass_length = length; \
  uint32_t b0,b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15; \
  uint32_t a,b,c,d; \
  uint32_t thread_index = blockIdx.x*blockDim.x + threadIdx.x; \
  uint32_t *DEVICE_Hashes_32 = (uint32_t *)DEVICE_Hashes; \
  unsigned char p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15; \
  UINT4 password_count = 0; \
  __shared__ __align__(16) unsigned char sharedCharset[MAX_CHARSET_LENGTH * MAX_PASSWORD_LEN]; \
  __shared__ __align__(16) unsigned char sharedBitmap[8192]; \
  __shared__ unsigned char sharedLengths[16]; \
  copyCharsetAndBitmap(sharedCharset, sharedBitmap, sharedLengths, charsetLen, pass_length); \
  loadStartPositions(pass_length, thread_index, DEVICE_Start_Positions,  \
		   p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15); \
  while (password_count < count) { \
  initMD(pass_length, sharedCharset, \
  	p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, \
	b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15);	 \
  CUDA_MD4(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, a, b, c, d); \
  checkHashMulti(pass_length, sharedBitmap, DEVICE_HashTable, numberOfPasswords, \
		DEVICE_Hashes_32, success, OutputPassword, \
		p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, \
		a, b, c, d, b0, b1, b2, b3, b4, b5); \
  password_count++; \
  incrementCounters##length##Multi(); \
  } \
}

