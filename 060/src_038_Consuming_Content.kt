val oper:ContentProviderOperation = ContentProviderOperation.newInsert(uri)
.withValue("key1", "val1")
.withValue("key2", 42)
.build()
