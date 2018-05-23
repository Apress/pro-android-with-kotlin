buildTypes {...}
flavorDimensions "monetary"
productFlavors {
    free {
        dimension "monetary"
        applicationIdSuffix ".free"
        versionNameSuffix "-free"
    }
    paid {
        dimension "monetary"
        applicationIdSuffix ".paid"
        versionNameSuffix "-paid"
    }
}
