





KERNEL CalculateNetworkActivation(cudafloat* output, cudafloat* Sample,int Length,cudafloat* dCenters,int NumCenters,cudafloat* dWeights,int NumClasses,cudafloat* dWidths,float scaling_factor){

	int idnx = blockIdx.x*blockDim.x + threadIdx.x;
	int idny = blockIdx.y*blockDim.y + threadIdx.y;
