    shrQAFinishExit(argc, (const char **)argv, (iRetVal==0 ? QA_PASSED : QA_FAILED));
}

///////////////////////////////////////////////////////////////////////////////
//Parse args, run the appropriate tests
///////////////////////////////////////////////////////////////////////////////
int runTest(const int argc, const char **argv)
{
    int start = DEFAULT_SIZE;
    int end = DEFAULT_SIZE;
    int startDevice = 0;
    int endDevice = 0;
    int increment = DEFAULT_INCREMENT;
    testMode mode = QUICK_MODE;
    bool htod = false;
    bool dtoh = false;
    bool dtod = false;
    bool wc = false;
    char *modeStr;
    char *device = NULL;
    printMode printmode = USER_READABLE;
    char *memModeStr = NULL;
    memoryMode memMode = PAGEABLE;

    //process command line args
    if(shrCheckCmdLineFlag( argc, argv, "help"))
    {
        printHelp();
        return -1;
    }
