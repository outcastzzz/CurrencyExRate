
package com.register.currencyexrate.presentation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.register.currencyexrate.R
import com.register.currencyexrate.databinding.FragmentSplashBinding
import com.register.currencyexrate.domain.entities.CurrencyInfo
import com.register.currencyexrate.presentation.adapters.CurrencyListAdapter
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SplashFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CurrencyApp).component
    }

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("FragmentSplashBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        viewModel.getCurrency()
        viewModel.currencyListDate.observe(viewLifecycleOwner) { data ->
            Log.d("CurrencyFragment", "currencyList has been observed with data: $data")
            if(data == null) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Нет данных",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("CurrencyFragment", "Showed Toast: Нет данных")
            } else {
                Log.d("CurrencyFragment", "Launching CurrencyListFragment")
                launchCurrencyListFragment()
            }
        }
        animateLogo()
    }

    private fun animateLogo() {
        val scaleX = ObjectAnimator.ofFloat(binding.textView, "scaleX", 1f, 1.2f, 1f)
        scaleX.repeatCount = Animation.INFINITE
        val scaleY = ObjectAnimator.ofFloat(binding.textView, "scaleY", 1f, 1.2f, 1f)
        scaleY.repeatCount = Animation.INFINITE
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleY, scaleX)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    private fun launchCurrencyListFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_listOfCurrencyFragment)
    }

}