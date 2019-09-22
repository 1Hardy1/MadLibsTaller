package com.electiva1.madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ingresarpalabras.*
import java.util.*
import kotlin.collections.ArrayList


class Ingreso : AppCompatActivity() {

    private val REQ_CODE = 123
    private lateinit var myAdapter: ArrayAdapter<String>
    private val palabras = ArrayList<String>()
    private val palabrasType = ArrayList<String>()
    private var aux = 0
    private var storyID = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresarpalabras)
        val story = intent.getStringExtra("historia")
        extract(story)
    }


    fun extract(story: String){
        val builder = StringBuilder()
        this.storyID = resources.getIdentifier(story, "raw", packageName)
        val reader = Scanner(resources.openRawResource(storyID))
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(line)
        }
        var story = builder.toString()
        val r = Regex("<.*?>")
        val found = r.findAll(story)
        found.forEach{ f ->
            val m = f.value
            palabrasType.add(m)
            aux++
        }
        val first_type = palabrasType.get(0)
        palabras_ingresadas.hint = "por favor ingrese $first_type"
        hints.text = "tan solo te faltan: $aux"
    }

    fun agregarClick(view: View){


        if(palabras_ingresadas.text.toString().isEmpty() || palabras_ingresadas.text.toString().trim().isEmpty()){
            val Toast = Toast.makeText(this, "Ingrese la palabra!", Toast.LENGTH_SHORT)
            Toast.show()
        }
        else{
            val word = palabras_ingresadas.text.toString().trim()
            palabras.add(word)
            aux--
            palabras_ingresadas.setText("")
            if(aux >= 1){
                val next_type = palabrasType.get(palabrasType.size - aux)
                palabras_ingresadas.hint = "Ungrese $next_type"
                hints.text = "tan solo faltan: $aux"
            }

            if(aux == 1)
                btnAgregar.text = "LISTO!"


            if(aux == 0){
                val intent = Intent(this, Historia::class.java)
                intent.putExtra("inputs", palabras)
                intent.putExtra("storyID", storyID)
                startActivityForResult(intent, REQ_CODE)
            }
        }
    }
}
