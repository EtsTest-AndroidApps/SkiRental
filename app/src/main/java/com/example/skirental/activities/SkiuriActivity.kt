package com.example.skirental.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skirental.R
import com.example.skirental.adapters.SkiuriRecyclerAdapter
import com.example.skirental.databinding.ActivitySkiuriBinding
import com.example.skirental.infoactivities.InfoSkiuriActivity
import com.example.skirental.miscellaneous.TopSpacingItemDecoration
import com.example.skirental.models.PerecheSki
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class SkiuriActivity : AppCompatActivity(), SkiuriRecyclerAdapter.OnItemClickListener {

    private lateinit var binding: ActivitySkiuriBinding
    private lateinit var skiuriAdapter: SkiuriRecyclerAdapter
    private var listaSkiuri = ArrayList<PerecheSki>()
    val db = FirebaseFirestore.getInstance()
    private val TAG = "vlad"
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkiuriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG,"skiuriActivity ${intent.getStringExtra("inaltime")}")

        preiaSchiuriDinBD()


    }

    private fun preiaSchiuriDinBD(){
        var sem : Boolean = false
        db.collection("schiuri")
                .whereLessThanOrEqualTo("lungime",intent.getStringExtra("inaltime").toString().toInt()+5)
                .whereGreaterThanOrEqualTo("lungime",intent.getStringExtra("inaltime").toString().toInt()-25)
                .whereEqualTo("inchiriat", sem)
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.size() != 0){
                        for(document in documents){
                            val perecheSki = PerecheSki(document.get("firma").toString(),
                                    document.get("descriere").toString(),
                                    document.id)
                            listaSkiuri.add(perecheSki)
                            Log.d(TAG,perecheSki.imagine)
                        }
                        initRecyclerView()
                        addDataSet(listaSkiuri)
                    } else{
                        Toast.makeText(this, "There are no skis available for your height.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{ exception ->
                    Log.w(TAG,"Error getting documents: ", exception)
                }
    }

    private fun addDataSet(data: ArrayList<PerecheSki>){
        skiuriAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        val recycler_view: RecyclerView = findViewById(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(this@SkiuriActivity)
        val topSpacingDecoration = TopSpacingItemDecoration(30)
        recycler_view.addItemDecoration(topSpacingDecoration)
        skiuriAdapter = SkiuriRecyclerAdapter(this)
        recycler_view.adapter = skiuriAdapter

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked!", Toast.LENGTH_SHORT).show()
        val clickedItem: PerecheSki = listaSkiuri[position]
        skiuriAdapter.notifyItemChanged(position)
        val intent = Intent(this, InfoSkiuriActivity::class.java)
        intent.putExtra("titlu",clickedItem.descriere)
        intent.putExtra("username",clickedItem.firma)
        startActivity(intent)
    }
}