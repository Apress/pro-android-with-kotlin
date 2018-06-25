fun updateContact(id:String, firstName:String?, lastName:String?, emailAddr:String?, phone:String?) {
    var op : ContentProviderOperation.Builder? = null
    if(firstName != null && lastName != null) {
        op = ContentProviderOperation.newUpdate( ContactsContract.Data.CONTENT_URI)
        .withSelection(ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?", arrayOf(id, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE))
        .withValue(ContactsContract.Contacts.DISPLAY_NAME, firstName + " " + lastName)
        opList.add(op.build())
    }
    if(emailAddr != null) {
        op = ContentProviderOperation.newUpdate( ContactsContract.Data.CONTENT_URI)
        .withSelection(ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?", arrayOf(id, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE))
        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, emailAddr)
        opList.add(op.build())
    }
    if(phone != null) {
        op = ContentProviderOperation.newUpdate( ContactsContract.Data.CONTENT_URI)
        .withSelection(ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?", arrayOf(id, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE))
        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        opList.add(op.build())
    }
}
