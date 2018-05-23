with(supportFragmentManager.beginTransaction()) {
    val fragment = MyFragment()
    add(fragm_container.id, fragment, "fragmTag")
    val fragmentId = fragment.id // can use that later...
    commit()
}
