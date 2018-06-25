buildscript {
    ext.kotlin_version = '1.2.31'
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:" + "kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'kotlin2js'

sourceSets {
    main.kotlin.srcDirs += 'src/main/kotlinjs'
}

task prepareForExport(type: Jar) {
    baseName = project.name + '-all'
    from {
        configurations.compile.collect {
            it.isDirectory() ? it : zipTree(it) } + 'src/main/web'
    }
    with jar
}

repositories {
    mavenCentral()
}

dependencies {
    implementation "org.jetbrains.kotlin:" + "kotlin-stdlib-js:$kotlin_version"
}
