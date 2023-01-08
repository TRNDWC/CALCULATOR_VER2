package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastOpe="+"
    var num01: Double=0.0
    var num02: Double=0.0
    var Ope=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput= findViewById(R.id.textView)
    }

    fun Format(s: String): String {
        if (s.contains(".")==true)
        {
            var ans=s
            while (ans[ans.length-1]=='0') ans=ans.substring(0,s.length-1)
            if (ans[ans.length-1]=='.') ans=ans.substring(0,s.length-2)
            return ans
        }
        else return s
    }
    fun onClick(view: View){
        if (Ope==1) tvInput?.text=""
        Ope=0
        tvInput?.append((view as Button).text)
    }

    fun onDot(view: View){
        var tvValue=tvInput?.text
        tvValue?.let{
            if (tvValue.contains(".")==false)
            {
                if (tvValue!="") tvInput?.append(".")
                else tvInput?.append("0.")
            }
        }
    }

    fun onClr (view: View){
        tvInput?.text=""
        num01=0.0
        num02=0.0
        Ope=0
        lastOpe="+"
    }

    fun onCt (view: View){
        var str: String= tvInput?.text.toString()
        var gt= str.toDouble()
        if (gt<0) gt= abs(gt)
        else gt=-gt
        tvInput?.text=Format(gt.toString())
    }

    fun onPer (view: View){
        var str: String= tvInput?.text.toString()
        var gt= str.toDouble()
        tvInput?.text=Format((gt/100).toString())
    }

    fun onOperator(view: View) {
        if (Ope == 1) {
            lastOpe = (view as Button).text.toString()
        } else {
            if (tvInput?.text == "") {
                lastOpe = (view as Button).text.toString()
            } else {
                var str: String = tvInput?.text.toString()
                var gt = str.toDouble()
                num02 = gt
                Ope = 1
                if (lastOpe == "+") num01 += num02
                else if (lastOpe == "-") num01 -= num02
                else if (lastOpe == "*") num01 *= num02
                else if (lastOpe == "/" && num02 != 0.0) num01 /= num02

                if (lastOpe == "/" && num02 == 0.0) {
                    tvInput?.text = "ERROR!!"
                    return
                } else tvInput?.text = Format(num01.toString())
                num02 = 0.0
                lastOpe = (view as Button).text.toString()
            }
        }
    }
    fun onEqual(view: View){
        if (Ope==1)
        {
            num01+=num01
            tvInput?.text=Format(num01.toString())
        }
        else
        {
            var str: String= tvInput?.text.toString()
            var gt= str.toDouble()
            num02=gt
            if (lastOpe=="+") num01+=num02
            else if (lastOpe=="-") num01-=num02
            else if (lastOpe=="*") num01*=num02
            else if (lastOpe=="/"&&num02!=0.0) num01/=num02

            if (lastOpe=="/"&&num02==0.0) {
                tvInput?.text="ERROR!!"
                return}
            else tvInput?.text=Format(num01.toString())
            num02=0.0
            num01=0.0
            lastOpe="+"
        }
    }
}

