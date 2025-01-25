        p44, p45, p46, p47, \
        b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
  CUDA_GENERIC_MD4(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15,\
        a, b, c, d, (pass_length * 4)); \
  checkDuplicatedHashMultiLong(pass_length, sharedBitmap, DEVICE_HashTable, numberOfPasswords, \
		DEVICE_Hashes_32, success, OutputPassword, \
		p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, \
                p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, \
                p30, p31, p32, p33, p34, p35, p36, p37, p38, p39, p40, p41, p42, p43, \
                p44, p45, p46, p47, \
		a, b, c, d, b0, b1, b2, b3, b4, b5); \
  password_count++; \
  incrementCounters##length##Multi(); \
  } \
}


DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(1)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(2)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(3)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(4)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(5)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(6)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(7)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(8)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(9)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(10)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(11)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(12)
DUPLICATEDNTLM_CUDA_KERNEL_CREATE_LONG(13)
