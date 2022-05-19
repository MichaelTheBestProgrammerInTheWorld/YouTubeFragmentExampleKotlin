package com.example.testprojects

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import java.util.*


class YoutubeTestFragment : Fragment() {

    //youtube player fragment
    private lateinit var youTubePlayerFragment: YouTubePlayerSupportFragmentX
    private var youtubeVideoArrayList: ArrayList<String>? = null

    //youtube player to play video when new video selected
    private var youTubePlayer: YouTubePlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_youtube_test, container, false)


        generateDummyVideoList()
        initializeYoutubePlayer()


        return view
    }

    /**
     * method to generate dummy array list of videos
     */
    private fun generateDummyVideoList() {
        youtubeVideoArrayList = ArrayList()

        //get the video id array from strings.xml
        val videoIDArray = resources.getStringArray(R.array.video_id_array)

        //add all videos to array list
        for (vidId in videoIDArray){
            youtubeVideoArrayList?.add(vidId)
        }
    }

    /**
     * initialize youtube player via Fragment and get instance of YoutubePlayer
     */
    private fun initializeYoutubePlayer() {
        //youTubePlayerFragment = activity?.getSupportFragmentManager()?.findFragmentById(R.id.youtube_player_fragment)  as YouTubePlayerSupportFragmentX
        youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.youtube_player_fragment, youTubePlayerFragment).commit()
        if (youTubePlayerFragment == null) return
        youTubePlayerFragment?.initialize(
            "AIzaSyAISkugfeK9PYfn-RSraAp3lBI0y3V_OOY",
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider, player: YouTubePlayer,
                    wasRestored: Boolean
                ) {
                    if (!wasRestored) {
                        youTubePlayer = player


                        //set the player style default
                        youTubePlayer?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

                        //cue the 1st video by default
                        youTubePlayer?.cueVideo(youtubeVideoArrayList!![0])
                    }
                }

                override fun onInitializationFailure(
                    arg0: YouTubePlayer.Provider,
                    arg1: YouTubeInitializationResult
                ) {

                    //print or show error if initialization failed
                    Log.e("YouTube_Err", "Youtube Player View initialization failed")
                }
            })
    }

}