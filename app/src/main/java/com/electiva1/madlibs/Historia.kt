package com.electiva1.madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_historia.*
import java.util.*
import kotlin.collections.ArrayList


class Historia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historia)
        val inputs = intent.getStringArrayListExtra("inputs")
        val storyID = intent.getIntExtra("storyID", 0)
        writer(inputs, storyID)
    }

    fun writer(inputs: ArrayList<String>, storyID: Int){
        val builder = StringBuilder()
        val reader = Scanner(resources.openRawResource(storyID))

        val first_line = reader.nextLine()
        builder.append(first_line)
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(" ")
            builder.append(line)
        }

        var story = builder.toString()

        val r = Regex("<.*?>")
        val blanks = r.findAll(story).map { it.value }
        var i: Int = 0
        for(blank in blanks){
            story = story.replaceFirst(blank, inputs.get(i))
            i++
        }
        storyText.text = "$story"
    }


    fun nuevaClick(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
