package com.formationandroid.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MainActivity : AppCompatActivity()
{

	// Broadcast receiver :
	private lateinit var lecteurBroadcastReceiver: LecteurBroadcastReceiver

	// Vues :
	private lateinit var boutonPause: ImageButton


	override fun onCreate(savedInstanceState: Bundle?)
	{
		// init :
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// vues :
		boutonPause = findViewById(R.id.pause)

		// broadcast receiver :
		lecteurBroadcastReceiver = LecteurBroadcastReceiver(this)
	}

	override fun onStart()
	{
		super.onStart()
		registerReceiver(lecteurBroadcastReceiver, IntentFilter(LecteurBroadcastReceiver.INTENT_FILTER))
	}

	/**
	 * Listener clic play.
	 */
	fun clicPlay(view: View)
	{
		// lancement commande play :
		val intent = Intent(this, LecteurService::class.java)
		intent.putExtra(LecteurService.EXTRA_COMMANDE, LecteurService.COMMANDE_PLAY)
		startService(intent)

		// affichage bouton pause :
		boutonPause.visibility = View.VISIBLE
	}

	/**
	 * Listener clic pause.
	 */
	fun clicPause(view: View)
	{
		// lancement commande pause :
		val intent = Intent(this, LecteurService::class.java)
		intent.putExtra(LecteurService.EXTRA_COMMANDE, LecteurService.COMMANDE_PAUSE)
		startService(intent)
	}

	override fun onStop()
	{
		super.onStop()
		unregisterReceiver(lecteurBroadcastReceiver)
	}


	// Classe broadcast receiver :
	class LecteurBroadcastReceiver(private val mainActivity: MainActivity) : BroadcastReceiver()
	{
		companion object
		{
			const val INTENT_FILTER = "com.exemple.LECTEUR"
		}

		override fun onReceive(context: Context?, intent: Intent?)
		{
			// signale que la fin du titre a été atteinte :
			mainActivity.boutonPause.visibility = View.INVISIBLE
		}
	}

}