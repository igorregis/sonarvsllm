#include <stdlib.h>
#include <string.h>
#include <cpgplot.h>

#include "write_data.h"

/*	write_data(ofp, h_o, channels, n/bpc); */
/* PGPLOT interface with autocorrelator */
void write_data_pg(FILE *outfile,float *x,plot_option po,int n_bin)
{

  char msg[80];
  int n,m,echan,bchan;
  float min,max,tmp;
  static float *xvals=NULL;
  static int nx,ny,swapped,n_chan=0;
 
  n_bin=n_bin/2/po.nchan;
  if (po.sp1) {bchan=po.sp1;} else {bchan=0;}
  if (po.sp2) {echan=po.sp2;} else {echan=n_bin-1;}

  if (n_chan!=po.nchan) { 
    n_chan=po.if2-po.if1;
    xvals=(float *) realloc(xvals,n_bin*sizeof(float));
    for (n=0;n<n_bin;n++) xvals[n]=n;
    ny=ceil(sqrt(n_chan));nx=(n_chan+ny-1)/ny;
    //    nx=ceil(sqrt(n_chan));nx=3;ny=nx;
    strcpy(msg,po.device);
    if (cpgbeg(0,msg,nx,ny)!=1) fprintf(stderr, "Error calling PGBEGIN");
    swapped=(echan<bchan?1:0);
  }
  
  if (po.ymin||po.ymax) {min=po.ymin;max=po.ymax;}
  else {min=1e30;max=-min;
  for (n=bchan;n<=echan;n++) for (m=po.if1;m<po.if2;m++) {
      tmp=x[n+2*n_bin*m];
      min=(min<tmp?min:tmp);
      max=(max>tmp?max:tmp);}}

  //cpgsci(1);
  //cpgpanl(1,1);
  //cpgmtxt("T",2,0.5,0.5,po.title);
  
  for (m=0;m<n_chan;m++)
    { 
      cpgsci(1);
      cpgbbuf();
      cpgpanl(m%nx+1,m/nx+1);
      cpgeras();
      cpgvstd();
