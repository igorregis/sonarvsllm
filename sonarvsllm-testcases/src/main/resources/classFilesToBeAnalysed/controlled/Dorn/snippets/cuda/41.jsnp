#include <stdio.h>

#include "main.h"
#include "data_handling.h"

/*
 * Function to open the input file.
 */
void open_input(FILE **ifp, char *input)
{
	if ((*ifp = fopen(input, "r")) == NULL)
	{
		// Failed to open input file, print an error
		fprintf(stderr, "%s: Failed to open file\n", input);
		exit(EXIT_FAILURE);
	}
}

/*
 * Function to open the output file
 */
void open_output(FILE **ofp, char *output)
{
	if ((*ofp = fopen(output, "w")) == NULL)
	{
		// Failed to open output file, print an error
		fprintf(stderr, "%s: Failed to open file\n", output);
		exit(EXIT_FAILURE);
	}
}
