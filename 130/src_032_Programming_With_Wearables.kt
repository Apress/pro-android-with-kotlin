val capabilityInfo = Tasks.await( Wearable.getCapabilityClient(this).getCapability( "my_capability1", CapabilityClient.FILTER_REACHABLE))
capabilityInfo.nodes.find {
    it.isNearby
}?.run {
    msgClient.sendMessage( this.id,"/msg/path","Hello".toByteArray())
}
