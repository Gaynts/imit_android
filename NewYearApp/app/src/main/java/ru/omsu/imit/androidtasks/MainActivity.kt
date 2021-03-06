package ru.omsu.imit.androidtasks

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

const val SECRET_kEY_FOR_NAME = "ru.omsu.imit.androidtasks.SECRET_kEY"

private const val KEY_CROW_COUNT = "CROW_COUNT"
private const val PASSENGER_NOTIFY_ID = 1488
private const val CHANNEL_ID = "My app ID"

class MainActivity : AppCompatActivity() {
    private var ballsCount = 0
    private var name = ""

    private fun onExitNotification() {
        val nIntent = Intent(this, SurpriseActivity::class.java)
        val contentIntent =
            PendingIntent.getActivity(this, 0, nIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_passeger))
            .setContentTitle(getString(R.string.the_surprise))
            .setContentText(getString(R.string.surprise_notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setVibrate(longArrayOf(500, 500, 500))
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(this).notify(PASSENGER_NOTIFY_ID, notification)
    }

    private fun onCreateToastNotification() {
        //  Create toast in program (Use custom toasts considered bad practice)
        val toastImg = ImageView(this).apply {
            setImageResource(R.drawable.christmascat)
        }
        Toast.makeText(applicationContext, R.string.app_greeting, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            (view as LinearLayout).addView(toastImg, 0)
        }.show()
    }

    private fun openQuitDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.quit_confirm))
            setPositiveButton(getString(R.string.yes)) { _, _ -> finish() }
            setNegativeButton(getString(R.string.no)) { _, _ -> }
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mysteryCatImageButton.setOnClickListener {
            name = with(enterNameEditText.text) { if (isEmpty()) getString(R.string.friend) else toString() }
            greetingTextView.text = getString(R.string.cat_greeting).format(name)
        }
        crowCountButton.setOnClickListener {
            crowCountTextView.text = getString(R.string.ball_count_format_str).format(++ballsCount)
        }

        NotificationManagerCompat.from(this).cancel(PASSENGER_NOTIFY_ID)
        if (savedInstanceState != null) {
            ballsCount = savedInstanceState.getInt(KEY_CROW_COUNT)
            crowCountTextView.text = getString(R.string.ball_count_format_str).format(ballsCount)
        } else {
            onCreateToastNotification()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CROW_COUNT, ballsCount)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_the_passenger -> {
                val intent = Intent(this, SurpriseActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_to_traffic_lights -> {
                val intent = Intent(this, TrafficLightsActivity::class.java).apply {
                    if (name.isNotEmpty()) {
                        putExtra(SECRET_kEY_FOR_NAME, name)
                    }
                }
                startActivity(intent)
                return true
            }
            R.id.action_to_converter -> {
                val intent = Intent(this, ConverterActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        openQuitDialog()
    }

    override fun onStop() {
        super.onStop()
        onExitNotification()
    }
}