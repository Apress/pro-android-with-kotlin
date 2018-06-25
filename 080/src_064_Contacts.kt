val cwr = ContactsWriter(this, contentResolver)
cwr.addContact("com.google","user@gmail.com", "Peter","Kappa", "post@kappa.com","0123456789")
cwr.addContact("com.google","user@gmail.com", "Hilda","Kappa", "post2@kappa.com","0123456789")
cwr.doAll()
