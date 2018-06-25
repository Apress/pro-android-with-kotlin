import android.content.Context
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.provider.ContactsContract
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast

class ContactsWriter(val ctx:Context, val contentResolver: ContentResolver) {
    val opList = mutableListOf<ContentProviderOperation>()
    
    fun addContact(accountType:String, accountName:String, firstName:String, lastName:String, emailAddr:String, phone:String) {
        val firstOperationIndex = opList.size
        
        // Creates a new raw contact. The "contacts"
        // table will be filled automatically (access
        // is not possible anyways)
        // The device's user account and account type
        // is needed, otherwise the operations silently
        // will fail!
        var op = ContentProviderOperation.newInsert( ContactsContract.RawContacts.CONTENT_URI)
        .withValue( ContactsContract.RawContacts.ACCOUNT_TYPE, accountType)
        .withValue( ContactsContract.RawContacts.ACCOUNT_NAME, accountName)
        opList.add(op.build())
        
        // Creates the display name for the new raw
        // (a StructuredName data row)
        op = ContentProviderOperation.newInsert( ContactsContract.Data.CONTENT_URI)
        // withValueBackReference will make sure the
        // foreign key relations will be set
        // correctly
        .withValueBackReference( ContactsContract.Data.RAW_CONTACT_ID, firstOperationIndex)
        // The data row's MIME type is StructuredName
        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        // The row's display name is the name in the UI.
        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, firstName + " " + lastName)
        opList.add(op.build())
        
        // The specified phone number
        op = ContentProviderOperation.newInsert( ContactsContract.Data.CONTENT_URI)
        // Fix foreign key relation
        .withValueBackReference( ContactsContract.Data.RAW_CONTACT_ID, firstOperationIndex)
        // Sets the data row's MIME type to Phone
        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        // Phone number and type
        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
        opList.add(op.build())
        
        // Inserts the email
        op = ContentProviderOperation.newInsert( ContactsContract.Data.CONTENT_URI)
        // Fix the foreign key relation
        .withValueBackReference( ContactsContract.Data.RAW_CONTACT_ID, firstOperationIndex)
        // Sets the data row's MIME type to Email
        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
        // Email address and type
        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, emailAddr)
        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, android.provider.ContactsContract.CommonDataKinds.Email.TYPE_HOME)
        
        // Add a yield point. Has no functional influence,
        // but introduces a break so the system can do
        // other work to improve usability
        op.withYieldAllowed(true)
        
        opList.add(op.build())
    }
    
    fun reset() {
        opList.clear()
    }
    
    fun doAll() {
        try {
            contentResolver.applyBatch( ContactsContract.AUTHORITY, opList as ArrayList)
        } catch (e: Exception) {
            // Display a warning
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(ctx, "Something went wrong", duration)
            toast.show()
            
            // Log exception
            Log.e("LOG", "Exception encountered "+ "while inserting contact: " + e, e)
        }
    }
}
