 */
void write_data(FILE *ofp, float *h_o, int channels, int bins)
{
	int ppc = bins/channels;

	for (int m = 0; m < channels; m++)
	{
		for (int i = 0; i <= ppc/2; i++)
		{
			fprintf(ofp, "%f\n", h_o[m*ppc + i]);
		}
		fprintf(ofp, "\n");
	}
}

/*
 * Function to discard the last half of each channel from the spectrum
 */
void trim_spectrum(float *h_o, float *h_h, int channels, int bins, float norm)
{
	int ppc = bins/channels;

	for (int m = 0; m < channels; m++)
	{
		for (int i = 0; i <= ppc/2; i++)
		{
			h_h[m*ppc/2 + i] = h_o[m*ppc + i]/norm;
		}
	}
}
