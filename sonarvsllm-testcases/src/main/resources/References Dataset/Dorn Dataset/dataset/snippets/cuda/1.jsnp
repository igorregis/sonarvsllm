
/* *************************************************************** */
/* *************************************************************** */
void reg_affine_positionField_gpu(	mat44 *affineMatrix,
					nifti_image *targetImage,
					float4 **array_d)
{
	int3 imageSize = make_int3(targetImage->nx,targetImage->ny,targetImage->nz);
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_ImageSize,&imageSize,sizeof(int3)));
	CUDA_SAFE_CALL(cudaMemcpyToSymbol(c_VoxelNumber,&(targetImage->nvox),sizeof(int)));
