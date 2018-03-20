package com.example.jiun.sookpam.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageButton
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_license.*

class LicenseActivity : AppCompatActivity() {
    private lateinit var backImageButton: ImageButton
    private lateinit var licenseRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        initialize()
    }

    private fun initialize() {
        backImageButton = license_back_btn
        backImageButton.setOnClickListener { finish() }
        licenseRecyclerView = license_recycler_view
        licenseRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        licenseRecyclerView.adapter = LicenseRecyclerAdapter(getLicenses())
    }

    private fun getLicenses(): List<LicenseItem> {
        val licenseItemList = ArrayList<LicenseItem>()
        licenseItemList.add(LicenseItem("Gilde", "License for everything not in third_party and not otherwise marked:\n" +
                "Copyright 2014 Google, Inc. All rights reserved.\n" +
                "Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:\n" +
                "\n" +
                "1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.\n" +
                "\n" +
                "2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.", "https://github.com/bumptech/glide"))
        licenseItemList.add(LicenseItem("Retrofit2", "Copyright 2013 Square, Inc.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software " +
                "distributed under the License is distributed on an \"AS IS\" BASIS, " +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and " +
                "limitations under the License.\n", "http://square.github.io/retrofit/"))
        licenseItemList.add(LicenseItem("Realm", "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software " +
                "distributed under the License is distributed on an \"AS IS\" BASIS, " +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and " +
                "limitations under the License.", "https://github.com/realm/realm-java/blob/master/LICENSE\n"))
        licenseItemList.add(LicenseItem("ExpandableLayout", "Copyright (C) 2015 A.Akira\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software " +
                "distributed under the License is distributed on an \"AS IS\" BASIS, " +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and " +
                "limitations under the License.", "https://github.com/AAkira/ExpandableLayout"))
        licenseItemList.add(LicenseItem("TedPermission", "Copyright 2017 Ted Park\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software " +
                "distributed under the License is distributed on an \"AS IS\" BASIS, " +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and " +
                "limitations under the License.\n", "https://github.com/ParkSangGwon/TedPermission"))
        licenseItemList.add(LicenseItem("Medal image", "Attribution 3.0 Unported (CC BY 3.0)", "https://www.flaticon.com/authors/smashicons"))
        licenseItemList.add(LicenseItem("Server Error image", "GNU GENERAL PUBLIC LICENSE\n" +
                "Version 3, 29 June 2007\n" +
                "\n" +
                "Copyright © 2007 Free Software Foundation, Inc. <https://fsf.org/>\n" +
                "\n" +
                "Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.", "https://www.iconfinder.com/icons/1055095"))
        return licenseItemList
    }

}
