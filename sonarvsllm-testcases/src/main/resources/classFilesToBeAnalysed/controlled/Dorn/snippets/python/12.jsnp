            event.result = event.func(*event.args, **event.kwargs)
        except Exception, e:
            event.exception = e
        event.event.set()

    def Invoke(self, sync, func, *args, **kwargs):
        """When called from any thread other than the main GUI thread,
        Invoke(function, *args, **kwargs) will call 'function' from
        the GUI thread, block until it returns, and return its return
        value, or returns immediately if 'sync' is False."""

        event = InvokeEvent(sync, func, args, kwargs)
        wx.PostEvent(self, event)

        if sync:
            event.event.wait()

        if event.exception:
            raise event.exception
        return event.result


def synchronized(func):
    """Use this decorator on a class using te DeferredCallMixin to
    make a method automatically called through Invoke."""

    def inner(self, *args, **kwargs):
        return self.Invoke(True, func, self, *args, **kwargs)

    return inner
