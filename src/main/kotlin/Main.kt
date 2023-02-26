const val PERCENT_VISA_MIR = 0.75
const val MIN_COMMISSION_VISA_MIR = 3500
const val LIMIT_MASTER_MAESTRO = 75_000_00
const val MIN_COMMISSION = 0
const val PERCENT_MASTER_MAESTRO = 0.6
const val COMMISSION_MASTER_MAESTRO = 2_000

fun main() {
    val paymentSystem = choosePaymentSystem()
    print("Введите сумму перевода: ")
    val money = readln().toInt()
    val commission = pay(money, paymentSystem = paymentSystem)
    println("Ваша комиссия при переводе составит - $commission коп.")

}

enum class PaymentSystem {
    MasterCard, Maestro, Visa, VKPay, Mir
}

fun pay(money: Int, lastPay: Int = 0, paymentSystem: PaymentSystem = PaymentSystem.VKPay): Int {
    return when (paymentSystem) {
        PaymentSystem.MasterCard, PaymentSystem.Maestro -> {
            if (lastPay + money < LIMIT_MASTER_MAESTRO) MIN_COMMISSION
            else (money * PERCENT_MASTER_MAESTRO).toInt() + COMMISSION_MASTER_MAESTRO
        }
        PaymentSystem.VKPay -> {
            if (money < 15_000_00 || money + lastPay < 40_000_00) MIN_COMMISSION
            else {
                println("Вы превысили лимит по переводу денежных средств, воспользуйтесь другой платежной системой")
                MIN_COMMISSION
            }
        }
        PaymentSystem.Visa, PaymentSystem.Mir -> {
            if (money * PERCENT_VISA_MIR > MIN_COMMISSION_VISA_MIR) (money * PERCENT_VISA_MIR).toInt()
            else MIN_COMMISSION_VISA_MIR
        }
    }
}

fun choosePaymentSystem(): PaymentSystem {
    println("Выберите платежную систему согласно списка: ")
    println("1. MasterCard")
    println("2. Maestro")
    println("3. Visa")
    println("4. VK Pay")
    println("5. Мир")
    val paymentSystem: PaymentSystem = when (readln()) {
        "1" -> PaymentSystem.MasterCard
        "2" -> PaymentSystem.Maestro
        "3" -> PaymentSystem.Visa
        "4" -> PaymentSystem.VKPay
        "5" -> PaymentSystem.Mir
        else -> {
            println("Вы неверно указали платежную систему, в связи с чем будет установлена система по умолчанию - VK Pay")
            PaymentSystem.VKPay
        }
    }
    return paymentSystem
}