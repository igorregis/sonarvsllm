    r.x = dot(v, M.m[0]);
    r.y = dot(v, M.m[1]);
    r.z = dot(v, M.m[2]);
    r.w = 1.0f;
    return r;
}


__global__ void
d_tt_backproject_ray(float *d_projection, float *d_output, uint3 volumeVoxels, float3 sourcePosition, float3 volumeSize, uint2 detectorPixels, float tStep, int interpolation)
{
    const uint   image_width_pixels  = detectorPixels.x; 
    const uint   image_height_pixels = detectorPixels.y; 
    const float3 volume_size = volumeSize; 
    const uint3  volume_voxels = volumeVoxels;
    const float3 source_position = sourcePosition;

    const float  tstep    = tStep;
    const int    maxSteps = MAX_STEPS; //(volume_size.x^2+volume_size.y^2+volume_size.z^2)^0.5f/tStep;       //diagonal of the bounding box
    const float3 boxMin = make_float3(0.0f, 0.0f, 0.0f);
    const float3 boxMax = make_float3(volume_size.x, volume_size.y, volume_size.z);

    //x and y index detector pixel
    uint x = blockIdx.x*blockDim.x + threadIdx.x;
    uint y = blockIdx.y*blockDim.y + threadIdx.y;
    if ((x >= image_width_pixels) || (y >= image_height_pixels)) return;

    //u and v are in normalized detector pixel [0,0]->[1,1]
    float u = (x / (float) image_width_pixels);
    float v = (y / (float) image_height_pixels);
