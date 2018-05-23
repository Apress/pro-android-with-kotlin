flavorDimensions "monetary", "apilevel"
productFlavors {
    free {
        dimension "monetary" ... }
    paid {
        dimension "monetary" ... }
    sinceapi21 {
        dimension "apilevel"
        versionNameSuffix "-api21" ... }
    sinceapi24 {
        dimension "apilevel"
        versionNameSuffix "-api24" ... }
}
