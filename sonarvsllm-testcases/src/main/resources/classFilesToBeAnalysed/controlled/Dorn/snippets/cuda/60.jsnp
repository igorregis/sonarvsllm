        fprintf(stderr, "Unable to pop the context in cudaforward init!\n");
        return -1;
    }

    fprintf(stderr, "cudaforwarder initialized !\n");
    return 0;
}
 
/* For the libvirt data transfers using datatransfer-serial.h, this handler function is
 * executed as its own thread. That means, as it is executed, it will not have a context.
 * This creates a problem in cases where the guest calls driver api functions. This is solved
 * by creating a main cuda handler thread. The CUDA API can the manage contexts itself, 
 * as is normal. Please see the comment in the main cuda handler function above. 
 *
 * We have to manually set and unset the context on enter and leave to this function.
 *
 * For now, it is garuanteed that only one handler exists at a time (to avoid race conditions
 * and a lot of work..) */
static void cudaFwdMsgHandler(void *msg, uint32_t msg_sz, void** respMsg, uint32_t *respMsgSz){

    if(msg == NULL) // Handle empty messages
        return;

    inArgs->msg = msg;
    inArgs->msgSz = msg_sz;
    sem_post(&sem_in);
    sem_wait(&sem_out);

    *respMsg = outArgs->respMsg;
    *respMsgSz = outArgs->respMsgSz;
