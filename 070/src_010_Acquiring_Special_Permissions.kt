val backFromSettingPerm = 6183  // any suitable constant
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    val activity = this
    if (!Settings.System.canWrite(activity)) {
        // This is just a suggestion: present a special
        // dialog to the user telling about the special
        // permission. Important is the Activity start
        AlertDialog dialog = new AlertDialog.Builder(activity)
        .setTitle(...)
        .setMessage(...)
        .setPositiveButton("OK", { dialog, id ->
            val intent = Intent( Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:" + getPackageName())
            activity.startActivityForResult(intent, backFromSettingPerm)
        }).setNegativeButton("Cancel", { dialog, id ->
            // ...
        })
        .create();
        dialog.show();
        systemWillAsk = true;
    }
} else {
    // do as with any other permissions...
}
