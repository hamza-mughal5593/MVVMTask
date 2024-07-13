package com.hamza.test.data.model.data

data class MedicalData(
    val problems: HashMap<String, List<Any>>
)

data class Problem(
    val medications: Medication? = null,
    val labs: List<Map<String, String>>? = null
)
data class Medication(
    val medicationsClasses: List<MedicationsClass>
)

data class MedicationsClass(
    val classNames: Map<String, List<DrugClass>> // Using Map for dynamic class names
)

data class DrugClass(
    val associatedDrugs: Map<String, List<Drug>> // Using Map for dynamic associated drugs
)

data class Drug(
    val name: String,
    val dose: String? = null,
    val strength: String
)
