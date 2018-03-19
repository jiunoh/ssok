package com.example.jiun.sookpam.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    private lateinit var backImageButton: ImageButton
    private lateinit var versionNumberTextView: TextView
    private lateinit var licenseTextView: TextView
    private lateinit var feedbackTextView: TextView
    private lateinit var developerTextView: TextView
    private lateinit var informationIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        initialize()
    }

    private fun initialize() {
        backImageButton = info_back_btn
        backImageButton.setOnClickListener { finish() }
        versionNumberTextView = info_version_number_txt
        versionNumberTextView.text = APP_VERSION
        licenseTextView = info_license_txt
        licenseTextView.setOnClickListener {
            informationIntent = Intent(applicationContext, LicenseActivity::class.java)
            startActivity(informationIntent)
        }
        feedbackTextView = info_feedback_txt
        feedbackTextView.setOnClickListener {
            sendFeedback(emailTo, emailSubject)
        }
        developerTextView = info_developer_txt
        developerTextView.setOnClickListener {
            informationIntent = Intent(applicationContext, DeveloperInfoActivity::class.java)
            startActivity(informationIntent)
        }
    }

    private fun sendFeedback(mailTo: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(Intent.EXTRA_EMAIL, mailTo)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        startActivity(intent)
    }

    companion object {
        const val APP_VERSION = "1.0.0"
        private val emailTo = arrayOf("1117hyemin@gmail.com", "juneoh227@gmail.com", "lync2846@gmail.com")
        private val emailSubject = "[SSOK]"
    }
}
