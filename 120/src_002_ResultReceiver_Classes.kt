Intent(this, CalledActivity::class.java).apply {
    putExtra(MyResultReceiver.INTENT_KEY, MyResultReceiver())
}.run{ startActivity(this) }
