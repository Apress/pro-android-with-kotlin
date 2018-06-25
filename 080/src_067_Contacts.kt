fun delete(id:String) {
    var op = ContentProviderOperation.newDelete( ContactsContract.RawContacts.CONTENT_URI)
    .withSelection(ContactsContract.RawContacts.CONTACT_ID + " = ?", arrayOf(id))
    opList.add(op.build())
}
