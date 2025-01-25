	}
	//copy the subsitution matrix, gap opening penalty and gap extending penalty from host to GPU
	cudasw->swMemcpyParameters(matrix, gapOpen,gapExtend);
	//create the channel format descriptor
	cudasw->swCreateChannelFormatDesc();

	//copy the database sequences from host to GPU
	cudasw->cudaInterSeqs = cudasw->swMallocArray(width, height, pChannelFormatKindUnsignedChar4);
	pMemcpyToArray(cudasw->cudaInterSeqs, 0, 0, array, width * height * sizeof(uchar4), pMemcpyHostToDevice);
	pFreeHost(array);	
