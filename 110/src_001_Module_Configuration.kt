apply plugin: "com.android.application"
apply plugin: "kotlin-android"
apply plugin: "kotlin-android-extensions"

android {
    compileSdkVersion 26
    defaultConfig {
        applicationId "de.pspaeth.xyz"
        minSdkVersion 16
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner §$\neg$§
        "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles §$\neg$§
            getDefaultProguardFile( §$\neg$§ "proguard-android.txt"), §$\neg$§
            "proguard-rules.pro"
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', §$\neg$§ include: ['*.jar'])
    implementation §$\neg$§
    "org.jetbrains.kotlin:kotlin-stdlib-jre7: §$\neg$§
    $kotlin_version"
    implementation §$\neg$§
    "com.android.support:appcompat-v7:26.1.0"
    implementation §$\neg$§
    "com.android.support.constraint: §$\neg$§
    constraint-layout:1.0.2"
    testImplementation "junit:junit:4.12"
    androidTestImplementation §$\neg$§
    "com.android.support.test:runner:1.0.1"
    androidTestImplementation §$\neg$§
    "com.android.support.test.espresso: §$\neg$§
    espresso-core:3.0.1"
}
