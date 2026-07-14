package dev.shreyash.wonderouscompose.utils


fun String.prependProxy() =
    "https://api.allorigins.win/raw?url=$this"