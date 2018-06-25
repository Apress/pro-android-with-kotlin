task syncKotlinJs(type: Copy) {
    from zipTree('../kotlinjsSample/build/libs/' + 'kotlinjsSample-all.jar')
    into 'src/main/assets/kotlinjs'
}
preBuild.dependsOn(syncKotlinJs)
