when(uriMatcher.match(url)) {
    PEOPLE ->
    // incoming path = people, do s.th. with that...
    PEOPLE_ID ->
    // incoming path = people/#, do s.th. with that...
    PEOPLE_PHONES ->
    // incoming path = people/#/phone, ...
    else ->
    // do something else
}
