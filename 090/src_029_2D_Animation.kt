val sceneRoot:ViewGroup = ...
// Obtain the view hierarchy to add as a child of
// the scene root when this scene is entered
val startViewHierarchy:ViewGroup = ...
// Same for the end scene
val endViewHierarchy:ViewGroup = ...
// Create the scenes
val startScene = Scene(sceneRoot, startViewHierarchy)
val endScene = Scene(sceneRoot, endViewHierarchy)

val fadeTransition = Fade()
TransitionManager.go(endScene, fadeTransition)
