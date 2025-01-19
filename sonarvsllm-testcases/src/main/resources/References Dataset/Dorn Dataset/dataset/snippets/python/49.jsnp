                copydir_run_2to3(source, dest, template=self.manifest_in)
            log.set_threshold(loglevel)


class smart_install_data(install_data):
    # From <http://wiki.python.org/moin/DistutilsInstallDataScattered>,
    # by Pete Shinners.

    def run(self):
        #need to change self.install_dir to the library dir
        install_cmd = self.get_finalized_command('install')
        self.install_dir = getattr(install_cmd, 'install_lib')
        return install_data.run(self)

class build_data(Command):

    def initialize_options(self):
        pass

    def finalize_options(self):
        pass

    def run(self):
        build_py = self.get_finalized_command('build_py')
        data_files = self.distribution.data_files
        for f in data_files:
            dir = convert_path(f[0])
            dir = os.path.join(build_py.build_lib, dir)
            self.mkpath(dir)
            for data in f[1]:
                data = convert_path(data)
                self.copy_file(data, dir)

# let our build_data run
build.sub_commands.append(('build_data', lambda *a: True))


def do_setup():
    kwargs = package_data.copy()
    kwargs['classifiers'] = classifiers
    # Install data files properly.
    kwargs['cmdclass'] = {'build_data': build_data,
                          'install_data': smart_install_data}
    # Auto-convert source code for Python 3
    if sys.version_info >= (3,):
        kwargs['cmdclass']['build_py'] = copy_build_py_2to3
    else:
        kwargs['cmdclass']['build_py'] = build_py
    dist = setup(**kwargs)
    return dist
