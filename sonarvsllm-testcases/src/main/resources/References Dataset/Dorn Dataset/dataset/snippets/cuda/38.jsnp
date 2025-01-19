#include "../../inc/GRT_CUDA_device/CUDA_Load_Store_Registers.h"


#define CREATE_SHA1_GEN_KERNEL(length) \
__global__ void MakeSHA1ChainLen##length(unsigned char *InitialPasswordArray, unsigned char *OutputHashArray, \
    uint32_t PasswordSpaceOffset, uint32_t StartChainIndex, uint32_t StepsToRun, uint32_t charset_offset) { \
    const int pass_length = length; \
    uint32_t CurrentStep, PassCount, password_index; \
    uint32_t b0,b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15; \
    uint32_t a,b,c,d,e; \
    uint32_t *InitialArray32; \
    uint32_t *OutputArray32; \
    InitialArray32 = (uint32_t *)InitialPasswordArray; \
    OutputArray32 = (uint32_t *)OutputHashArray; \
    __shared__ char charset[512]; \
    copySingleCharsetToShared(charset, SHA1_Generate_Device_Charset_Constant); \
    password_index = ((blockIdx.x*blockDim.x + threadIdx.x) + (PasswordSpaceOffset * SHA1_Generate_Device_Number_Of_Threads)); \
    if (password_index >= SHA1_Generate_Device_Number_Of_Chains) { \
        return; \
    } \
    clearB0toB15(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15); \
    LoadMD5RegistersFromGlobalMemory(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15, \
        InitialArray32, SHA1_Generate_Device_Number_Of_Chains, password_index, pass_length); \
    for (PassCount = 0; PassCount < StepsToRun; PassCount++) { \
        CurrentStep = PassCount + StartChainIndex; \
        b15 = ((pass_length * 8) & 0xff) << 24 | (((pass_length * 8) >> 8) & 0xff) << 16; \
        SetCharacterAtPosition(0x80, pass_length, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15 ); \
        SHA_TRANSFORM(a, b, c, d, e, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15); \
        a = reverse(a);b = reverse(b);c = reverse(c);d = reverse(d);e = reverse(e); \
        clearB0toB15(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15); \
        reduceSingleCharsetNormal(b0, b1, b2, a, b, c, d, CurrentStep, charset, charset_offset, pass_length, SHA1_Generate_Device_Table_Index); \
        charset_offset++; \
        if (charset_offset >= SHA1_Generate_Device_Charset_Length) { \
            charset_offset = 0; \
        } \
    } \
    if (CurrentStep >= (SHA1_Generate_Device_Chain_Length - 1)) { \
        OutputArray32[0 * SHA1_Generate_Device_Number_Of_Chains + password_index] = a; \
        OutputArray32[1 * SHA1_Generate_Device_Number_Of_Chains + password_index] = b; \
        OutputArray32[2 * SHA1_Generate_Device_Number_Of_Chains + password_index] = c; \
        OutputArray32[3 * SHA1_Generate_Device_Number_Of_Chains + password_index] = d; \
        OutputArray32[4 * SHA1_Generate_Device_Number_Of_Chains + password_index] = e; \
    } \
    else { \
    SaveMD5RegistersIntoGlobalMemory(b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15, \
        InitialArray32, SHA1_Generate_Device_Number_Of_Chains, password_index, pass_length); \
    } \
}


