savedInstanceState?.run {
    photoFile = getString("imgFile")?.let {File(it)}
}
