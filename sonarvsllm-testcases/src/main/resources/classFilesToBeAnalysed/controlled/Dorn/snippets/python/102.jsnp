    return result

def handleBlockQuote(node):
    result = BlockQuoteDitem(node.nodeName)
    result.children = processChildren(node)
    return result

def handleList(node):
    result = ListDitem(node.nodeName)
    result.children = processChildren(node)
    return result

def handleListItem(node):
    result = ListItemDitem(node.nodeName)
    result.children = processChildren(node)
    return result

def handleTable(node):
    result = TableDitem(node.nodeName)
    # Ignore table contents that are not tr
    result.children = [x
        for x in processChildren(node) if x.type=='tr']
    return result

def handleTr(node):
    result = TrDitem(node.nodeName)
    # Ignore tr contents that are not th or td
    result.children = [x
        for x in processChildren(node) if x.type in ('th', 'td')]
    return result
