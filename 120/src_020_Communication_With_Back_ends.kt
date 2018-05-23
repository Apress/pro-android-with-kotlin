val reqObj:JSONObject = JSONObject("""{"a":7, "b":"Hello"}""")
val json1 = JsonObjectRequest(Request.Method.POST, "https://${HOST_IP}:6699/test/json",
reqObj, Response.Listener<JSONObject> { response ->
    Log.e("LOG", "Response: ${response}")
}, Response.ErrorListener{ err ->
    Log.e("LOG", "Error: ${err}")
})
