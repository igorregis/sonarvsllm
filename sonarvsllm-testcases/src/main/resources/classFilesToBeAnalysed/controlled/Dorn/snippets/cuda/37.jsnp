            free(toFree);
        }

        search->previous->next = search->next;
        search->next->previous = search->previous;
        free((void*) search);

        return 0;
    }
    else{
        return -1;
    }

}


static FILE *fp;

struct iolistener_common *ioc;

static void cudaFwdMsgHandler(void *msg, uint32_t msg_sz, void** respMsg, uint32_t *resMsgSz);
static void cudaFwdMemTransfHandler(void* msg, uint32_t msg_sz, void** respMsg, uint32_t *respMsgSz);

int cudaforward_init(FILE *cfp){

    ioc = registerPortHandler(cudaFwdMsgHandler, IOLISTENER_IO_DATA_START, IOLISTENER_IO_CONTROL_START);
    if(ioc == NULL)
        return -1;

 //   fp = cfp;
 //   fprintf(fp, "cudaforwarder initialized !\n");

    dummyLaunchConf = 
        (struct kernLaunchConfig*) malloc(sizeof(struct kernLaunchConfig));
    dummyFatBin = 
        (struct fatbinaryLL*) malloc(sizeof(struct fatbinaryLL));
    dummyKernLaunch = 
        (struct kernLaunchLL*) malloc(sizeof(struct kernLaunchLL));
    
    dummyLaunchConf->next = dummyLaunchConf;
    dummyLaunchConf->previous = dummyLaunchConf;

    dummyFatBin->next = dummyFatBin;
    dummyFatBin->previous = dummyFatBin;

    dummyKernLaunch->next = dummyKernLaunch;
    dummyKernLaunch->previous = dummyKernLaunch;

    if(dummyLaunchConf == 0 || dummyFatBin == 0 || dummyKernLaunch == 0)
        return -1;
