package utils


fun String.prependProxy() =
    "https://api.allorigins.win/raw?url=$this"