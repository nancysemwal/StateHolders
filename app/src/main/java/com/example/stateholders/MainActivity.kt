package com.example.stateholders

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.stateholders.ui.theme.StateHoldersTheme

class MainActivity : ComponentActivity() {
    var variable = mutableStateOf(5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateHoldersTheme {
                Column {
                    Parent()
                    Parent2()
                    Parent3()
                    Parent4()
                    Parent5(variable.value) { v -> variable.value = v }
                    Parent6(variable.value)
                    Button(onClick = { variable.value = 3 }) {
                        Text("MainActivity Button")
                    }
                    Text(text=variable.value.toString())
                }
            }
        }
    }
}

data class MyState(
    var _counter : Int
) {
    var counter = _counter
    fun increment() { counter++ }
}

data class MyState2(
    var _counter : Int
) {
    var counter by mutableStateOf(_counter)
    fun increment() {
        Log.d("haptork", counter.toString())
        counter++
    }
}

@Composable
fun rememberMyState(counter: Int = 1) = remember(mutableStateOf(counter)) {
    MyState(counter)
}

@Composable
fun rememberMyState2(counter:  Int = 3)
 = remember(counter) {
     MyState2(counter)
}

@Composable
fun Parent() {
    var counter = remember() { mutableStateOf(3)}
    var state = remember() {
        mutableStateOf(MyState(0))
    }//rememberMyState(4)
    Log.d("haptork", state.value.counter.toString())
    Column() {
        Text(text = "Counter is ${state.value.counter}")
        Button(onClick = { Log.d("HAPTORK", state.value.counter.toString())
            state.value = MyState(state.value.counter+1)
            //st.value.increment()
        })
        {
            //Text(text = "Not Working")
            Text(text = "State from composable")

        }
    }
}

@Composable
fun Parent2() {
    var state = rememberMyState2()
    Log.d("haptork", state.counter.toString())
    Column() {
        Text(text = "Counter is ${state.counter}")
        Button(onClick = { Log.d("HAPTORK", state.counter.toString())
            state.increment()})
        {
            Text(text = "State from class")
        }
    }
}

@Composable
fun Parent3() {
    var counter = remember() { mutableStateOf(3)}
    var state : MutableState<MyState?> = remember() {
        mutableStateOf(null)
    }//rememberMyState(4)
    //Log.d("haptork", st.value.counter.toString())
    Column() {
        if (state.value != null) {
            Text(text = "Counter is ${state.value!!.counter}")
        } else {
            Text(text = "Counter is null")
        }
        Button(onClick = { //Log.d("HAPTORK", st.value.counter.toString())
            if (state.value != null) {
                state.value = MyState(state.value!!.counter+1)
            } else {
                state.value = MyState(0)
            }
            //st.value.increment()
        })
        {
            Text(text = "Null State & from composable")
        }
    }
}

@Composable
fun Parent4() {
    var state : MyState2? = remember() {
        MyState2(0)
    }
    Log.d("haptork", state?.counter.toString())
    Column() {
        if(state != null){
            Text(text = "Counter is ${state?.counter}")
        }
        else{
            Text(text = "Counter is null")
        }
        Button(onClick = {
            if(state == null || state?.counter == 10){
                state = MyState2(0)
                state?.increment()
            }
            state?.increment()
        })
        {
            Text(text = "Till 10")
        }
    }
}


@Composable
fun Parent5(variable:Int, setVariable:(Int)->Unit) {
    var counter = remember(variable) { mutableStateOf(variable)}
    var state : MutableState<MyState?> = remember() {
        mutableStateOf(null)
    }//rememberMyState(4)
    //Log.d("haptork", st.value.counter.toString())
    Column() {
        if (state.value != null) {
            Text(text = "Counter is ${state.value!!.counter}")
        } else {
            Text(text = "Counter is null")
        }
        Button(onClick = { //Log.d("HAPTORK", st.value.counter.toString())
            if (state.value != null) {
                //st.value!!.counter = st.value!!.counter+1
                state.value = MyState(state.value!!.counter+1)
                setVariable(state.value!!.counter)
            } else {
                state.value = MyState(0)
            }
            //st.value.increment()
        })
        {
            Text(text = "From Composable & set variable")
        }
        Text(text = "Variable set from Parent 5: ${variable}")
    }
}


@Composable
fun Parent6(variable:Int) {
    var counter = remember(variable) { mutableStateOf(variable)}
    var state : MutableState<MyState?> = remember() {
        mutableStateOf(null)
    }//rememberMyState(4)
    //Log.d("haptork", st.value.counter.toString())
    Column() {
        if (state.value != null) {
            Text(text = "Counter is ${state.value!!.counter}")
        } else {
            Text(text = "Counter is null")
        }
        Button(onClick = { //Log.d("HAPTORK", st.value.counter.toString())
            if (state.value != null) {
                //st.value!!.counter = st.value!!.counter+1
                state.value = MyState(state.value!!.counter+1)
            } else {
                state.value = MyState(0)
            }
            //st.value.increment()
        })
        {
            Text(text = "No setVariable()")
        }
        Text(text = "In p6: ${variable}")
    }
}

@Composable
fun Child(counter : MutableState<Int>, onCounterChange : () -> Unit, buttonName : String){
    Text(text = "Child ${counter.value}")
    Button(onClick = { onCounterChange() }) {
        Text(text = buttonName)
    }
}