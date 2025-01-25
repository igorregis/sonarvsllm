
	// Update H
	W.ReplaceByTranspose();
	DeviceMatrix<cudafloat>::Multiply(W, V, deltaH);
	W.MultiplyBySelfTranspose(aux);
	//DeviceMatrix<cudafloat>::Multiply(aux, H, deltaH, CUDA_VALUE(-1.0), CUDA_VALUE(1.0));
	DeviceMatrix<cudafloat>::Multiply(aux, H, deltaH2);
	W.ReplaceByTranspose();
	//UpdateMatrixNMFadditive<<<NumberBlocks(H.Elements(), SIZE_BLOCKS_NMF), SIZE_BLOCKS_NMF>>>(H.Pointer(), deltaH.Pointer(), CUDA_VALUE(0.001), H.Elements());
	UpdateMatrix_AE<<<NumberBlocks(H.Elements(), SIZE_BLOCKS_NMF), SIZE_BLOCKS_NMF>>>(H.Pointer(), deltaH.Pointer(), deltaH2.Pointer(), H.Elements());
