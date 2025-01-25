    password_index = ((blockIdx.x*blockDim.x + threadIdx.x) + (PasswordSpaceOffset * MD5_Generate_Device_Number_Of_Threads)); \
    if (password_index >= MD5_Generate_Device_Number_Of_Chains) { \
        return; \
    } \
    clearB0toB15(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15); \
    LoadMD5RegistersFromGlobalMemory(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15, \
        InitialArray32, MD5_Generate_Device_Number_Of_Chains, password_index, pass_length); \
    for (PassCount = 0; PassCount < StepsToRun; PassCount++) { \
        CurrentStep = PassCount + StartChainIndex; \
        padMDHash(pass_length, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
        CUDA_MD5(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, a, b, c, d); \
        reduceSingleCharsetNormal(b0, b1, b2, a, b, c, d, CurrentStep, charset, charset_offset, pass_length, MD5_Generate_Device_Table_Index); \
        charset_offset++; \
        if (charset_offset >= MD5_Generate_Device_Charset_Length) { \
            charset_offset = 0; \
        } \
    } \
    if (CurrentStep >= (MD5_Generate_Device_Chain_Length - 1)) { \
        OutputArray32[0 * MD5_Generate_Device_Number_Of_Chains + password_index] = a; \
        OutputArray32[1 * MD5_Generate_Device_Number_Of_Chains + password_index] = b; \
        OutputArray32[2 * MD5_Generate_Device_Number_Of_Chains + password_index] = c; \
        OutputArray32[3 * MD5_Generate_Device_Number_Of_Chains + password_index] = d; \
    } \
    else { \
    SaveMD5RegistersIntoGlobalMemory(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15, \
        InitialArray32, MD5_Generate_Device_Number_Of_Chains, password_index, pass_length); \
    } \
}


