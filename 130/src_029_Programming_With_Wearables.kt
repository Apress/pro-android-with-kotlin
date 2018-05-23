var wearableConnected = false
fun onCreate(savedInstanceState: Bundle?) {
    ...Wearable.getNodeClient(this@MainActivity).connectedNodes.addOnSuccessListener {
        wearableConnected = it.any {
            it.isNearby
        }
    }.addOnCompleteListener {
    }.addOnFailureListener {
        ...}
}
