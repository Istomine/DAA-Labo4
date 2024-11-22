package ch.heigvd.iict.daa.template

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.fragments.FragmentControles
import ch.heigvd.iict.daa.template.fragments.FragmentNotes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager

            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FragmentNotes())
                    .commit()
            } else {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_notes, FragmentNotes())
                    .replace(R.id.fragment_controles, FragmentControles())
                    .commit()
            }
        }
    }
}