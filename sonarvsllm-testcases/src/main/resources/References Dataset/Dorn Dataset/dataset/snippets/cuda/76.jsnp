	if(sortedSeqs){
		pFreeHost(sortedSeqs);
	}
	//free database
	if(dbSeqs){
		for(i = 0; i < numSeqs; i++){
			free(dbSeqs[i]);
			free(dbSeqsName[i]);
		}
		free(dbSeqs);
		free(dbSeqsName);
		free(dbSeqsLen);
		free(dbSeqsAlignedLen);
	}
}
void CSearch::run ()
{
    //read in the input sequence
    char* query = params->getQueryFile();
	char* mat = params->getSubMatrixName();
    char* db = params->getDbFile();

	//get the gap penalties
	gapOpen = params->getGapOpen();
	gapExtend = params->getGapExtend();

	printf("/**********************************/\n");
	printf("\tModel:\t\t\t%s\n", params->isUseSIMTModel() == true ? "SIMT scalar" : "SIMD vectorized");
	printf("\tScoring matrix:\t\t\t%s\n", mat);
	printf("\tGap Open penalty:\t\t%d\n", gapOpen);
	printf("\tGap Extension penalty:\t\t%d\n", gapExtend);
	printf("/**********************************/\n");

	//loading substitution matrix
	params->getMatrix (matrix);

	//load database
	loaddb(db);

	//generate the object
    dbsearch(query);    

	printf("Finished!\n");
}

//for quick sort
int	CSearch::compar_ascent(const void * va, const void * vb)
{
	const SeqEntry* a = (const SeqEntry*)va;
	const SeqEntry* b = (const SeqEntry*)vb;
