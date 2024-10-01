package me.dgahn.domain.model

enum class AgreeStatus(
    val agreedType: Boolean,
) {
    AGREE(true),
    DIS_AGREE(false),
    ;

    companion object {
        fun of(agreedType: Boolean): AgreeStatus {
            return if (agreedType) AGREE else DIS_AGREE
        }
    }
}
