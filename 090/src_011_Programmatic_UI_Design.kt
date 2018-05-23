val WRAP = ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
fl.addView( Button(this).apply {
    text = "Go"
    setOnClickListener { v ->
        v?.run {
            x += 30.0f *
            (-0.5f + Math.random().toFloat())
            y += 30.0f *
            (-0.5f + Math.random().toFloat())
        }
    }
}, WRAP
)
