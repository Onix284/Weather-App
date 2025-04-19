import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.room_contactpp.Models.WeatherApiResponse


object KtorClient {

    private val okHttpClient = HttpClient{
        install(ContentNegotiation){
            json(json = Json{
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout){
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        install(Logging){
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(DefaultRequest){
            url{
                host = "api.openweathermap.org/data"
                protocol = URLProtocol.HTTPS
                parameters.append("appid", "5f68a3941158ef2c7f509997707af7b7")
            }
        }
    }

    suspend fun GetWeatherByCity(city : String) : WeatherApiResponse{
        return okHttpClient.get("/2.5/weather?q=$city")
            .body<WeatherApiResponse>()
    }
}