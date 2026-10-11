package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.CandidateAnalysis
import com.example.data.local.CandidateRepository
import com.example.data.model.Candidate
import com.example.data.model.ChecklistEntry
import com.example.data.model.ChecklistRating
import com.example.data.model.JournalEntry
import com.example.data.model.ThirtyDayProgress
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BakalSuamiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CandidateRepository(AppDatabase.getInstance(application))

    val candidates: StateFlow<List<Candidate>> = repository.getAllCandidates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCandidateId = MutableStateFlow<Long?>(null)
    val selectedCandidateId: StateFlow<Long?> = _selectedCandidateId.asStateFlow()

    init {
        viewModelScope.launch {
            candidates.collect { list ->
                if (list.isEmpty()) {
                    repository.seedInitialSampleCandidateIfEmpty()
                } else if (_selectedCandidateId.value == null) {
                    _selectedCandidateId.value = list.first().id
                }
            }
        }
    }

    val selectedCandidate: StateFlow<Candidate?> = _selectedCandidateId.flatMapLatest { id ->
        if (id == null) flowOf(null) else repository.getCandidateById(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val checklistEntries: StateFlow<List<ChecklistEntry>> = _selectedCandidateId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getChecklistEntries(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val journals: StateFlow<List<JournalEntry>> = _selectedCandidateId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getJournals(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val thirtyDayProgress: StateFlow<List<ThirtyDayProgress>> = _selectedCandidateId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getThirtyDayProgress(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val candidateAnalysis: StateFlow<CandidateAnalysis?> = combine(
        _selectedCandidateId,
        checklistEntries
    ) { id, entries ->
        if (id == null) null else CandidateRepository.calculateAnalysis(id, entries)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun selectCandidate(id: Long) {
        _selectedCandidateId.value = id
    }

    fun addCandidate(
        name: String,
        age: Int,
        occupation: String,
        maritalStatus: String,
        durationKnown: String,
        bio: String
    ) {
        viewModelScope.launch {
            val id = repository.insertCandidate(
                Candidate(
                    name = name,
                    age = age,
                    occupation = occupation,
                    maritalStatus = maritalStatus,
                    durationKnown = durationKnown,
                    bio = bio
                )
            )
            _selectedCandidateId.value = id
        }
    }

    fun updateCandidate(candidate: Candidate) {
        viewModelScope.launch {
            repository.updateCandidate(candidate)
        }
    }

    fun deleteCandidate(candidate: Candidate) {
        viewModelScope.launch {
            repository.deleteCandidate(candidate)
            val currentList = candidates.value.filter { it.id != candidate.id }
            _selectedCandidateId.value = currentList.firstOrNull()?.id
        }
    }

    fun setRating(criterionId: String, rating: ChecklistRating, note: String = "") {
        val candId = _selectedCandidateId.value ?: return
        viewModelScope.launch {
            repository.updateChecklistRating(candId, criterionId, rating, note)
        }
    }

    fun addJournal(
        dateString: String,
        title: String,
        observedAction: String,
        wordsSaid: String,
        isConsistent: Boolean,
        rating: ChecklistRating
    ) {
        val candId = _selectedCandidateId.value ?: return
        viewModelScope.launch {
            repository.addJournal(
                JournalEntry(
                    candidateId = candId,
                    dateString = dateString,
                    title = title,
                    observedAction = observedAction,
                    wordsSaid = wordsSaid,
                    isConsistent = isConsistent,
                    rating = rating
                )
            )
        }
    }

    fun deleteJournal(entry: JournalEntry) {
        viewModelScope.launch {
            repository.deleteJournal(entry)
        }
    }

    fun toggleDay(dayNumber: Int, isCompleted: Boolean, note: String = "") {
        val candId = _selectedCandidateId.value ?: return
        viewModelScope.launch {
            repository.toggleDayProgress(candId, dayNumber, isCompleted, note)
        }
    }
}
