package com.example.oh_sheet



import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ManageTimesheetActivity : AppCompatActivity(), View.OnClickListener {
    //--------------------------------------------------------------------------\\
    //var textSearchView1 = findViewById<TextView>(R.id.searchDate1)
    //var textSearchView2 = findViewById<TextView>(R.id.searchDate2)

    //val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    //var selectedDate1: Date? = null
    //var selectedDate2: Date? = null

    //--------------------------------------------------------------------------\\
    private lateinit var recyclerView: RecyclerView

    //------------------------------------------------------------------------------------------------\\

    // Declare the Firebase Database reference
    private val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("timesheets")
    private lateinit var auth: FirebaseAuth

    //--------------------------------------------------------------------------------------------------\\
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_timesheet)

        auth = FirebaseAuth.getInstance()

        val currentUser: FirebaseUser? = auth.currentUser
        val currentUserId = currentUser?.uid
        Log.d("ManageTimesheetActivity", "Current User ID: $currentUserId")
        recyclerView = findViewById(R.id.recyclerViewTable)


        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val dataArray: ArrayList<TimesheetEntry> = ArrayList()

                for (snapshot in dataSnapshot.children) {
                    val timesheetEntry: TimesheetEntry? =
                        snapshot.getValue(TimesheetEntry::class.java)

                    // Check if the timesheet entry belongs to the current user
                    if (timesheetEntry != null && timesheetEntry.userId == currentUserId) {
                        timesheetEntries.add(timesheetEntry)
                    }
                }

                // Do something with the dataArray, containing timesheet entries of the current user
                setupRecyclerView(timesheetEntries)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })




        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    //------------------------------------------------------------------------------------------------\\
    private fun setupRecyclerView(dataArray: ArrayList<TimesheetEntry>) {
        val data = createSampleData(dataArray)
        val adapter = TableAdapter(data)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    //------------------------------------------------------------------------------------------------\\
    private fun createSampleData(dataArray: ArrayList<TimesheetEntry>): ArrayList<TableRowC> {
        val data = ArrayList<TableRowC>()

        // Assuming these become the column headings
        data.add(TableRowC("Descript.", "Date", "Start Time", "End Time", "Categ."))

        val line: String = "------------"
        // Create line
        data.add(TableRowC(line, line, line, line, line))

        // Iterate through dataArray and add the data to the ArrayList
        if (!dataArray.isEmpty()) {
            for (entry in dataArray) {
                val val1: String = entry.category.name.toString() // category
                val val2: String = entry.date.toString() // date
                val val3: String = entry.description.toString() // description
                val val4: String = entry.endTime.toString() // end time
                val val5: String = entry.startTime.toString() // start time

                data.add(TableRowC(val3, val2, val5, val4, val1))
            }
        }

        // Return the populated ArrayList back to setupRecyclerView()
        return data
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //------------------------------------------------------------------------------------------------\\
    //  Everything past this point is commented out because im scared it breaks the application
    //  this code is for creating search functionality
    //  the onClick method calls the setupRecycler method that updates the recycler view with a new set of data
    //  this new set of data comes from the 3rd class
    //  new set of data = new table
    //  i despise working with dates

    //also disabled it cos i felt that 3 methods happening on the onClcik was a bit too much
    //------------------------------------------------------------------------------------------------\\
    override fun onClick(v: View?) {

        //setupRecyclerSearch()
    }

    //------------------------------------------------------------------------------------------------\\
    /*private fun setupRecyclerSearch() {
        val data2 = createSearchData()
        val adapter = TableAdapter(data2)

        //updates the adapter with new data
        adapter.notifyDataSetChanged()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }*/

    //------------------------------------------------------------------------------------------------\\
    /*private fun createSearchData(): ArrayList<TableRowC> {

        val data2 = ArrayList<TableRowC>()

        //im assuming these become the column headings
        data2.add(TableRowC("Description", "Start Date","Start Time", "End Time", "Categories"))

        //just some default values
        data2.add(TableRowC("Value 1", "Value 2","Value 3", "Value 4", "value 5"))

        val dateString1 = textSearchView1.text.toString()
        val dateString2 = textSearchView2.text.toString()

        try {
            selectedDate1 = dateFormat.parse(dateString1)
            selectedDate2 = dateFormat.parse(dateString2)
        } catch (e: ParseException) {
            // Handle parsing error, e.g., invalid date format
            e.printStackTrace()
        }

        // Handle the selected date if parsing was successful
        if ((selectedDate1 != null) && (selectedDate2 != null)) {
            // Handle the selected date as needed
            val array: ArrayList<CreateTimesheetActivity.TimesheetEntry> = obj.timesheetEntries
            //put Daniel H data into the values in " ... "

            val startCalendar = Calendar.getInstance()
            startCalendar.time = dateFormat.parse(dateString1) as Date

            val endCalendar = Calendar.getInstance()
            endCalendar.time = dateFormat.parse(dateString2) as Date

            val targetCalendar = Calendar.getInstance()

            if(!array.indices.isEmpty()){

                for(i in array.indices){

                    targetCalendar.time = dateFormat.parse(array.get(i).date.toString()) as Date

                    // Check if the target date is between the start and end dates
                    if(targetCalendar.after(startCalendar) && targetCalendar.before(endCalendar)){

                        //will get the corresponding timesheet values at the matching index of the date that passed the
                        //if statement
                        val val1: String = array.get(i).category.toString()// category
                        val val2: String = array.get(i).date.toString() //date
                        val val3: String = array.get(i).description.toString() //description
                        val val4: String = array.get(i).endTime.toString() //end time
                        val val5: String = array.get(i).startTime.toString() //start time
                        val val6: String = array.get(i).photoPath.toString() //photo

                        data2.add(TableRowC(val3, val2, val5,val4, val1))
                    }

                }
            }

        } else {
            // Parsing error occurred, handle accordingly

        }

        return data2
    }*/
    //------------------------------------------------------------------------------------------------------------------//













}

//------------------------------------------End of File------------------------------------------------------\\