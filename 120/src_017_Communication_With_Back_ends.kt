// This is a convention for emulated devices
// addressing the host (development PC)
val HOST_IP = "10.0.2.2"

val stringRequest = StringRequest(Request.Method.GET, "https://${HOST_IP}:6699/test/person",
Response.Listener<String> { response ->
    val shortened = response.substring(0, Math.min(response.length, 500))
    tv.text = "Response is: ${shortened}"
}, Response.ErrorListener { err ->
    Log.e("LOG", err.toString())
    tv.text = "That didn't work!"
})
queue.add(stringRequest)
