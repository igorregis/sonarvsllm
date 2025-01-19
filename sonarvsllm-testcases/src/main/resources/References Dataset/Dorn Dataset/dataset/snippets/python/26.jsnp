	for exe in executables:
		exe.applyFixes(fixes, log)
	# step 5: write log
	logfile = file(os.path.join(bundle, "Contents/osxtools.log"), "a")
	logfile.write("ingest at " + datetime.now().isoformat(" ") + "\n")
	for e in log:
		logfile.write(e + "\n")
	logfile.close()
		

def createSymlinks(bundle, links):
	currDir = os.getcwd()
	for lnk,tar in links:
		print "chdir to " + os.path.join(bundle, os.path.dirname(lnk))
		os.chdir(os.path.join(bundle, os.path.dirname(lnk)))
		print "symlink " + os.path.basename(lnk) + " -> " + tar
		os.symlink(tar, os.path.basename(lnk))
	os.chdir(currDir)
	
	
def relinkOld(FILE, LIBDIR, INSTALLDIR):
	#LIBS=`otool -L $FILE | sed 's/\([^(]*\)(.*)/\1/g'`
	#for LIB in $LIBS ; do
	#	LNAM=`basename $LIB`
	#	if [ $FILE -ef $LIBDIR/$LNAM ] ; then
	#		install_name_tool -id $INSTALLDIR$LNAM $FILE
	#	elif [ -e $LIBDIR/$LNAM ] ; then
	#		install_name_tool -change $LIB $INSTALLDIR$LNAM $FILE
	pass
