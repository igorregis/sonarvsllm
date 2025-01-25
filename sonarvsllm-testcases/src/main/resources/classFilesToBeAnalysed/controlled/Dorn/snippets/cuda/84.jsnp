        e = InitialArray32[4]; \
    } else { \
        a = OutputArray32[0 * SHA1_Candidate_Device_Chain_Length + chain_index]; \
        b = OutputArray32[1 * SHA1_Candidate_Device_Chain_Length + chain_index]; \
        c = OutputArray32[2 * SHA1_Candidate_Device_Chain_Length + chain_index]; \
        d = OutputArray32[3 * SHA1_Candidate_Device_Chain_Length + chain_index]; \
        e = OutputArray32[4 * SHA1_Candidate_Device_Chain_Length + chain_index]; \
    } \
    step_to_calculate = chain_index + StartStep; \
    charset_offset = step_to_calculate % SHA1_Candidate_Device_Charset_Length; \
    clearB0toB15(b0,b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
    reduceSingleCharsetNormal(b0, b1, b2, a, b, c, d, step_to_calculate, charset, charset_offset, pass_length, SHA1_Candidate_Device_Table_Index); \
    step_to_calculate++; \
    charset_offset++; \
    if (charset_offset >= SHA1_Candidate_Device_Charset_Length) { \
        charset_offset = 0; \
    } \
    if ((step_to_calculate + StepsToRun) > SHA1_Candidate_Device_Chain_Length) { \
        last_step_for_iteration = SHA1_Candidate_Device_Chain_Length - 1; \
    } else { \
        last_step_for_iteration = (step_to_calculate + StepsToRun - 1); \
    } \
    for (i = step_to_calculate; i <= last_step_for_iteration; i++) { \
        b15 = ((pass_length * 8) & 0xff) << 24 | (((pass_length * 8) >> 8) & 0xff) << 16; \
        SetCharacterAtPosition(0x80, pass_length, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15 ); \
        SHA_TRANSFORM(a, b, c, d, e, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
        a = reverse(a);b = reverse(b);c = reverse(c);d = reverse(d);e = reverse(e); \
        clearB0toB15(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15); \
        reduceSingleCharsetNormal(b0, b1, b2, a, b, c, d, i, charset, charset_offset, pass_length, SHA1_Candidate_Device_Table_Index); \
        charset_offset++; \
        if (charset_offset >= SHA1_Candidate_Device_Charset_Length) { \
            charset_offset = 0; \
        } \
    } \
    OutputArray32[0 * SHA1_Candidate_Device_Chain_Length + chain_index] = a; \
    OutputArray32[1 * SHA1_Candidate_Device_Chain_Length + chain_index] = b; \
    OutputArray32[2 * SHA1_Candidate_Device_Chain_Length + chain_index] = c; \
    OutputArray32[3 * SHA1_Candidate_Device_Chain_Length + chain_index] = d; \
    OutputArray32[4 * SHA1_Candidate_Device_Chain_Length + chain_index] = e; \
}

CREATE_SHA1_CH_KERNEL(6)
CREATE_SHA1_CH_KERNEL(7)
CREATE_SHA1_CH_KERNEL(8)
CREATE_SHA1_CH_KERNEL(9)
CREATE_SHA1_CH_KERNEL(10)


extern "C" void LaunchSHA1CandidateHashKernel(int PasswordLength, int CUDA_Blocks, int CUDA_Threads,
        unsigned char *DEVICE_End_Hashes, uint32_t ThreadSpaceOffset, uint32_t StartStep, uint32_t StepsToRun) {
