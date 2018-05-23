override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        with(window) {
            requestFeature( Window.FEATURE_CONTENT_TRANSITIONS)
            exitTransition = Explode()
            // if inside the CALLED transition,
            // instead use:
            // enterTransition = Explode()
            
            // use this in the CALLED transition to
            // primordially start the enter transition:
            // allowEnterTransitionOverlap = true
        }
    } else {
        // Go without transition - this can be empty
    }
    
    ...}
