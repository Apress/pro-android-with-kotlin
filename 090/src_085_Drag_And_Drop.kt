override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    theView.setOnTouchListener ...
    val dragListener = MyDragListener()
    theView.setOnDragListener(dragListener)
    otherView.setOnDragListener(dragListener)
    ...}
