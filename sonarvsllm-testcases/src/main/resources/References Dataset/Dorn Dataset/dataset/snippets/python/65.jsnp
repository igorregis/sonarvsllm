	
	
def ingest(bundle, exceptions=[], strippedFrameworks=[]):
	"Moves all needed non-System libraries inside the bundle and fixes links"
	# step 1: find all executables
	executables = MachO.findExecutables(bundle)
	# find the bundle executable
	pat = re.compile("executable")
	exePath = ""
	for exe in executables:
		if pat.match(exe.Kind):
			exePath = os.path.dirname(exe.Location)
			print "using @executable_path=" + exePath
			break
	# step 2: find all dependencies
	fixes = findDependencies(executables, exePath)
	# step 3: move all libraries which are not excepted
	log = []
	frameworks = os.path.join(bundle, "Contents/Frameworks")
	if not os.path.exists(frameworks):
		log.append(">> mkdir " + frameworks)
		os.makedirs(frameworks, 0755)
	for k,fix in fixes.items():
		if fix.Location in exceptions or fix.Link in exceptions:
			del fixes[k]
		else:
			stripFW = fix.Location in strippedFrameworks
			executables.extend(fix.moveLibrary(bundle, stripFW, log))

	# step 3.5: copy aspell dictionaries, hacked for aspell via macports for now, #7371
	aspellsrcpath = "/opt/local/share/aspell"
	if os.path.exists(aspellsrcpath):
		aspelldestpath = os.path.join(bundle, "Contents/share/aspell")
		if not os.path.exists(aspelldestpath):
			log.append(">> mkdir " + aspelldestpath)
			os.makedirs(aspelldestpath, 0755)      
		if os.path.exists(aspelldestpath):
			log.append(">> copying aspell dictionaries")
			print "copying aspell dictionaries"
			copy_tree(aspellsrcpath, aspelldestpath)

	# step 4: fix all executables
	for exe in executables:
		exe.applyFixes(fixes, log)
	# step 5: write log
	logfile = file(os.path.join(bundle, "Contents/osxtools.log"), "a")
	logfile.write("ingest at " + datetime.now().isoformat(" ") + "\n")
	for e in log:
		logfile.write(e + "\n")
	logfile.close()
