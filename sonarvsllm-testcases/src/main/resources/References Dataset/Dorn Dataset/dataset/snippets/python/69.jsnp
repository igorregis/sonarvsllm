codes["darkred"]   = codes["0xAA0000"]

codes["green"]     = codes["0x55FF55"]
codes["darkgreen"] = codes["0x00AA00"]

codes["yellow"]    = codes["0xFFFF55"]
codes["brown"]     = codes["0xAA5500"]

codes["blue"]      = codes["0x5555FF"]
codes["darkblue"]  = codes["0x0000AA"]

codes["fuchsia"]   = codes["0xFF55FF"]
codes["purple"]    = codes["0xAA00AA"]

codes["teal"]      = codes["0x00AAAA"]
codes["turquoise"] = codes["0x55FFFF"]

codes["white"]     = codes["0xFFFFFF"]
codes["lightgray"] = codes["0xAAAAAA"]

codes["darkteal"]   = codes["turquoise"]
codes["darkyellow"] = codes["brown"]
codes["fuscia"]     = codes["fuchsia"]
codes["white"]      = codes["bold"]

def nocolor():
    "turn off colorization"
    for code in codes:
        codes[code] = ""

def reset_color():
    return codes["reset"]

def colorize(color_key, text):
    return codes[color_key] + text + codes["reset"]

functions_colors = [
    "bold", "white", "teal", "turquoise", "darkteal",
    "fuscia", "fuchsia", "purple", "blue", "darkblue",
    "green", "darkgreen", "yellow", "brown",
    "darkyellow", "red", "darkred"
]

def create_color_func(color_key):
    """
    Return a function that formats its argument in the given color.
    """
    def derived_func(text):
        return colorize(color_key, text)
    return derived_func
