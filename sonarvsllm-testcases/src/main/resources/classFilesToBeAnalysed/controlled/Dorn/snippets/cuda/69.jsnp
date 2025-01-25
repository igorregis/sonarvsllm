        MD4HH (b, c, d, a, b12, MD4S34); \
        MD4HH (a, b, c, d, b2, MD4S31); \
        MD4HH (d, a, b, c, b10, MD4S32); \
        MD4HH (c, d, a, b, b6, MD4S33); \
        MD4HH (b, c, d, a, b14, MD4S34); \
        MD4HH (a, b, c, d, b1, MD4S31); \
        if (pass_len > 6) { \
            MD4HH (d, a, b, c, b9, MD4S32); \
            MD4HH (c, d, a, b, b5, MD4S33); \
            MD4HH (b, c, d, a, b13, MD4S34); \
            MD4HH (a, b, c, d, b3, MD4S31); \
            if (pass_len > 14) { \
                MD4HH (d, a, b, c, b11, MD4S32); \
                MD4HH (c, d, a, b, b7, MD4S33); \
            } \
        } \
        checkHash128LENTLM(a, b, c, d, b0, b1, b2, b3, \
            b4, b5, b6, b7, sharedBitmap, \
            deviceGlobalBitmapAPlainNTLM, deviceGlobalBitmapBPlainNTLM, \
            deviceGlobalBitmapCPlainNTLM, deviceGlobalBitmapDPlainNTLM, \
            deviceGlobalFoundPasswordsPlainNTLM, deviceGlobalFoundPasswordFlagsPlainNTLM, \
            deviceGlobalHashlistAddressPlainNTLM, numberOfHashesPlainNTLM, \
            passwordLengthPlainNTLM); \
        if (charsetLengthsPlainNTLM[1] == 0) { \
                makeMFNSingleIncrementorsNTLM##pass_len (sharedCharsetPlainNTLM, sharedReverseCharsetPlainNTLM, sharedCharsetLengthsPlainNTLM); \
        } else { \
                makeMFNMultipleIncrementorsNTLM##pass_len (sharedCharsetPlainNTLM, sharedReverseCharsetPlainNTLM, sharedCharsetLengthsPlainNTLM); \
        } \
        password_count++; \
    } \
    storeNTLMPasswords32(deviceGlobalStartPasswords32PlainNTLM, deviceNumberThreadsPlainNTLM, pass_len); \
}


MAKE_MFN_NTLM_KERNEL1_8LENGTH(1);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(2);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(3);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(4);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(5);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(6);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(7);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(8);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(9);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(10);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(11);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(12);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(13);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(14);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(15);
MAKE_MFN_NTLM_KERNEL1_8LENGTH(16);
