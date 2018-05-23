with(recycler_view) {
    // use this setting to improve performance if you know
    // that changes in content do not change the layout
    // size of the RecyclerView
    setHasFixedSize(true)
    // use for example a linear layout manager
    layoutManager = LinearLayoutManager(this@MainActivity)
    // specify the adapter, use some sample data
    val dataset = (1..21).map { "Itm" + it }.toTypedArray()
    adapter = MyAdapter(dataset)
}
