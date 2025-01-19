    tag = 'Feature enhancements' if version.endswith('.0') else 'Bug fixes'
    release = dict(version=version, changelog=changelog, tag_list=tag)
    body = codecs.encode(simplejson.dumps(dict(auth_code=auth_code, 
                                               release=release)))
    path = '/projects/taskcoach/releases.json'
    host = 'freecode.com'
    if options.dry_run:
        print 'Skipping announcing "%s" on %s.'%(release, host)
    else:
        httpPostRequest(host, path, body, 'application/json', ok=201)
