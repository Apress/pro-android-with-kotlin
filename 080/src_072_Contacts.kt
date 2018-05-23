val contactUri = ContactsContract.Contacts.getLookupUri( id?.toLong()?:0, lookup)
quickBadge.assignContactUri(contactUri)
val thumbnail = loadContactPhotoThumbnail(photo.toString())
quickBadge.setImageBitmap(thumbnail)
