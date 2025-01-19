      for (hash_to_check = 0; hash_to_check < numberOfPasswords; hash_to_check++) { \
          clearB0toB15(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
          LoadPasswordAtPosition(pass_length, 0, sharedCharset, \
            p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, \
            b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
          LoadSalt(pass_length, hash_to_check, constantSalts, constantSaltLengths, \
            b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
          SetCharacterAtPosition(0x80, pass_length + constantSaltLengths[hash_to_check], \
            b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
          b15 = (pass_length + constantSaltLengths[hash_to_check]) * 8 << 24; \
          SHA_TRANSFORM(a, b, c, d, e, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
        checkHashMultiSHA1(pass_length, sharedBitmap, DEVICE_HashTable, numberOfPasswords, \
		DEVICE_Hashes_32, success, OutputPassword,  \
		p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15,  \
		b0, b1, b2, b3, b4, a, b, c, d, e, b5); \
      } \
  password_count++;  \
  incrementCounters##length##Multi(); \
  } \
}


CUDA_SSHA_KERNEL_CREATE(1)
CUDA_SSHA_KERNEL_CREATE(2)
CUDA_SSHA_KERNEL_CREATE(3)
CUDA_SSHA_KERNEL_CREATE(4)
CUDA_SSHA_KERNEL_CREATE(5)
CUDA_SSHA_KERNEL_CREATE(6)
CUDA_SSHA_KERNEL_CREATE(7)
CUDA_SSHA_KERNEL_CREATE(8)
CUDA_SSHA_KERNEL_CREATE(9)
CUDA_SSHA_KERNEL_CREATE(10)
CUDA_SSHA_KERNEL_CREATE(11)
CUDA_SSHA_KERNEL_CREATE(12)
CUDA_SSHA_KERNEL_CREATE(13)
CUDA_SSHA_KERNEL_CREATE(14)
CUDA_SSHA_KERNEL_CREATE(15)
CUDA_SSHA_KERNEL_CREATE(16)

// Copy the shared variables to the host
extern "C" void copySSHADataToConstant(char *hostCharset, int charsetLength,
        unsigned char *hostCharsetLengths, unsigned char *hostSharedBitmap, int threadId,
        unsigned char *salts, unsigned char *saltLengths, uint32_t numberOfHashes) {
    //printf("Thread %d in CHHashTypeMD5.cu, copyMD5DataToCharset()\n", threadId);
    CUDA_SAFE_CALL(cudaMemcpyToSymbol(deviceCharset, hostCharset, (MAX_CHARSET_LENGTH * charsetLength)));
    CUDA_SAFE_CALL(cudaMemcpyToSymbol(constantBitmap, hostSharedBitmap, 8192));
    CUDA_SAFE_CALL(cudaMemcpyToSymbol(charsetLengths, hostCharsetLengths, 16));
    CUDA_SAFE_CALL(cudaMemcpyToSymbol(constantSalts, salts, numberOfHashes * 32));
    CUDA_SAFE_CALL(cudaMemcpyToSymbol(constantSaltLengths, saltLengths, numberOfHashes));
}
