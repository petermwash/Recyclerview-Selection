package com.pemwa.recyclerviewselection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val adapter = ItemsAdapter()
    private var tracker: SelectionTracker<Long>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = adapter

        setUpAdapter()
        setUpTracker()
    }

    private fun setUpAdapter() {
        adapter.list = createRandomIntList()
        adapter.notifyDataSetChanged()
    }

    private fun setUpTracker() {
        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            rvItems,
            MyItemKeyProvider(rvItems),
            MyItemDetailsLookup(rvItems),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val nItems: Int? = tracker?.selection!!.size()
                    if (nItems == 3) {
                        launchSum(tracker?.selection!!)
                    }
                }
            }
        )

        adapter.tracker = tracker
    }

    /**
     * We map the selection to an ArrayList<Int> that we will pass to the new activity
     */
    private fun launchSum(selection: Selection<Long>) {
        val list = selection.map {
            adapter.list[it.toInt()]
        }.toList()
        SumActivity.launch(this, list as ArrayList<Int>)
    }

    private fun createRandomIntList(): List<Int> {
        val random = Random()
        return (1..10).map { random.nextInt() }
    }
}
