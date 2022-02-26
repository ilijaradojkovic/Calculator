package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var textview: EditText

    private lateinit var delete_OperationTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview = findViewById<EditText>(R.id.Result)
        delete_OperationTextView = findViewById(R.id.Delete_Operation)
        delete_OperationTextView.setOnLongClickListener {
            textview.setText("")
            true
        }
    }

    fun izracunajIzraz(v: View) {

        val s = textview.text.toString()


        val rez=izracunaj(s,1)
        textview.setText(rez.toString())
    }


    private fun izracunaj(niz: String, flag: Int): Double {
        var i = 0
        var rezultat: Double = 0.0
        val lista=LinkedList<String>()
        lista.add(niz)


        rezultat += saberiDvaBroja(lista)
        return rezultat
    }


    private fun saberiDvaBroja(a: List<String>): Double {


        var rezultat = 0.0
        val n = a.size
        var broj = 0.0
        for (i in 0 until n) {
            Log.i("cao", "testiram broj " + a[i].toString())
            broj = when {



                a[i].contains("(") -> {
                    var rez1=0.0
                    Log.i("cao","Sadrzim")
                    val lista=LinkedList<String>()

                   var index=a[i].indexOf("(")
                    Log.i("cao","Index ( $index")
                    if(index==0){
                        index=a[i].indexOf(")")
                        Log.i("cao","Index ) $index")
                        if(index==a[i].length-1) {

                            val str=a[i].substring(a[i].indexOf('(')+1,a[i].indexOf(')'))
                            rez1=saberiDvaBroja(str.split("+"))
                        }
                            else {

                            while (index > 0 && index<a[i].length) {
                                if (a[i][index] == '+') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", a[i][index].toString())
                                    rez1=  saberiDvaBroja(lista)
                                    break
                                } else if (a[i][index] == '-') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= oduzmiDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '*') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= pomnoziDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '/') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= podeliDvaBroja(lista)
                                    break
                                }
                                index++
                            }
                        }
                    }else{
                           index-=2
                        while(index>0){

                                if(a[i][index]=='+') {
                                    lista.add(a[i].substring(0,index))
                                    lista.add(a[i].substring(index+1,a[i].length))
                                    Log.i("cao",lista.toString())
                                    rez1=  saberiDvaBroja(lista)
                                    break
                                }
                            else if(a[i][index]=='-') {
                                    lista.add(a[i].substring(0,index))
                                    lista.add(a[i].substring(index+1,a[i].length))
                                    Log.i("cao",lista.toString())
                                    rez1= oduzmiDvaBroja(lista)
                                 break
                                }
                                else if (a[i][index] == '*') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= pomnoziDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '/') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= podeliDvaBroja(lista)
                                    break
                                }
                            index--
                        }
                    }
                    rez1
                }a[i].contains("+") -> {

                    saberiDvaBroja(a[i].split("+"))
                }

                a[i].contains("-") -> {

                    oduzmiDvaBroja(a[i].split("-"))
                }


                a[i].contains("*") -> {

                    pomnoziDvaBroja(a[i].split("*"))

                }
                a[i].contains("/") -> {

                    podeliDvaBroja(a[i].split("/"))
                }
                a[i].contains("%") -> {

                    procenatBroja(a[i].split("%"))
                }


                else -> a[i].toDouble()
            }
            rezultat += broj
        }

        return rezultat
    }

    private fun procenatBroja(a: List<String>): Double {
        var rez = 0.0

        val n = a.size
        var broj = 0.0
        for (i in 0 until n) {
            Log.i("cao", "testiram broj" + a[i].toString())
            broj = when {


                a[i].contains("-") -> {
                    oduzmiDvaBroja(a[i].split("-"))

                }
                a[i].contains("*") -> {
                    saberiDvaBroja(a[i].split("*"))
                }

                a[i].contains("/") -> {
                    podeliDvaBroja(a[i].split("/"))
                }

                else -> a[i].toDouble()
            }
            if (rez == 0.0) rez = broj
            else rez *= broj / 100

        }

        return rez
    }

    private fun oduzmiDvaBroja(a: List<String>): Double {

        var rez = 0.0
        val n = a.size
        var broj = 0.0
        Log.i("cao", a.toString())

        for (i in 0 until n) {
            Log.i("cao", "testiram broj" + a[i].toString())
            broj = when {

                a[i].contains("(") -> {
                    var rez1=0.0
                    Log.i("cao","Sadrzim")
                    var lista=LinkedList<String>()

                    var index=a[i].indexOf("(")
                    if(index==0){
                        index=a[i].indexOf(")")

                        if(index==a[i].length-1) {
                            var str=a[i].substring(a[i].indexOf('(')+1,a[i].indexOf(')'))
                            rez1=saberiDvaBroja(str.split("+"))
                        }
                        else {
                            index++
                            // Log.i("cao",a[i].substring(0,index))

                            //Log.i("cao",lista.toString())
                            //  saberiDvaBroja(lista)
                            while (index > 0) {

                                if (a[i][index] == '+') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1=  saberiDvaBroja(lista)
                                    break
                                } else if (a[i][index] == '-') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= oduzmiDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '*') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= pomnoziDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '%') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= podeliDvaBroja(lista)
                                    break
                                }
                                index++
                            }
                        }
                        // Log.i("cao",a[i].substring(0,index))

                        //Log.i("cao",lista.toString())
                        //  saberiDvaBroja(lista)

                    }else{

                        while(index>0){

                            if(a[i][index]=='+') {
                                lista.add(a[i].substring(0,index))
                                lista.add(a[i].substring(index+1,a[i].length))
                                Log.i("cao",lista.toString())
                                rez1=  saberiDvaBroja(lista)
                                break
                            }
                            else if(a[i][index]=='-') {
                                lista.add(a[i].substring(0,index))
                                lista.add(a[i].substring(index+1,a[i].length))
                                Log.i("cao",lista.toString())
                                rez1=  oduzmiDvaBroja(lista)
                                break
                            }
                            else if (a[i][index] == '*') {
                                lista.add(a[i].substring(0, index))
                                lista.add(a[i].substring(index + 1, a[i].length))
                                Log.i("cao", lista.toString())
                                rez1= pomnoziDvaBroja(lista)
                                break
                            }
                            else if (a[i][index] == '%') {
                                lista.add(a[i].substring(0, index))
                                lista.add(a[i].substring(index + 1, a[i].length))
                                Log.i("cao", lista.toString())
                                rez1= podeliDvaBroja(lista)
                                break
                            }
                            index--
                        }
                    }
                    rez1
                }

                a[i].contains("*") -> {
                    pomnoziDvaBroja(a[i].split("*"))

                }
                a[i].contains("%") -> {
                    procenatBroja(a[i].split("%"))
                }

                a[i].contains("/") -> {
                    podeliDvaBroja(a[i].split("/"))
                }

                else -> a[i].toDouble()
            }
            if (rez == 0.0) rez = broj
            else rez -= broj
        }

        return rez
    }

    private fun pomnoziDvaBroja(a: List<String>): Double {
        var rez = 0.0

        val n = a.size
        var broj = 0.0
        for (i in 0 until n) {
            Log.i("cao", "testiram broj" + a[i].toString())
            broj = when {

                a[i].contains("(") -> {
                    var rez1=0.0
                    Log.i("cao","Sadrzim")
                    var lista=LinkedList<String>()

                    var index=a[i].indexOf("(")
                    if(index==0){
                        index=a[i].indexOf(")")

                        if(index==a[i].length-1) {
                            var str=a[i].substring(a[i].indexOf('(')+1,a[i].indexOf(')'))
                            rez1=saberiDvaBroja(str.split("+"))
                        }
                        else {
                            index++
                            // Log.i("cao",a[i].substring(0,index))

                            //Log.i("cao",lista.toString())
                            //  saberiDvaBroja(lista)
                            while (index > 0) {

                                if (a[i][index] == '+') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1=  saberiDvaBroja(lista)
                                    break
                                } else if (a[i][index] == '-') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= oduzmiDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '*') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= pomnoziDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '%') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= podeliDvaBroja(lista)
                                    break
                                }
                                index++
                            }
                        }
                        // Log.i("cao",a[i].substring(0,index))

                        //Log.i("cao",lista.toString())
                        //  saberiDvaBroja(lista)

                    }else{

                        while(index>0){

                            if(a[i][index]=='+') {
                                lista.add(a[i].substring(0,index))
                                lista.add(a[i].substring(index+1,a[i].length))
                                Log.i("cao",lista.toString())
                                rez1=  saberiDvaBroja(lista)
                                break
                            }
                            else if(a[i][index]=='-') {
                                lista.add(a[i].substring(0,index))
                                lista.add(a[i].substring(index+1,a[i].length))
                                Log.i("cao",lista.toString())
                                rez1=  oduzmiDvaBroja(lista)
                                break
                            }
                            else if (a[i][index] == '*') {
                                lista.add(a[i].substring(0, index))
                                lista.add(a[i].substring(index + 1, a[i].length))
                                Log.i("cao", lista.toString())
                                rez1= pomnoziDvaBroja(lista)
                                break
                            }
                            else if (a[i][index] == '%') {
                                lista.add(a[i].substring(0, index))
                                lista.add(a[i].substring(index + 1, a[i].length))
                                Log.i("cao", lista.toString())
                                rez1= podeliDvaBroja(lista)
                                break
                            }
                            index--
                        }
                    }
                    rez1
                }

                a[i].contains("-") -> {
                    oduzmiDvaBroja(a[i].split("-"))

                }
                a[i].contains("%") -> {
                    procenatBroja(a[i].split("%"))
                }

                a[i].contains("/") -> {
                    podeliDvaBroja(a[i].split("/"))
                }

                else -> a[i].toDouble()
            }
            if (rez == 0.0) rez = broj
            else rez *= broj

        }

        return rez
    }

    private fun podeliDvaBroja(a: List<String>): Double {
        var rez = 0.0
        val n = a.size
        var broj = 0.0
        for (i in 0 until n) {
            Log.i("cao", "testiram broj" + a[i].toString())
            broj = when {

                a[i].contains("(") -> {
                    var rez1=0.0
                    Log.i("cao","Sadrzim")
                    var lista=LinkedList<String>()

                    var index=a[i].indexOf("(")
                    if(index==0){
                        index=a[i].indexOf(")")

                        if(index==a[i].length-1) {
                            var str=a[i].substring(a[i].indexOf('(')+1,a[i].indexOf(')'))
                            rez1=saberiDvaBroja(str.split("+"))
                        }
                        else {
                            index++
                            // Log.i("cao",a[i].substring(0,index))

                            //Log.i("cao",lista.toString())
                            //  saberiDvaBroja(lista)
                            while (index > 0) {

                                if (a[i][index] == '+') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1=  saberiDvaBroja(lista)
                                    break
                                } else if (a[i][index] == '-') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= oduzmiDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '*') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= pomnoziDvaBroja(lista)
                                    break
                                }
                                else if (a[i][index] == '%') {
                                    lista.add(a[i].substring(0, index))
                                    lista.add(a[i].substring(index + 1, a[i].length))
                                    Log.i("cao", lista.toString())
                                    rez1= podeliDvaBroja(lista)
                                    break
                                }
                                index++
                            }
                        }
                        // Log.i("cao",a[i].substring(0,index))

                        //Log.i("cao",lista.toString())
                        //  saberiDvaBroja(lista)

                    }else{

                        while(index>0){

                            if(a[i][index]=='+') {
                                lista.add(a[i].substring(0,index))
                                lista.add(a[i].substring(index+1,a[i].length))
                                Log.i("cao",lista.toString())
                                rez1=  saberiDvaBroja(lista)
                                break
                            }
                            else if(a[i][index]=='-') {
                                lista.add(a[i].substring(0,index))
                                lista.add(a[i].substring(index+1,a[i].length))
                                Log.i("cao",lista.toString())
                                rez1=  oduzmiDvaBroja(lista)
                                break
                            }
                            else if (a[i][index] == '*') {
                                lista.add(a[i].substring(0, index))
                                lista.add(a[i].substring(index + 1, a[i].length))
                                Log.i("cao", lista.toString())
                                rez1= pomnoziDvaBroja(lista)
                                break
                            }
                            else if (a[i][index] == '%') {
                                lista.add(a[i].substring(0, index))
                                lista.add(a[i].substring(index + 1, a[i].length))
                                Log.i("cao", lista.toString())
                                rez1= podeliDvaBroja(lista)
                                break
                            }
                            index--
                        }
                    }
                    rez1
                }


                a[i].contains("-") -> {
                    oduzmiDvaBroja(a[i].split("-"))

                }

                a[i].contains("*") -> {
                    podeliDvaBroja(a[i].split("*"))
                }

                a[i].contains("%") -> {
                    procenatBroja(a[i].split("%"))
                }


                else -> a[i].toDouble()
            }
            if (rez == 0.0) rez = broj
            else rez /= broj

        }

        return rez
    }

    fun zeroClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("0")
        textview.setText(text.toString())

    }

    fun oneClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("1")
        textview.setText(text.toString())

    }

    fun twoClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("2")
        textview.setText(text.toString())

    }

    fun threeClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("3")
        textview.setText(text.toString())

    }

    fun fourClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("4")
        textview.setText(text.toString())

    }

    fun fiveClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("5")
        textview.setText(text.toString())

    }

    fun sixClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("6")
        textview.setText(text.toString())

    }

    fun sevenClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("7")
        textview.setText(text.toString())

    }

    fun eightClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("8")
        textview.setText(text.toString())

    }

    fun nineClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("9")
        textview.setText(text.toString())

    }

    fun plusClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("+")
        textview.setText(text.toString())

    }

    fun subClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("-")
        textview.setText(text.toString())

    }

    fun multiplyClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("*")
        textview.setText(text.toString())

    }

    fun devisionClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("/")
        textview.setText(text.toString())

    }

    fun deleteElement(v: View) {
        val text = textview.text.toString()
        if (text.isNotEmpty())
            textview.setText(text.substring(0, text.length - 1))
    }

    fun procentClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("%")
        textview.setText(text.toString())

    }

    fun levaZagradaClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append("(")
        textview.setText(text.toString())

    }

    fun desnaZagradaClicked(v: View) {
        val text = StringBuilder(textview.text.toString())

        text.append(")")
        textview.setText(text.toString())

    }

}

