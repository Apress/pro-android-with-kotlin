val rawId = ...val cwr = ContactsWriter(this, contentResolver)
cwr.delete(rawId)
cwr.doAll()
