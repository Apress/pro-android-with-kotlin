import static spark.Spark.*

def keystoreFilePath = "keystore.jks"
def keystorePassword = "passw7%d"
def truststoreFilePath = null
def truststorePassword = null

secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword)
port(6699)

get("/test/person", { req, res -> "Hello World" })

post("/test/json", { req, res ->
    println(req.body())
    '{ "msg":"Hello World", "val":7 }'
})
