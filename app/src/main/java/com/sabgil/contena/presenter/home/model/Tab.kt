package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.R
import com.sabgil.contena.presenter.home.fragment.BookmarkTabFragment
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.fragment.SettingsTabFragment
import com.sabgil.contena.presenter.widget.BottomNavigationBar

enum class Tab : BottomNavigationBar.Tab {
    MAIN {
        override val icon get() = R.drawable.bottom_nav_ic_main
        override val selectedColor get() = R.color.colorBeigeWhite
        override val unselectedColor get() = R.color.colorDarkGray
        override val fragmentClazz get() = NewItemTabFragment::class.java

    },
    ADD {
        override val icon get() = R.drawable.bottom_nav_ic_add
        override val selectedColor get() = R.color.colorBeigeWhite
        override val unselectedColor get() = R.color.colorDarkGray
        override val fragmentClazz get() = SearchTabFragment::class.java
    },
    BOOKMARK {
        override val icon get() = R.drawable.bottom_nav_ic_bookmark
        override val selectedColor get() = R.color.colorBeigeWhite
        override val unselectedColor get() = R.color.colorDarkGray
        override val fragmentClazz get() = BookmarkTabFragment::class.java
    },
    SETTINGS {
        override val icon get() = R.drawable.bottom_nav_ic_settings
        override val selectedColor get() = R.color.colorBeigeWhite
        override val unselectedColor get() = R.color.colorDarkGray
        override val fragmentClazz get() = SettingsTabFragment::class.java
    };
}
