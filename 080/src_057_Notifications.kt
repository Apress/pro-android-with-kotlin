val groupId = "my_group"
// The user-visible name of the group.
val groupName = "Group Name"
val notificationMngr = getSystemService(Context.NOTIFICATION_SERVICE)
as NotificationManager
notificationMngr.createNotificationChannelGroup( NotificationChannelGroup(groupId, groupName))
