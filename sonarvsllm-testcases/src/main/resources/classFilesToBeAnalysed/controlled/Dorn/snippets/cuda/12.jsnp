                        sum_attenuation += g_attenuation[index];
			g_backprojection[index] = s_sino[threadIdx.x]*exp(-sum_attenuation);
                        //g_backprojection[index] = g_sinogram[tid];
			index += pixelNumber;
		}
	}
	return;  
}

