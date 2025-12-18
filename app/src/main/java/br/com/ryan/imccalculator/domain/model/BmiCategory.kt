package br.com.ryan.imccalculator.domain.model

enum class BmiCategory(
    val label: String,
    val interpretation: String
) {
    UNDERWEIGHT("Abaixo do peso", "Atenção: pode indicar baixo peso. Avalie com um profissional."),
    NORMAL("Normal", "Faixa considerada saudável na maioria dos casos."),
    OVERWEIGHT("Sobrepeso", "Pode indicar excesso de peso. Ajustes de rotina podem ajudar."),
    OBESITY_I("Obesidade I", "Risco aumentado. Recomenda-se acompanhamento profissional."),
    OBESITY_II("Obesidade II", "Risco alto. Recomenda-se acompanhamento profissional."),
    OBESITY_III("Obesidade III", "Risco muito alto. Procure acompanhamento profissional.")
}
