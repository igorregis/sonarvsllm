
        retval.append([outputCommand,params])
    return retval

def formatPath(a):
    """Format SVG path data from an array"""
    return "".join([cmd + " ".join([str(p) for p in params]) for cmd, params in a])

def translatePath(p, x, y):
    for cmd,params in p:
        defs = pathdefs[cmd]
        for i in range(defs[1]):
            if defs[3][i] == 'x':
                params[i] += x
            elif defs[3][i] == 'y':
                params[i] += y

def scalePath(p, x, y):
    for cmd,params in p:
        defs = pathdefs[cmd]
        for i in range(defs[1]):
            if defs[3][i] == 'x':
                params[i] *= x
            elif defs[3][i] == 'y':
                params[i] *= y
            elif defs[3][i] == 'r':         # radius parameter
                params[i] *= x
            elif defs[3][i] == 's':         # sweep-flag parameter
                if x*y < 0:
                    params[i] = 1 - params[i]
            elif defs[3][i] == 'a':         # x-axis-rotation angle
                if y < 0:
                    params[i] = - params[i]

def rotatePath(p, a, cx = 0, cy = 0):
    if a == 0:
        return p
    for cmd,params in p:
        defs = pathdefs[cmd]
        for i in range(defs[1]):
            if defs[3][i] == 'x':
                x = params[i] - cx
                y = params[i + 1] - cy
                r = math.sqrt((x**2) + (y**2))
                if r != 0:
                    theta = math.atan2(y, x) + a
                    params[i] = (r * math.cos(theta)) + cx
                    params[i + 1] = (r * math.sin(theta)) + cy

