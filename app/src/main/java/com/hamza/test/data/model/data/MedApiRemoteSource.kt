package com.hamza.test.data.model.data

import androidx.lifecycle.viewModelScope
import com.hamza.test.data.network.api.ApiService
import com.mycomposeproj.data.repository.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedApiRemoteSource @Inject constructor(private val medicineRepository: MedicineRepository)  {
    suspend fun getMedCategories(): ArrayList<Drug> = withContext(Dispatchers.IO) {
        val problemsResponse = medicineRepository.getMedCategories2().body()

        val drugsList = ArrayList<Drug>()

        problemsResponse?.problems?.forEach { problemMap ->
            problemMap.values.forEach { medicalInfoList ->
                val medicalInfos = medicalInfoList as? List<Map<String, Any>> ?: return@forEach
                medicalInfos.forEach { medicalInfo ->
                    val medications =
                        medicalInfo["medications"] as? List<Map<String, Any>> ?: return@forEach
                    medications.forEach { medication ->
                        val medicationsClasses =
                            medication["medicationsClasses"] as? List<Map<String, Any>>
                                ?: return@forEach
                        medicationsClasses.forEach { classMap ->
                            classMap.values.forEach { classList ->
                                val classNames =
                                    classList as? List<Map<String, Any>> ?: return@forEach
                                classNames.forEach { associatedDrugMap ->
                                    associatedDrugMap.values.forEach { associatedDrugList ->
                                        val drugs = associatedDrugList as? List<Map<String, Any>>
                                            ?: return@forEach
                                        drugs.forEach { drugMap ->
                                            val drug = Drug(
                                                name = drugMap["name"] as String,
                                                dose = drugMap["dose"] as String,
                                                strength = drugMap["strength"] as String
                                            )
                                            drugsList.add(drug)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Print or use the drugs list
        println(drugsList)




        return@withContext drugsList
    }


}