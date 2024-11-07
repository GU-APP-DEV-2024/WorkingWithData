package com.zybooks.workingwithdata

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.PrintWriter

/*
    Shared Preferences
    1. val preference = getPreferences(Context.MODE_PRIVATE)
    2. val prefEditor = preference.edit()
    3. prefEditor.put*(KEY, VALUE)
    4. prefEditor.get*(KEY, DEFAULT_VALUE)
    5. prefEditor.apply()
*/
const val SAVED_DATA_KEY = "SAVED_DATA"
class MainActivity : AppCompatActivity() {
    lateinit var savedDataButton:Button
    lateinit var savedDataEditText:EditText
    lateinit var dataset: ArrayList<Pair<String, String>>
    lateinit var customAdapter: CustomAdapter

    data class Contact(val name: String, val number:String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var pref = getPreferences(Context.MODE_PRIVATE)
        savedDataEditText = findViewById<EditText>(R.id.saved_data_editText)
        savedDataEditText.setText(pref.getString(SAVED_DATA_KEY, "No Saved Data"))

        savedDataButton = findViewById<Button>(R.id.saved_data_Button)
        savedDataButton.setOnClickListener {
            var pref = getPreferences(Context.MODE_PRIVATE)
            var editor = pref.edit()
            editor.putString(SAVED_DATA_KEY, savedDataEditText.text.toString())
            editor.apply()
        }

        var loadContacts = findViewById<Button>(R.id.loadContacts)
        loadContacts.setOnClickListener {
            dataset.clear()
            dataset.addAll(loadFromFile())
        }

        var saveContacts = findViewById<Button>(R.id.saveContacts)
        saveContacts.setOnClickListener {
            saveToFile()
        }

        var addContact = findViewById<Button>(R.id.addContact)
        addContact.setOnClickListener {
            dataset += Pair("New Person","5555555555")
            customAdapter.notifyDataSetChanged()
        }

        var clearContacts = findViewById<Button>(R.id.clear)
        clearContacts.setOnClickListener {
            dataset.clear()
            customAdapter.notifyDataSetChanged()
        }

        //val dataset = arrayOf(Pair("Bob", "5553423334"), Pair("Susan", "5557841415"), Pair("Joe", "5554185545"))
        dataset = loadFromFile()
        customAdapter = CustomAdapter(dataset)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
    }

    private fun saveToFile() {
        val outputStream = openFileOutput("ContactList.txt", Context.MODE_PRIVATE)
        val writer = PrintWriter(outputStream)

        var json = JSONObject()
        // Write each task on a separate line
        for ((index, pair) in dataset.withIndex()) {
            var newJSONObject = JSONObject()
            newJSONObject.put("name", pair.first)
            newJSONObject.put("number", pair.second)
            json.put(index.toString(), newJSONObject)
            //writer.println(pair.first + "," + pair.second)
        }
        val jsonString = json.toString(3)
        writer.println(jsonString)
        writer.close()
    }

    private fun loadFromFile(): ArrayList<Pair<String, String>> {
        var ret = arrayListOf<Pair<String, String>>()
        try {
            val inputStream = openFileInput("ContactList.txt")
            val reader = inputStream.bufferedReader()

            // Append each task to stringBuilder
            reader.forEachLine {
                var splitLine = it.split(',')
                ret.add(Pair(splitLine[0], splitLine[1]))
            }
            return ret
        } catch ( e: FileNotFoundException ) { return ret }


    }
}