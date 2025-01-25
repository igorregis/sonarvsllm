				donor = solutions[bestPosID] + f * (solutions[r1+tid] - solutions[r2+tid]);
			}
			break;

		default:break;
		}

		// crop donor
		cropPosition(bounds, donor, blockIdx.x, tid, actualSolutionSize);

		// b. CROSSOVER
		//    use the selected algorithm, binomial or exponential,
		//    to calculate the trial vector
		switch (crossover)

		{
		case DE_BINOMIAL:
			trialVectors[posID] =  (tid==jr ||  curand_uniform(&localState)<=cr) ? donor : solutions[posID];
			break;
		case DE_EXPONENTIAL:
			trialVectors[posID] =  (overflow) ? ((tid>=jr||tid<L) ? donor : solutions[posID]) : ((tid>=jr&&tid<L) ? donor : solutions[posID]);
			break;

		default:break;
		}
	}

	randStates[posID] = localState;
}

__global__ void de_selection (float*  solutions, float*  trialVectors, float* fitnesses, float* trialFitnesses,
							unsigned int problemDimension, unsigned int actualSolutionSize)
{
	uint32_t  tid = threadIdx.x;
	const uint32_t solutionPosID = IMUL(blockIdx.x, gridDim.y)+blockIdx.y;
	uint32_t posID = IMUL( solutionPosID, actualSolutionSize) + tid;

	__shared__ bool update;

	if (tid == 0)
	{
		float trialFitness = trialFitnesses[solutionPosID];
		if (trialFitness BETTER_THAN fitnesses[solutionPosID])
		{
			update = true;
			fitnesses[solutionPosID] = trialFitness;
		}
		else update = false;
	}
	__syncthreads ();
