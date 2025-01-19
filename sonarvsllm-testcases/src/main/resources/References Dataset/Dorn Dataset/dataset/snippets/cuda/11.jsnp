
//#define SH(_R, _C) sh[(_R)][(_C)]
#define SH(_R, _C) sh[(_C)][(_R)]

#define SVW(_R, _C) svw[(_R)][(_C)]
//#define SVW(_R, _C) svw[(_C)][(_R)]

KERNEL UpdateW_AD(cudafloat * W, cudafloat * H, cudafloat * V, cudafloat * WH, cudafloat * sumH, int n, int m, int r) {
	__shared__ cudafloat SH(32, 32);
	__shared__ cudafloat SVW(32, 32);
