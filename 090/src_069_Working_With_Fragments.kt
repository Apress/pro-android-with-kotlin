import android.support.v4.app.Fragment
...class MyFragment : Fragment() {
    override
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate( my_fragment, container, false)
    }
}
