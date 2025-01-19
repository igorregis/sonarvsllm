	x |= x >> 2;
	x |= x >> 4;
	x |= x >> 8;
	x |= x >> 16;
	return ++x;
}

KERNEL reduce2(int *g_idata,int *g_odata, int *g_idata_old, unsigned int n)
{
	extern __shared__ int sdata2[];
