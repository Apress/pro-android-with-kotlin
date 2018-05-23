class MyAdapter(val myDataset:Array<String>) : RecyclerView.Adapter
<MyAdapter.Companion.ViewHolder>() {
    companion object {
        class ViewHolder(val v:RelativeLayout) : RecyclerView.ViewHolder(v)
    }
    
    override
    fun onCreateViewHolder(parent:ViewGroup, viewType:Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context)
        .inflate(R.layout.item, parent, false)
        as RelativeLayout
        return ViewHolder(v)
    }
    
    override
    fun onBindViewHolder(holder:ViewHolder, position:Int) {
        // replace the contents of the view with
        // the element at this position
        holder.v.findViewById<TextView>( R.id.firstLine).text = myDataset[position]
    }
    
    override
    fun getItemCount() : Int = myDataset.size
}
