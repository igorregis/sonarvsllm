	if(tid<c_ActiveVoxelNumber){

		//Get the real world position in the source space
		float4 realPosition = tex1Dfetch(positionFieldTexture,tid);

		//Get the voxel-based position in the source space
		float3 voxelPosition;
		float4 matrix = tex1Dfetch(sourceMatrixTexture,0);
		voxelPosition.x =	matrix.x*realPosition.x + matrix.y*realPosition.y  +
					matrix.z*realPosition.z  +  matrix.w;
		matrix = tex1Dfetch(sourceMatrixTexture,1);
		voxelPosition.y =	matrix.x*realPosition.x + matrix.y*realPosition.y  +
					matrix.z*realPosition.z  +  matrix.w;
		matrix = tex1Dfetch(sourceMatrixTexture,2);
		voxelPosition.z =	matrix.x*realPosition.x + matrix.y*realPosition.y  +
					matrix.z*realPosition.z  +  matrix.w;

		int3 sourceImageSize = c_SourceDim;
		if(	-1<voxelPosition.x && voxelPosition.x<=sourceImageSize.x &&
			-1<voxelPosition.y && voxelPosition.y<=sourceImageSize.y &&
			-1<voxelPosition.z && voxelPosition.z<=sourceImageSize.z){
			float3 relativePosition;
			relativePosition.x=(voxelPosition.x+0.5f)/(float)c_SourceDim.x;
			relativePosition.y=(voxelPosition.y+0.5f)/(float)c_SourceDim.y;
			relativePosition.z=(voxelPosition.z+0.5f)/(float)c_SourceDim.z;
			resultArray[tex1Dfetch(maskTexture,tid)]=tex3D(sourceTexture, relativePosition.x, relativePosition.y, relativePosition.z);
		}
		else resultArray[tex1Dfetch(maskTexture,tid)]=c_PaddingValue;
	}
}
