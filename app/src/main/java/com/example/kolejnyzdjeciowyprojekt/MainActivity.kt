package com.example.kolejnyzdjeciowyprojekt

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.INotificationSideChannel
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var CameraStart = findViewById<Button>(R.id.CameraStart)
        var Rotation = findViewById<SeekBar>(R.id.rotation)
        var Zdjecie = findViewById<ImageView>(R.id.Zdjecie)
        var Transparency = findViewById<SeekBar>(R.id.transparency)
        var red = findViewById<Button>(R.id.red)
        var green = findViewById<Button>(R.id.green)
        var blue = findViewById<Button>(R.id.blue)
        var cleario = findViewById<Button>(R.id.cleario)

        Rotation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(SeekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Zdjecie.rotation = progress.toFloat()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        Transparency.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                Zdjecie.setAlpha((progress.toFloat())/100)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        red.setOnClickListener{
        Zdjecie.setColorFilter(resources.getColor(R.color.redColor));
        }
        green.setOnClickListener{
            Zdjecie.setColorFilter(resources.getColor(R.color.greenColor));
        }
        blue.setOnClickListener{
            Zdjecie.setColorFilter(resources.getColor(R.color.blueColor));
        }
        cleario.setOnClickListener{
            Zdjecie.clearColorFilter();
        }






        CameraStart.setOnClickListener{
            if(ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){
                val thumbNail: Bitmap = data!!.extras!!.get("data") as Bitmap

                var Zdjecie = findViewById<ImageView>(R.id.Zdjecie)

                Zdjecie.setImageBitmap(thumbNail)


            }
        }

    }

}