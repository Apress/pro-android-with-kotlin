variantFilter { variant ->
    def names = variant.flavors*.name  // this is an array
    // To filter out variants, make a check here and then
    // do a "setIgnore(true)" if you don't need a variant.
    // This is just an example:
    if (names.contains("sinceapi24") && names.contains("free")) {
        setIgnore(true)
    }
}
