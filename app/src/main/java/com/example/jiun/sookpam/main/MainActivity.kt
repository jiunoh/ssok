package com.example.jiun.sookpam.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.jiun.sookpam.message.MessageContract
import com.example.jiun.sookpam.message.MessagePresenter
import android.view.View
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.model.ContactDBManager
import com.example.jiun.sookpam.data.DataActivity
import com.example.jiun.sookpam.searchable.ContactablesLoaderCallbacks
import com.example.jiun.sookpam.setting.SettingActivity
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*

import io.realm.Realm

class MainActivity : AppCompatActivity(), MessageContract.View {
    var CONTACT_QUERY_LOADER = 0
    var QUERY_KEY = "query"
    override lateinit var presenter: MessageContract.Presenter
    private lateinit var toolbar: Toolbar
    private lateinit var progressbar: ProgressBar
    private lateinit var adapter: MainListviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MainListviewAdapter()
        val listView = findViewById<View>(R.id.main_listView) as ListView
        listView.adapter = adapter
        initialize()
        presenter.start()
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            var selectedMain = listView.getItemAtPosition(position) as MainItem
            go(selectedMain.category)
        }
        if (intent != null) {
            handleIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }


    override fun onResume() {
        super.onResume()
        scatterCheckedCategories()
    }

    private fun scatterCheckedCategories() {
        val contactDBManager = applicationContext as ContactDBManager
        val departmentList = contactDBManager.getDepartmentList()
        adapter.clear()
        adapter.notifyDataSetChanged()
        for (division in departmentList) {
            if (SharedPreferenceUtil.get(applicationContext, division, false))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), division)
        }
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "공지")
    }

    override fun showToastMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        finish()
    }

    override fun onPause() {
        super.onPause()
        presenter.cancelMessageAsyncTask()
    }

    private fun initialize() {
        Realm.init(this)
        progressbar = main_message_prograssbar
        presenter = MessagePresenter(applicationContext, this, progressbar)
        setToolbar()
    }

    private fun setToolbar() {
        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun go(category: String) {
        val intent = Intent(this, DataActivity::class.java)
        intent.putExtra("category", category);
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun showPermissionMessage(permissionListener: PermissionListener) {
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleTitle(getString(R.string.read_sms_request_title))
                .setRationaleMessage(getString(R.string.read_sms_request_detail))
                .setDeniedTitle(getString(R.string.denied_read_sms_title))
                .setDeniedMessage(getString(R.string.denied_read_sms_detail))
                .setGotoSettingButtonText(getString(R.string.move_setting))
                .setPermissions(android.Manifest.permission.READ_SMS)
                .check()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_synchronization -> {
                presenter.start()
                scatterCheckedCategories()
                true
            }
            R.id.action_search -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     * @param intent
     */
    private fun handleIntent(intent: Intent) {
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH == intent.action) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            val query = intent.getStringExtra(SearchManager.QUERY)

            // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            val bundle = Bundle()
            bundle.putString(QUERY_KEY, query)

            val loaderCallbacks = ContactablesLoaderCallbacks(this)

            // Start the loader with the new query, and an object that will handle all callbacks.
            loaderManager.restartLoader<Cursor>(CONTACT_QUERY_LOADER, bundle, loaderCallbacks)
        }
    }

}