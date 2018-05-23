val intent:Intent = Intent("<PCKG_NAME>.START_SERVICE")
intent.setPackage("<PCKG_NAME>")
bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
