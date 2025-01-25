  MD5GG (d, a, b, c, b14, MD5S22, 0xc33707d6); /* 26 */
  MD5GG (c, d, a, b, b3, MD5S23, 0xf4d50d87); /* 27 */
  MD5GG (b, c, d, a, b8, MD5S24, 0x455a14ed); /* 28 */
  MD5GG (a, b, c, d, b13, MD5S21, 0xa9e3e905); /* 29 */
  MD5GG (d, a, b, c, b2, MD5S22, 0xfcefa3f8); /* 30 */
  MD5GG (c, d, a, b, b7, MD5S23, 0x676f02d9); /* 31 */
  MD5GG (b, c, d, a, b12, MD5S24, 0x8d2a4c8a); /* 32 */

  /* Round 3 */
  MD5HH (a, b, c, d, b5, MD5S31, 0xfffa3942); /* 33 */
  MD5HH (d, a, b, c, b8, MD5S32, 0x8771f681); /* 34 */
  MD5HH (c, d, a, b, b11, MD5S33, 0x6d9d6122); /* 35 */
  MD5HH (b, c, d, a, b14, MD5S34, 0xfde5380c); /* 36 */
  MD5HH (a, b, c, d, b1, MD5S31, 0xa4beea44); /* 37 */
  MD5HH (d, a, b, c, b4, MD5S32, 0x4bdecfa9); /* 38 */
  MD5HH (c, d, a, b, b7, MD5S33, 0xf6bb4b60); /* 39 */
  MD5HH (b, c, d, a, b10, MD5S34, 0xbebfbc70); /* 40 */
  MD5HH (a, b, c, d, b13, MD5S31, 0x289b7ec6); /* 41 */
  MD5HH (d, a, b, c, b0, MD5S32, 0xeaa127fa); /* 42 */
  MD5HH (c, d, a, b, b3, MD5S33, 0xd4ef3085); /* 43 */
  MD5HH (b, c, d, a, b6, MD5S34,  0x4881d05); /* 44 */
  MD5HH (a, b, c, d, b9, MD5S31, 0xd9d4d039); /* 45 */

  // These are done after the initial "a" check.
  // MD5HH (d, a, b, c, b12, MD5S32, 0xe6db99e5); /* 46 */
  // MD5HH (c, d, a, b, b15, MD5S33, 0x1fa27cf8); /* 47 */
  // MD5HH (b, c, d, a, b2, MD5S34, 0xc4ac5665); /* 48 */

  // Round 4 and the final constants go byebye!
}



// Reverses the MD5
__device__ void MD5_Reverse(UINT4 b0, UINT4 b1, UINT4 b2, UINT4 b3, UINT4 b4, UINT4 b5, UINT4 b6, UINT4 b7,
               UINT4 b8, UINT4 b9, UINT4 b10, UINT4 b11, UINT4 b12, UINT4 b13, UINT4 b14, UINT4 b15,
               UINT4 &a, UINT4 &b, UINT4 &c, UINT4 &d,
               int data_length_bytes, uint32_t *DEVICE_Hashes_32) {


    a = DEVICE_Hashes_32[0];
    b = DEVICE_Hashes_32[1];
    c = DEVICE_Hashes_32[2];
    d = DEVICE_Hashes_32[3];


    a -= 0x67452301;
    b -= 0xefcdab89;
    c -= 0x98badcfe;
    d -= 0x10325476;
