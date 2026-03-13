package com.cardforge.app.spacedrepetition

import com.cardforge.app.database.entity.ReviewEntity
import kotlin.math.roundToInt

object SM2 {

    fun review(
        previousInterval: Int,
        previousEase: Float,
        quality: Int
    ): Pair<Int, Float> {

        var ease = previousEase
        var interval = previousInterval

        if (quality < 3) {

            interval = 1

        } else {

            interval = (interval * ease).roundToInt()

            ease = ease + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f))

            if (ease < 1.3f) {
                ease = 1.3f
            }
        }

        return Pair(interval, ease)
    }
}