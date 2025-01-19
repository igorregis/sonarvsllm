{
    numSeqs = 0;
    numThreshold = 0;
    maxSeqLength = 0;
    totalAminoAcids = 0;
    dbSeqsSize = 0;
    dbSeqs = 0;
    dbSeqsLen = 0;
    dbSeqsAlignedLen = 0;
    dbSeqsName = 0;
	sortedSeqs = 0;

	this->params = params;
}

CSearch::~CSearch()
{
	int i;
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
