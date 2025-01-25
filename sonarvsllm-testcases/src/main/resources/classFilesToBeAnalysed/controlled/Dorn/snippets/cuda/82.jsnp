                        p101 = p001; p101.z+=1;
                        p110 = p100; p110.y+=1;
                        p111 = p110; p111.x+=1; 
                        float3 d;
                        d.x = pos.x-p000.x;
                        d.y = pos.y-p000.y;
                        d.z = pos.z-p000.z; 
                        float w000, w001, w010, w011, w100, w101, w110, w111;                      
                        w000 = (1-d.z)*(1-d.y)*(1-d.x)*proj;
                        w001 = (1-d.z)*(1-d.y)*( d.x )*proj;
                        w010 = (1-d.z)*( d.y )*(1-d.x)*proj;
                        w011 = (1-d.z)*( d.y )*( d.x )*proj;
                        w100 = ( d.z )*(1-d.y)*(1-d.x)*proj;
                        w101 = ( d.z )*(1-d.y)*( d.x )*proj;
                        w110 = ( d.z )*( d.y )*(1-d.x)*proj;
                        w111 = ( d.z )*( d.y )*( d.x )*proj;
                        d_output[p000.z*volumeVoxels.y*volumeVoxels.x+p000.y*volumeVoxels.x+p000.x] += w000;
                        d_output[p001.z*volumeVoxels.y*volumeVoxels.x+p001.y*volumeVoxels.x+p001.x] += w001;
                        d_output[p010.z*volumeVoxels.y*volumeVoxels.x+p010.y*volumeVoxels.x+p010.x] += w010;
                        d_output[p011.z*volumeVoxels.y*volumeVoxels.x+p011.y*volumeVoxels.x+p011.x] += w011;
                        d_output[p100.z*volumeVoxels.y*volumeVoxels.x+p100.y*volumeVoxels.x+p100.x] += w100;
                        d_output[p101.z*volumeVoxels.y*volumeVoxels.x+p101.y*volumeVoxels.x+p101.x] += w101;
                        d_output[p110.z*volumeVoxels.y*volumeVoxels.x+p110.y*volumeVoxels.x+p110.x] += w110;
                        d_output[p111.z*volumeVoxels.y*volumeVoxels.x+p111.y*volumeVoxels.x+p111.x] += w111;
        }

        __syncthreads();

        t += tstep;
        pos += step;
