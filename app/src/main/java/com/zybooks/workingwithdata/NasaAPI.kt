package com.zybooks.workingwithdata

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NasaAPI : AppCompatActivity() {
    lateinit var startDateTextView: TextView
    lateinit var startDateEditText: EditText
    lateinit var endDateTextView: TextView
    lateinit var endDateEditText: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var imageDataSet: ArrayList<Pair<String, String>>
    lateinit var imageCustomAdapter: ImageCustomAdapter
    lateinit var countEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nasa_api)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setTitle("WWD - NASA")


        startDateTextView = findViewById(R.id.dateTextView)
        startDateEditText = findViewById(R.id.dateEditText)
        startDateEditText.doAfterTextChanged {
            countEditText.isEnabled = startDateEditText.text.isEmpty()
        }

        endDateTextView = findViewById(R.id.endDateTextView)
        endDateEditText = findViewById(R.id.endDateEditText)
        endDateEditText.doAfterTextChanged {
            countEditText.isEnabled = endDateEditText.text.isEmpty()
        }

        countEditText = findViewById(R.id.countEditText)
        countEditText.doAfterTextChanged {
            startDateEditText.isEnabled = countEditText.text.isEmpty()
            endDateEditText.isEnabled = countEditText.text.isEmpty()
        }

        var rangeCheckBox = findViewById(R.id.rangeCheckBox) as CheckBox
        rangeCheckBox.setOnClickListener {
            if (rangeCheckBox.isChecked ) {
                endDateTextView.visibility = View.VISIBLE
                endDateEditText.visibility = View.VISIBLE
                startDateTextView.text = "Start"
            } else {
                endDateTextView.visibility = View.INVISIBLE
                endDateEditText.visibility = View.INVISIBLE
                startDateTextView.text = "Date"

            }
        }
        imageDataSet = arrayListOf(Pair("https://apod.nasa.gov/apod/image/1908/EtnaCloudsMoon_Giannobile_960.jpg", "What's happening above that volcano? Although Mount Etna is seen erupting, the clouds are not related to the eruption. They are lenticular clouds formed when moist air is forced upwards near a mountain or volcano.  The surreal scene was captured by chance late last month when the astrophotographer went to Mount Etna, a UNESCO World Heritage Site in Sicily, Italy, to photograph the conjunction between the Moon and the star Aldebaran. The Moon appears in a bright crescent phase, illuminating an edge of the lower lenticular cloud.  Red hot lava flows on the right.  Besides some breathtaking stills, a companion time-lapse video of the scene shows the lenticular clouds forming and wavering as stars trail far in the distance.    Follow APOD in English on: Instagram, Facebook,  Reddit, or Twitter"),
            Pair("https://img.youtube.com/vi/f8rs3bcEO-o/0.jpg", "What would it look like to orbit a black hole? Many black holes are surrounded by swirling pools of gas known as accretion disks. These disks can be extremely hot, and much of the orbiting gas will eventually fall through the black hole's event horizon -- where it will never be seen again. The featured animation is an artist's rendering of the curious disk spiraling around the supermassive black hole at the center of spiral galaxy NGC 3147.  Gas at the inner edge of this disk is so close to the black hole that it moves unusually fast -- at 10 percent of the speed of light. Gas this fast shows relativistic beaming, making the side of the disk heading toward us appear significantly brighter than the side moving away.  The animation is based on images of NGC 3147 made recently with the Hubble Space Telescope.    Astrophysicists: Browse 2,000+ codes in the Astrophysics Source Code Library"),
            Pair("https://apod.nasa.gov/apod/image/1908/HumanSpaceship2_TsevisHubbleRJN_960.jpg", "You are a spaceship soaring through the universe. So is your dog. We all carry with us trillions of microorganisms as we go through life. These multitudes of bacteria, fungi, and archaea have different DNA than you. Collectively called your microbiome, your shipmates outnumber your own cells. Your crew members form communities, help digest food, engage in battles against intruders, and sometimes commute on a liquid superhighway from one end of your body to the other.  Much of what your microbiome does, however, remains unknown. You are the captain, but being nice to your crew may allow you to explore more of your local cosmos."),
            Pair("https://apod.nasa.gov/apod/image/1908/orion1901_ritchey1024.jpg", "By the turn of the 20th century advances in photography contributed an important tool for astronomers. Improving photographic materials, long exposures, and new telescope designs produced astronomical images with details not visible at the telescopic eyepiece alone. Remarkably recognizable to astrophotographers today, this stunning image of the star forming Orion Nebula was captured in 1901 by American astronomer and telescope designer George Ritchey. The original glass photographic plate, sensitive to green and blue wavelengths, has been digitized and light-to-dark inverted to produce a positive image. His hand written notes indicate a 50 minute long exposure that ended at dawn and a reflecting telescope aperture of 24 inches masked to 18 inches to improve the sharpness of the recorded image. Ritchey's plates from over a hundred years ago preserve astronomical data and can still be used for exploring astrophysical processes."),
            Pair("https://apod.nasa.gov/apod/image/1908/ElephantTrunk_Ayoub1024.jpg", "Like an illustration in a galactic Just So Story, the Elephant's Trunk Nebula winds through the emission nebula and young star cluster complex IC 1396, in the high and far off constellation of Cepheus. Also known as vdB 142, the cosmic elephant's trunk is over 20 light-years long. This colorful close-up view was recorded through narrow band filters that transmit the light from ionized hydrogen, sulfur, and oxygen atoms in the region.  The resulting composite highlights the bright swept-back ridges that outline pockets of cool interstellar dust and gas. Such embedded, dark, tendril-shaped clouds contain the raw material for star formation and hide protostars within. Nearly 3,000 light-years distant, the relatively faint IC 1396 complex covers a large region on the sky, spanning over 5 degrees. The dramatic scene spans a 1 degree wide field, about the size of 2 Full Moons."),
            Pair("https://apod.nasa.gov/apod/image/1908/PerseidsPloughCow1024.jpg", ""),
            Pair("https://apod.nasa.gov/apod/image/2210/GrbRings_SwiftMiller_960.jpg",""))
        imageCustomAdapter = ImageCustomAdapter(imageDataSet)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = imageCustomAdapter

    }
}