package com.register.currencyexrate.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.register.currencyexrate.R
import com.register.currencyexrate.databinding.FragmentCurrencyInfoBinding
import com.register.currencyexrate.databinding.FragmentListOfCurrencyBinding
import javax.inject.Inject

class CurrencyInfoFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CurrencyApp).component
    }

    private var _binding: FragmentCurrencyInfoBinding? = null
    private val binding: FragmentCurrencyInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentCurrencyInfoBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        viewModel.currentName.observe(viewLifecycleOwner) {
            binding.currencyInfoName.text = it
        }
        viewModel.currentCharCode.observe(viewLifecycleOwner) {
            binding.currencyInfoCharcode.text = it
            binding.textInputCharcode.hint = it
        }
        viewModel.currentCount.observe(viewLifecycleOwner) {
            binding.currencyInfoCount.text = it.toString()
        }
        binding.imageArrowBack.setOnClickListener {
            goBack()
        }
        binding.textEtCharcode.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не используется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
                if (inputText.isBlank()) {
                    binding.textEtRub.setText("0")
                } else if (inputText == "-") {
                    Toast.makeText(
                        requireContext(),
                        "Отрицательные числа недопустимы",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.textEtCharcode.setText("")
                } else if (!inputText.matches(Regex("-?\\d*"))) {
                    binding.textEtCharcode.setText(inputText.filter { it.isDigit() })
                } else {
                    val sum = inputText.toLong()
                    val rate = viewModel.currentCount.value
                    val modifiedText = (rate?.times(sum)).toString()
                    if (modifiedText.contains('.')) {
                        val splitText = modifiedText.split(".")
                        if (splitText.size == 2 && splitText[1].length > 3) {
                            val newText = "${splitText[0]}.${splitText[1].substring(0, 3)}"
                            binding.textEtRub.setText(newText)
                        }
                    } else {
                        binding.textEtRub.setText(modifiedText)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Не используется
            }
        })
    }

    private fun goBack() {
        findNavController().navigate(R.id.action_currencyInfoFragment_to_listOfCurrencyFragment)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}