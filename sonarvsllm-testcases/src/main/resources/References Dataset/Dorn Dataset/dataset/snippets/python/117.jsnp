
    if path != '.':
        for name in os.listdir(path):
            if name.startswith('Release'):
                fname = os.path.join(path, name)
                if os.path.isdir(fname):
                    print '<h1>Bug fixes (from %s)</h1>' % name
                    listPath(fname)

    print '<a href="http://www.taskcoach.org/download.html>Back to Task Coach downloads</a>'
