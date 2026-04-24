package dam_A15031.coolweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {

    var day = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY) in 6..18
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // ✅ TEMA ANTES DO SUPER
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                if (day) setTheme(R.style.Theme_Day)
                else setTheme(R.style.Theme_Night)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                if (day) setTheme(R.style.Theme_Day_Land)
                else setTheme(R.style.Theme_Night_Land)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI
        val txtTemp = findViewById<TextView>(R.id.temperatureValue)
        val txtWind = findViewById<TextView>(R.id.speedValue)
        val txtDirection = findViewById<TextView>(R.id.directionValue)
        val txtTime = findViewById<TextView>(R.id.timeValue)
        val txtPressure = findViewById<TextView>(R.id.pressureValue)

        val editLat = findViewById<EditText>(R.id.latitudeValue)
        val editLon = findViewById<EditText>(R.id.longitudeValue)

        val btnUpdate = findViewById<Button>(R.id.updateButton)
        val imgWeather = findViewById<ImageView>(R.id.weatherImageView)

        // tentar obter localização real e atualizar UI
        getDeviceLocation(editLat, editLon, txtTemp, txtWind, txtDirection, txtTime, imgWeather, txtPressure)

        // botão manual update
        btnUpdate.setOnClickListener {
            fetchWeather(
                editLat.text.toString(),
                editLon.text.toString(),
                txtTemp, txtWind, txtDirection,
                txtTime, imgWeather, txtPressure
            )
        }
    }

    private fun fetchWeather(
        latStr: String,
        lonStr: String,
        txtTemp: TextView,
        txtWind: TextView,
        txtDirection: TextView,
        txtTime: TextView,
        imgWeather: ImageView,
        txtPressure: TextView
    ) {
        Thread {
            try {
                val lat = latStr.toFloat()
                val lon = lonStr.toFloat()

                val data = WeatherAPI_Call(lat, lon)

                runOnUiThread {
                    updateUI(data, txtTemp, txtWind, txtDirection, txtTime, imgWeather, txtPressure)
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun updateUI(
        data: WeatherData,
        txtTemp: TextView,
        txtWind: TextView,
        txtDirection: TextView,
        txtTime: TextView,
        imgWeather: ImageView,
        txtPressure: TextView
    ) {

        txtTemp.text = "${data.current_weather.temperature} °C"
        txtWind.text = "${data.current_weather.windspeed} km/h"
        txtDirection.text = "${data.current_weather.winddirection}°"

        val rawTime = data.current_weather.time
        val parts = rawTime.split("T")
        txtTime.text = "${parts[0]} às ${parts[1]}"

        // ícone
        val map = getWeatherCodeMap()
        val code = data.current_weather.weathercode
        val baseName = map[code]?.image ?: "clear_"
        val suffix = if (day) "day" else "night"
        val iconName = baseName + suffix

        val currentHour = data.current_weather.time.substring(0, 13)
        val index = data.hourly.time.indexOfFirst { it.startsWith(currentHour) }

        if (index != -1) {
            val pressure = data.hourly.pressure_msl[index]
            txtPressure.text = "$pressure hPa"
        } else {
            txtPressure.text = "N/A"
        }

        val resId = resources.getIdentifier(iconName, "drawable", packageName)
        if (resId != 0) imgWeather.setImageResource(resId)
    }

    private fun WeatherAPI_Call(lat: Float, lon: Float): WeatherData {
        val urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=$lat&longitude=$lon&" +
                "current_weather=true&" +
                "hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m,winddirection_10m"

        val url = URL(urlString)
        url.openStream().use {
            val reader = InputStreamReader(it, "UTF-8")
            return Gson().fromJson(reader, WeatherData::class.java)
        }
    }

    private fun getDeviceLocation(
        editLat: EditText, editLon: EditText,
        txtTemp: TextView, txtWind: TextView, txtDirection: TextView,
        txtTime: TextView, imgWeather: ImageView, txtPressure: TextView
    ) {
        // Verificar permissão
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Pedir permissão
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1000
            )
            return
        }

        // Obter localização real
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                editLat.setText(location.latitude.toString())
                editLon.setText(location.longitude.toString())

                // ✅ Atualiza automaticamente o tempo
                fetchWeather(
                    location.latitude.toString(),
                    location.longitude.toString(),
                    txtTemp, txtWind, txtDirection,
                    txtTime, imgWeather, txtPressure
                )
            } else {
                Toast.makeText(this, "Não foi possível obter a localização.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
