import codecs
import shutil
import cPickle as pickle
import cStringIO as StringIO
from os import path

from docutils.io import StringOutput, DocTreeInput
from docutils.core import publish_parts
from docutils.utils import new_document
from docutils.readers import doctree
from docutils.frontend import OptionParser

from .util import (get_matching_files, attrdict, status_iterator,
                   ensuredir, get_category, relative_uri)
from .writer import HTMLWriter
from .console import bold, purple, green
from .htmlhelp import build_hhx
from .environment import BuildEnvironment
from .highlighting import pygments, get_stylesheet

# side effect: registers roles and directives
from . import roles
from . import directives

ENV_PICKLE_FILENAME = 'environment.pickle'
LAST_BUILD_FILENAME = 'last_build'

# Helper objects

class relpath_to(object):
    def __init__(self, builder, filename):
        self.baseuri = builder.get_target_uri(filename)
        self.builder = builder
    def __call__(self, otheruri, resource=False):
        if not resource:
            otheruri = self.builder.get_target_uri(otheruri)
        return relative_uri(self.baseuri, otheruri)


class collect_env_warnings(object):
    def __init__(self, builder):
        self.builder = builder
    def __enter__(self):
        self.stream = StringIO.StringIO()
        self.builder.env.set_warning_stream(self.stream)
    def __exit__(self, *args):
        self.builder.env.set_warning_stream(self.builder.warning_stream)
        warnings = self.stream.getvalue()
        if warnings:
            print >>self.builder.warning_stream, warnings
