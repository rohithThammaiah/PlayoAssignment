package me.assignment.playo.newsapp.newsDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.news_details_screen.*
import me.assignment.playo.newsapp.R


class NewsDetailsScreen : AppCompatActivity() {

    companion object {

        const val KEY_NEWS_URL = "newsURL"

        fun getIntent(callingActivity: AppCompatActivity, newsUrl: String): Intent {
            val intent = Intent(callingActivity, NewsDetailsScreen::class.java)
            intent.putExtra(KEY_NEWS_URL, newsUrl)
            return intent
        }
    }

    private var newsUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_details_screen)

        newsUrl = intent.getStringExtra(KEY_NEWS_URL)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progress_horizontal.progress = newProgress

                if (newProgress == 100)
                    progress_horizontal.visibility = View.GONE
            }
        }

        newsUrl?.let {
            web_view.webViewClient = WebViewClient()
            web_view.settings.javaScriptEnabled = true
            web_view.loadUrl(newsUrl)
        } ?: showEmptyStringError()
    }

    private fun showEmptyStringError() {
        Snackbar.make(window.decorView, "Url should not be null", Snackbar.LENGTH_INDEFINITE)
            .setAction(
                "Go Back"
            ) {
                onBackPressed()
            }.show()
    }
}
