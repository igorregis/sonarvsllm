    return subject, content

charset_re = re.compile('charset="?([-0-9a-zA-Z]+)"?')

def getSubject(message):
    subject = message['subject']
    try:
        return u' '.join((part[0].decode(part[1]) if part[1] else part[0]) for part in email.header.decode_header(subject))
    except UnicodeDecodeError:
        return subject