//package com.example.calculator
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//
//class MainActivity : AppCompatActivity() {
//    // Represent whether the lastly pressed key is numeric or not
//    var lastNumeric: Boolean = false
//
//    // If true, do not allow to add another DOT
//    var lastDot: Boolean = false
//
//    private var tvInput:TextView?=null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        tvInput = findViewById(R.id.tvInput)
//    }
//
//    fun onDigit(view: View){
//        tvInput?.append((view as Button).text)
//        lastNumeric = true
//    }
//
//    fun onClear(view: View){
//        tvInput?.text = ""
//        lastNumeric = false
//        lastDot = false
//    }
//    /**
//     * Append . to the TextView
//     */
//    fun onDecimalPoint(view: View) {
//
//        // If the last appended value is numeric then append(".") or don't.
//        if (lastNumeric && !lastDot) {
//            tvInput?.append(".")
//            lastNumeric = false // Update the flag
//            lastDot = true // Update the flag
//        }
//    }
//
//    /**
//     * Append +,-,*,/ operators to the TextView as per the Button.Text
//     */
//    fun onOperator(view: View) {
//        tvInput?.text?.let {
//            if (lastNumeric && !isOperatorAdded(it.toString())) {
//                tvInput?.append((view as Button).text)
//                lastNumeric = false // Update the flag
//                lastDot = false    // Reset the DOT flag
//            }
//        }
//
//    }
//
//    /**
//     * Calculate the output
//     */
//    fun onEqual(view: View) {
//        // If the last input is a number only, solution can be found.
//        if (lastNumeric) {
//            // Read the textView value
//            var tvValue = tvInput?.text.toString()
//            var prefix = ""
//            try {
//
//                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
//                if (tvValue.startsWith("-")) {
//                    prefix = "-"
//                    tvValue = tvValue.substring(1);
//                }
//
//                // If the inputValue contains the Division operator
//                when {
//                    tvValue.contains("/") -> {
//                        // Will split the inputValue using Division operator
//                        val splitedValue = tvValue.split("/")
//
//                        var one = splitedValue[0] // Value One
//                        val two = splitedValue[1] // Value Two
//
//                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
//                            one = prefix + one
//                        }
//
//                        /*Here as the value one and two will be calculated based on the operator and
//                                if the result contains the zero after decimal point will remove it.
//                                And display the result to TextView*/
//                        tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
//                    }
//                    tvValue.contains("*") -> {
//                        // If the inputValue contains the Multiplication operator
//                        // Will split the inputValue using Multiplication operator
//                        val splitedValue = tvValue.split("*")
//
//                        var one = splitedValue[0] // Value One
//                        val two = splitedValue[1] // Value Two
//
//                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
//                            one = prefix + one
//                        }
//
//                        /** Here as the value one and two will be calculated based on the operator and
//                        if the result contains the zero after decimal point will remove it.
//                        And display the result to TextView
//                         */
//                        tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
//                    }
//                    tvValue.contains("-") -> {
//
//                        // If the inputValue contains the Subtraction operator
//                        // Will split the inputValue using Subtraction operator
//                        val splitedValue = tvValue.split("-")
//
//                        var one = splitedValue[0] // Value One
//                        val two = splitedValue[1] // Value Two
//
//                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
//                            one = prefix + one
//                        }
//
//                        /** Here as the value one and two will be calculated based on the operator and
//                        if the result contains the zero after decimal point will remove it.
//                        And display the result to TextView
//                         */
//                        tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
//                    }
//                    tvValue.contains("+") -> {
//                        // If the inputValue contains the Addition operator
//                        // Will split the inputValue using Addition operator
//                        val splitedValue = tvValue.split("+")
//
//                        var one = splitedValue[0] // Value One
//                        val two = splitedValue[1] // Value Two
//
//                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
//                            one = prefix + one
//                        }
//
//                        /**Here as the value one and two will be calculated based on the operator and
//                        if the result contains the zero after decimal point will remove it.
//                        And display the result to TextView
//                         */
//                        tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
//                    }
//                }
//            } catch (e: ArithmeticException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    /**
//     * Remove the zero after decimal point
//     */
//    private fun removeZeroAfterDot(result: String): String {
//
//        var value = result
//
//        if (result.contains(".0")) {
//            value = result.substring(0, result.length - 2)
//        }
//
//        return value
//    }
//
//    /**
//     * It is used to check whether any of the operator is used or not.
//     */
//    private fun isOperatorAdded(value: String): Boolean {
//
//        /**
//         * Here first we will check that if the value starts with "-" then will ignore it.
//         * As it is the result value and perform further calculation.
//         */
//
//        return if (value.startsWith("-")) {
//            false
//        } else {
//            (value.contains("/")
//                    || value.contains("*")
//                    || value.contains("-")
//                    || value.contains("+"))
//        }
//    }
//}