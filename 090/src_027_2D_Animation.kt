val anim = ObjectAnimator.ofFloat(tv, "x", 0.0f, 500.0f)
.apply {
    duration = 1000 // default is 300ms
    interpolator = AccelerateInterpolator()
}
anim.start()
