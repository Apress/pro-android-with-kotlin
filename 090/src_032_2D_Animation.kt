if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
}else{
    startActivity(intent)
}
