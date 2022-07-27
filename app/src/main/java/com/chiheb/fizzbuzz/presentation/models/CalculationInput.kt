package com.chiheb.fizzbuzz.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculationInput(
    val firstDivider: Int,
    val secondDivider: Int,
    val firstReplacementText: String,
    val secondReplacementText: String,
    val calculationLimit: Int
) : Parcelable
