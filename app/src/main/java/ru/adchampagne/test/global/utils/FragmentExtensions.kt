package ru.adchampagne.test.global.utils

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pro.appcraft.lib.utils.common.isApiAtLeast
import java.time.LocalDate
import java.time.ZoneId
import kotlin.reflect.KClass

inline fun <reified T : Fragment> FragmentManager.instantiateFragment(
    context: Context,
    fragmentClass: KClass<out T>,
    args: Bundle = bundleOf()
): T = fragmentFactory.instantiate(context.classLoader, fragmentClass.qualifiedName!!)
    .apply { arguments = args } as T

inline fun <reified T : DialogFragment> Fragment.showDialog(
    dialogClass: KClass<out T>,
    vararg args: Pair<String, Any?>
): DialogFragment {
    val fragment =
        childFragmentManager.instantiateFragment(requireContext(), dialogClass, bundleOf(*args))
    fragment.show(childFragmentManager, dialogClass.simpleName)
    return fragment
}

inline fun Fragment.buildDatePicker(
    currentDate: LocalDate,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    crossinline dateCallback: (LocalDate) -> Unit
): DatePickerDialog = DatePickerDialog(
    requireContext(),
    { _, year, month, dayOfMonth ->
        val newDate = LocalDate.of(year, month + 1, dayOfMonth)
        dateCallback(newDate)
    },
    currentDate.year,
    currentDate.monthValue - 1,
    currentDate.dayOfMonth
).apply {
    minDate?.let {
        datePicker.minDate = it.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
    maxDate?.let {
        datePicker.maxDate = it.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}

@TargetApi(Build.VERSION_CODES.N)
fun Fragment.appLocale() = if (isApiAtLeast(Build.VERSION_CODES.N))
    resources.configuration.locales[0]
else resources.configuration.locale