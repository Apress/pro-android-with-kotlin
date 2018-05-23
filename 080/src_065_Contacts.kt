val rawId = ...val cwr = ContactsWriter(this, contentResolver)
cwr.updateContact(rawId, null, null, "postXXX@kappa.com", null)
cwr.doAll()
