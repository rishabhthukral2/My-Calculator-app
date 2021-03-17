package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)       //to display each button text on screen
                                                    //needs to be casted,so that we can view txt of btn
        lastNumeric = true                          //digit entered so last numeric set to true

    }

    fun onClear(view: View){
        tvInput.text = ""
        lastNumeric = false         // on clear - values should be reset to default
        lastDot = false


    }

    fun onDecimalPoint(view: View){
        if( lastNumeric && !lastDot){           // to check whether last number is digit or a dot
            tvInput.append(".")
            lastNumeric = false             //set last number false bcz it is a dot
            lastDot = true                  //set last dot to true
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput.text.toString()       //it will copy tvInput value to tvValue
            var prefix = ""

            try{
                if(tvValue.startsWith("-")){            // 66-99 --> -33 - 66 --> app crashes
                    prefix= "-"                               //  this will ignore the first - sign as it is.
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                        // for eg. we have 99-1
                    var one = splitValue[0]         //it will take 99
                    var two = splitValue[1]          // it will take 1

                    if(!prefix.isEmpty()){          // if prefix is not empty
                        one = prefix + one           // this will make the value negative again..
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())     //need to convert into string..
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())  //need to convert into string..
                } else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())     //need to convert into string..
                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())  //need to convert into string..
                }





                }catch (e: ArithmeticException){           // our program doesn't crash if any arithemetic oprtn goes wrong
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result : String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric =false
            lastDot = true
        }

    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){               // it indicates value starts with -
           false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+")
                    || value.contains("-")                              //this allows us to add one operator
        }
    }
}