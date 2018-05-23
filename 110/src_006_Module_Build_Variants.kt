buildTypes {
    release {
        ...}
    debug {
        ...}
    integration {
        initWith debug
        manifestPlaceholders = §$\neg$§
        [hostName:"internal.mycompany.com"]
        applicationIdSuffix ".integration"
    }
}
