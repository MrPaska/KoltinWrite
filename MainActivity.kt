package com.example.writedata

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button1)
        val read_btn = findViewById<Button>(R.id.button2)
        val name_ = findViewById<TextInputEditText>(R.id.name)
        val surname_ = findViewById<TextInputEditText>(R.id.surname)
        val city_ = findViewById<TextInputEditText>(R.id.city)
        val pom_ = findViewById<TextInputEditText>(R.id.pomegiai)
        val pom1_ = findViewById<TextInputEditText>(R.id.pomegiai1)
        val pom2_ = findViewById<TextInputEditText>(R.id.pomegiai2)


        /*val path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOCUMENTS
        )*/
        //println("Hey!! $path")

        btn.setOnClickListener {
            createJsonData(name_, surname_, city_, pom_, pom1_, pom2_)
        }
        read_btn.setOnClickListener{
            val i = Intent(this, ReadData::class.java)
                startActivity(i)
        }

    }
        private fun createJsonData(name_: TextInputEditText, surname_: TextInputEditText, city_: TextInputEditText, pom_: TextInputEditText, pom1_: TextInputEditText, pom2_: TextInputEditText) {
            val REQUEST_WRITE_EXTERNAL = 1

            val vardas = name_.text.toString()
            val amzius = surname_.text.toString().toInt()
            val lytis = city_.text.toString()
            val pom1 = pom_.text.toString()
            val pom2 = pom1_.text.toString()
            val pom3 = pom2_.text.toString()

            if (TextUtils.isEmpty(vardas)) { // to do operations on String objects
                name_.error = "Įveskite vardą"
                return
            }
            if (TextUtils.isEmpty(amzius.toString())) {
                surname_.error = "Įveskite pavardę"
                return
            }
            if (TextUtils.isEmpty(lytis)) {
                city_.error = "Įveskite miestą"
                return
            }
            /*if (TextUtils.isEmpty(pomegiai)) {
                pomegiai_.error = "Įveskite miestą"
                return
            }*/

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Reikia leidimų prie vidinės atminties!", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL)
                return

            }else {
                val json = JSONObject() // json_object converts text to json format
                val user = User(vardas, amzius, lytis, pom1, pom2, pom3) // adding variables to User.kt

                json.put("asmenys", JSONArray().put(addUser(user)))

                saveJson(json.toString())
                clearText(name_, surname_, city_)
            }
        }
        private fun saveJson(s: String) {
            val output: Writer
            val context = applicationContext
            val path = context.filesDir.absolutePath
            val file = File(path, "newData.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            try {
                output = FileWriter(file, true)
                output.write(s) // needs string to write
                output.close()

                Toast.makeText(this, "ATLIKTA", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "NEPAVYKO", Toast.LENGTH_SHORT).show()
            }
        }

        private fun addUser(user: User): JSONObject {
            /*for (i in 0 until pomegiai.size){
                JSONObject().put("pomegiai", JSONArray().put(user.pomegiai))
            }*/
            return JSONObject()
                .put("vardas", user.vardas)
                .put("amzius", user.amzius)
                .put("lytis", user.lytis)
                .put("pomegiai",
                    JSONArray()
                        .put(user.pom1)
                        .put(user.pom2)
                        .put(user.pom3))
        }

        private fun clearText(name_: TextInputEditText, surname_: TextInputEditText, city_: TextInputEditText) {
            name_.setText("")
            surname_.setText("")
            city_.setText("")
        }

    }
