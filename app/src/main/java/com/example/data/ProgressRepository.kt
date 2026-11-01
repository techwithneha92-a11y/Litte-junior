package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean,
    val progress: Float, // 0.0 to 1.0
    val progressText: String
)

data class UserProgress(
    val totalStars: Int = 0,
    val completedLetters: Set<Char> = emptySet(),
    val completedNumbers: Set<Int> = emptySet(),
    val completedMathsCount: Int = 0,
    val completedGkCategories: Set<String> = emptySet(),
    val totalQuizzesPassed: Int = 0,
    val dailyChallengeCompleted: Boolean = false,
    val dailyProgressCount: Int = 0,
    val estimatedMinutesSpent: Int = 12
)

class ProgressRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("little_genius_prefs", Context.MODE_PRIVATE)

    private val _progressState = MutableStateFlow(loadProgress())
    val progressState: StateFlow<UserProgress> = _progressState.asStateFlow()

    private fun loadProgress(): UserProgress {
        val stars = prefs.getInt("total_stars", 15) // start with 15 welcoming starter stars
        val letters = prefs.getStringSet("completed_letters", emptySet())
            ?.mapNotNull { it.firstOrNull() }?.toSet() ?: emptySet()
        val numbers = prefs.getStringSet("completed_numbers", emptySet())
            ?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        val maths = prefs.getInt("completed_maths", 0)
        val gk = prefs.getStringSet("completed_gk", emptySet()) ?: emptySet()
        val quizzes = prefs.getInt("quizzes_passed", 0)
        val dailyCompleted = prefs.getBoolean("daily_completed", false)
        val dailyCount = prefs.getInt("daily_count", 1)
        val minutes = prefs.getInt("minutes_spent", 15)

        return UserProgress(
            totalStars = stars,
            completedLetters = letters,
            completedNumbers = numbers,
            completedMathsCount = maths,
            completedGkCategories = gk,
            totalQuizzesPassed = quizzes,
            dailyChallengeCompleted = dailyCompleted,
            dailyProgressCount = dailyCount,
            estimatedMinutesSpent = minutes
        )
    }

    private fun saveProgress(progress: UserProgress) {
        prefs.edit()
            .putInt("total_stars", progress.totalStars)
            .putStringSet("completed_letters", progress.completedLetters.map { it.toString() }.toSet())
            .putStringSet("completed_numbers", progress.completedNumbers.map { it.toString() }.toSet())
            .putInt("completed_maths", progress.completedMathsCount)
            .putStringSet("completed_gk", progress.completedGkCategories)
            .putInt("quizzes_passed", progress.totalQuizzesPassed)
            .putBoolean("daily_completed", progress.dailyChallengeCompleted)
            .putInt("daily_count", progress.dailyProgressCount)
            .putInt("minutes_spent", progress.estimatedMinutesSpent)
            .apply()

        _progressState.value = progress
    }

    fun addStars(count: Int) {
        val current = _progressState.value
        val newStars = current.totalStars + count
        val newDailyCount = current.dailyProgressCount + 1
        val dailyDone = current.dailyChallengeCompleted || newDailyCount >= 3
        saveProgress(
            current.copy(
                totalStars = newStars,
                dailyProgressCount = newDailyCount,
                dailyChallengeCompleted = dailyDone,
                estimatedMinutesSpent = current.estimatedMinutesSpent + 1
            )
        )
    }

    fun markLetterCompleted(letter: Char) {
        val current = _progressState.value
        if (!current.completedLetters.contains(letter)) {
            val updated = current.completedLetters + letter
            addStars(2)
            saveProgress(_progressState.value.copy(completedLetters = updated))
        }
    }

    fun markNumberCompleted(num: Int) {
        val current = _progressState.value
        if (!current.completedNumbers.contains(num)) {
            val updated = current.completedNumbers + num
            addStars(2)
            saveProgress(_progressState.value.copy(completedNumbers = updated))
        }
    }

    fun recordMathSolved() {
        val current = _progressState.value
        val count = current.completedMathsCount + 1
        addStars(3)
        saveProgress(_progressState.value.copy(completedMathsCount = count))
    }

    fun markGkCategoryCompleted(categoryId: String) {
        val current = _progressState.value
        if (!current.completedGkCategories.contains(categoryId)) {
            val updated = current.completedGkCategories + categoryId
            addStars(5)
            saveProgress(_progressState.value.copy(completedGkCategories = updated))
        }
    }

    fun recordQuizPassed(starsAwarded: Int = 5) {
        val current = _progressState.value
        addStars(starsAwarded)
        saveProgress(_progressState.value.copy(totalQuizzesPassed = current.totalQuizzesPassed + 1))
    }

    fun getBadges(progress: UserProgress): List<AchievementBadge> {
        val letterCount = progress.completedLetters.size
        val numberCount = progress.completedNumbers.size
        val mathCount = progress.completedMathsCount
        val gkCount = progress.completedGkCategories.size

        return listOf(
            AchievementBadge(
                id = "abc_champion",
                title = "ABC Champion",
                description = "Learn letters from A to Z",
                iconEmoji = "🔤",
                isUnlocked = letterCount >= 5,
                progress = (letterCount / 26f).coerceIn(0f, 1f),
                progressText = "$letterCount / 26 Letters"
            ),
            AchievementBadge(
                id = "number_master",
                title = "Number Master",
                description = "Count numbers 1 to 20",
                iconEmoji = "🔢",
                isUnlocked = numberCount >= 5,
                progress = (numberCount / 20f).coerceIn(0f, 1f),
                progressText = "$numberCount / 20 Numbers"
            ),
            AchievementBadge(
                id = "maths_star",
                title = "Maths Star",
                description = "Solve addition and subtraction",
                iconEmoji = "➕",
                isUnlocked = mathCount >= 3,
                progress = (mathCount / 10f).coerceIn(0f, 1f),
                progressText = "$mathCount Solved"
            ),
            AchievementBadge(
                id = "gk_genius",
                title = "GK Genius",
                description = "Explore General Knowledge categories",
                iconEmoji = "🌍",
                isUnlocked = gkCount >= 2,
                progress = (gkCount / 10f).coerceIn(0f, 1f),
                progressText = "$gkCount / 10 Categories"
            ),
            AchievementBadge(
                id = "super_learner",
                title = "Super Learner",
                description = "Collect 50 or more shining stars",
                iconEmoji = "🌟",
                isUnlocked = progress.totalStars >= 50,
                progress = (progress.totalStars / 50f).coerceIn(0f, 1f),
                progressText = "${progress.totalStars} / 50 Stars"
            ),
            AchievementBadge(
                id = "quiz_champion",
                title = "Quiz Champion",
                description = "Pass 5 learning quizzes with flying colors",
                iconEmoji = "🏆",
                isUnlocked = progress.totalQuizzesPassed >= 5,
                progress = (progress.totalQuizzesPassed / 5f).coerceIn(0f, 1f),
                progressText = "${progress.totalQuizzesPassed} / 5 Quizzes"
            )
        )
    }

    fun resetAllProgress() {
        prefs.edit().clear().apply()
        _progressState.value = UserProgress()
    }
}
