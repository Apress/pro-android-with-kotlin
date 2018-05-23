val intent = Intent("<PCKG_NAME>.START_SERVICE")
intent.setPackage("<PCKG_NAME>")
startService(intent)

// ... do something ...

stopService(intent)
