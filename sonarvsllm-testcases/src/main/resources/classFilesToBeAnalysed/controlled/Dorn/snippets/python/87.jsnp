                if e.args[0] == 995:
                    return None
                raise
            if size == 0:
                return None
            else:
                return FILE_NOTIFY_INFORMATION(self.buffer, size)

    def stop(self):
        CloseHandle(self.dirHandle)


class FilesystemNotifier(base.NotifierBase):
    def __init__(self):
        super(FilesystemNotifier, self).__init__()

        self.watcher = None
        self.thread = None
        self.lock = threading.Lock()

    def setFilename(self, filename):
        self.lock.acquire()
        try:
            if self.watcher is not None:
                self.watcher.stop()
                self.thread.join()
                self.watcher = None
                self.thread = None
            super(FilesystemNotifier, self).setFilename(filename)
            if self._filename:
                self.watcher = DirectoryWatcher(self._path)
                self.thread = threading.Thread(target=self._run)
                self.thread.setDaemon(True)
                self.thread.start()
        finally:
            self.lock.release()

    def stop(self):
        self.lock.acquire()
        try:
            if self.watcher is not None:
                self.watcher.stop()
                self.thread.join()
                self.watcher = None
                self.thread = None
        finally:
            self.lock.release()

    def onFileChanged(self):
        raise NotImplementedError
