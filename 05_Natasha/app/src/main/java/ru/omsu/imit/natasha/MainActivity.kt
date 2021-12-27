package ru.omsu.imit.natasha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        right_bottom_image.setOnClickListener {
            val phrases = listOf(
                "Уже все мандарины раскупили, Наташ",
                "Вставай, мы там ёлку уронили",
                "Мы уронили вообще всё, Наташ, честно",
                "Наташ, ты чё опять лежишь?",
                "Салатики-то сами себя не приготовят, Наташ!")

            val shuffledList = phrases.shuffled()

            second_text.text = shuffledList[0]
            third_text.text = shuffledList[1]
            forth_text.text = shuffledList[2]

        }


    }
}