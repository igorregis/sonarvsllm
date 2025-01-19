        self.env.topickle(path.join(self.outdir, ENV_PICKLE_FILENAME))
        self.msg('done', nobold=True)

        # global actions
        self.msg('checking consistency...')
        self.env.check_consistency()
        self.msg('creating index...')
        self.env.create_index(self)

        self.prepare_writing()

        if filenames:
            # add all TOC files that may have changed
            filenames_set = set(filenames)
            for filename in filenames:
                for tocfilename in self.env.files_to_rebuild.get(filename, []):
                    filenames_set.add(tocfilename)
            filenames_set.add('contents.rst')
        else:
            # build all
            filenames_set = set(self.env.all_files)

        # write target files
        with collect_env_warnings(self):
            self.msg('writing output...')
            for filename in status_iterator(sorted(filenames_set), green,
                                            stream=self.status_stream):
                doctree = self.env.get_and_resolve_doctree(filename, self)
                self.write_file(filename, doctree)

        # finish (write style files etc.)
        self.msg('finishing...')
        self.finish()
        self.msg('done!')

    def prepare_writing(self):
        raise NotImplementedError

    def write_file(self, filename, doctree):
        raise NotImplementedError

    def finish(self):
        raise NotImplementedError


class StandaloneHTMLBuilder(Builder):
    """
    Builds standalone HTML docs.
    """
    name = 'html'
