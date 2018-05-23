Wearable.getCapabilityClient(this).addListener({
    it.nodes.find {
        it.isNearby
    }?.run {
        msgClient.sendMessage( this.id,"/msg/path","Hello".toByteArray())
    }
}, "my_capability1")
