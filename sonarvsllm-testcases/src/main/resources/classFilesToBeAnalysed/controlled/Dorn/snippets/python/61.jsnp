        result['dest'] = result['dest'].replace("-", "_")
    else:
        errorExit(1, ( "Internal error: Missing `dest' in documentation string:",
                       pod, ))
    return result

###############################################################################

def pod2Argument(pod):
    """
    Return a list of two strings for `OptionGroup.__init__' describing the
    argument found in POD snippet.

    @param pod: Snippet in POD format to be analyzed.
    @type pod: str

    @return: Name of the argument and its description.
    @rtype: [ argument, description, ]
    """
    argument = ""
    description = ""
    for line in pod.split("\n"):
        if line.startswith("=cut"):
            break
        found = re.search("^=item\s*I<([^>]+)>", line)
        if found:
            description = ""
            argument = found.group(1)
        else:
            description += line + "\n"
    description = description.strip()
    return [ argument, description, ]

###############################################################################

def parseOptions():
    """
    Sets options and returns arguments.

    @return: Name of input file and optionally of output file.
    @rtype: ( str, [str,] )
    """
    global options
    pod = """

=head1 OPTIONS

=cut
    """
    optionParser = OptionParser("usage: %prog [option]... <xml> [<rst>]")
