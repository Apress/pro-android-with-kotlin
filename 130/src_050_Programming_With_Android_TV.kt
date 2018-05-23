class UpdateRecommendationsService : IntentService("RecommendationService") {
    companion object {
        private val TAG = "UpdateRecommendationsService"
        private val MAX_RECOMMENDATIONS = 3
    }
    override fun onHandleIntent(intent: Intent?) {
        Log.d("LOG", "Updating recommendation cards")
        
        val recommendations:List<Movie> = ArrayList<Movie>()
        // TODO: fill recommendation movie list...
        
        var count = 0
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
        as NotificationManager
        val notificationId = 42
        for (movie in recommendations) {
            Log.d("LOG", "Recommendation - " + movie.title!!)
            val builder = RecommendationBuilder( context = applicationContext, smallIcon = R.drawable.video_by_icon, id = count+1, priority = MAX_RECOMMENDATIONS - count, title = movie.title ?: "", description = "Description", image = getBitmapFromURL( movie.cardImageUrl ?:""), intent = buildPendingIntent(movie))
            val notification = builder.build()
            notificationManager.notify( notificationId, notification)
            if (++count >= MAX_RECOMMENDATIONS) {
                break
            }
        }
    }
    
    private fun getBitmapFromURL(src: String): Bitmap {
        val url = URL(src)
        return (url.openConnection() as HttpURLConnection).apply {
            doInput = true
        }.let {
            it.connect()
            BitmapFactory.decodeStream(it.inputStream)
        }
    }
    
    private fun buildPendingIntent(movie: Movie): PendingIntent {
        val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra("Movie", movie)
        
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack( DetailsActivity::class.java)
        stackBuilder.addNextIntent(detailsIntent)
        // Ensure a unique PendingIntents, otherwise all
        // recommendations end up with the same
        // PendingIntent
        detailsIntent.action = movie.id.toString()
        
        return stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
