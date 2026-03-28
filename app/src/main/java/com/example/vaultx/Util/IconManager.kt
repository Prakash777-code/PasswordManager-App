package com.example.vaultx.Util

import com.example.vaultx.R

class IconManager {


    fun setIcon(title:String): Int{

        val name = title.lowercase()

        return when{

            name.contains("google") -> R.drawable.icon_google
            name.contains("youtube") -> R.drawable.icon_youtube
            name.contains("spotify") -> R.drawable.icon_spotify
            name.contains("reddit") ->R.drawable.icon_reddit
            name.contains("snapchat") -> R.drawable.icon_snapchat
            name.contains("pinterest") -> R.drawable.icon_pinterest
            name.contains("linkedin") -> R.drawable.icon_linkeden
            name.contains("github") -> R.drawable.icon_github
            name.contains("netflix") -> R.drawable.icon_netflix
            name.contains("twitter") || name.contains("x") -> R.drawable.icon_twitter
            name.contains("amazon") -> R.drawable.icon_amazon
            name.contains("flipkart") -> R.drawable.icon_flipkart
            name.contains("facebook") -> R.drawable.icon_facebook
            name.contains("gmail") -> R.drawable.icon_gmail
            name.contains("instagram") -> R.drawable.icon_instagram
            name.contains("wifi") -> R.drawable.icon_wifi
            name.contains("api") -> R.drawable.icon_api

            else -> R.drawable.icon_default
        }


    }
}