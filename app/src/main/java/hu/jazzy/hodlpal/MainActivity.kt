package hu.jazzy.hodlpal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import hu.jazzy.hodlpal.databinding.ActivityMainBinding
import hu.jazzy.hodlpal.fragments.Coins
import hu.jazzy.hodlpal.fragments.Portfolio
import hu.jazzy.hodlpal.fragments.Wallet

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var portfolioFragment:Portfolio
    private lateinit var walletFragment: Wallet
    private lateinit var coinsFragment: Coins

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ///initFragments()
        //replaceFragment(coinsFragment)

//        binding.bottomNavbar.setOnNavigationItemSelectedListener {
//            handleBottomNavigation(
//                it.itemId
//            )
//        }
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController(binding.fragment.id)
        val navbar = binding.bottomNavbar
        navbar.setOnItemSelectedListener {
            (it.onNavDestinationSelected(navController))

        }
        navbar.setupWithNavController(navController)
    }


//    private fun handleBottomNavigation(
//        menuItemId: Int
//    ): Boolean = when(menuItemId) {
//        R.id.menu_wallet -> {
//            replaceFragment(walletFragment)
//            true
//        }
//        R.id.menu_coins -> {
//            replaceFragment(coinsFragment)
//            true
//        }
//        R.id.menu_portfolio -> {
//            replaceFragment(portfolioFragment)
//            true
//        }
//        else -> false
//    }
//
//    private fun replaceFragment(fragment: Fragment){
//        val tx = supportFragmentManager.beginTransaction()
//        tx.replace(binding.fragmentContainer.id,fragment)
//        tx.commit()
//    }

    private fun initFragments(){
        coinsFragment = Coins()
        walletFragment = Wallet()
        portfolioFragment = Portfolio()
    }
}