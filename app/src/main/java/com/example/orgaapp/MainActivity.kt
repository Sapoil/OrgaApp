package com.example.orgaapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.orgaapp.database.AppDatabase
import com.example.orgaapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var db: AppDatabase

    val sharedFloatingActionButton: FloatingActionButton by lazy{
        findViewById(R.id.fab)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "task-db"
        ).addCallback(object: RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase){
                super.onCreate(db)
                db.execSQL("INSERT INTO organizations (id,name,detail) VALUES (1,'Personnel','Organisation par défaut')")
            }
        }).build()

        binding.fab.setOnClickListener {
            binding.fab.isEnabled = false
            binding.fab.hide()
            lifecycleScope.launch {
                Log.d("Tache","Taches insérées : ")
                db.taskDao().getAll().forEach { task ->
                    Log.d("Taches",task.title)
                }
            }
            findNavController(R.id.content_main).navigate(R.id.action_firstFragment_to_addTaskFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}