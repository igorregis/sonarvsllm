    float3 tmin = fminf(ttop, tbot);
    float3 tmax = fmaxf(ttop, tbot);

    // find the largest tmin and the smallest tmax
    float largest_tmin = fmaxf(fmaxf(tmin.x, tmin.y), fmaxf(tmin.x, tmin.z));
    float smallest_tmax = fminf(fminf(tmax.x, tmax.y), fminf(tmax.x, tmax.z));

    *tnear = largest_tmin;
    *tfar = smallest_tmax;

    return smallest_tmax > largest_tmin;
}

// transform vector by matrix (no translation)
__device__
float3 mul(const float3x4 &M, const float3 &v)
{
    float3 r;
    r.x = dot(v, make_float3(M.m[0]));
    r.y = dot(v, make_float3(M.m[1]));
    r.z = dot(v, make_float3(M.m[2]));
    return r;
}

// transform vector by matrix with translation
__device__
float4 mul(const float3x4 &M, const float4 &v)
{
    float4 r;
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
