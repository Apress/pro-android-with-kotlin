android {
    ...defaultConfig {...}
    signingConfigs {
        release {
            storeFile file("myrelease.keystore")
            storePassword "passwd"
            keyAlias "MyReleaseKey"
            keyPassword "passwd"
        }
    }
    buildTypes {
        release {
            ...signingConfig signingConfigs.release
        }
    }
}
