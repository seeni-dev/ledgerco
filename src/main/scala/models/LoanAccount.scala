package models

case class LoanAccount(bankName: String,
                       borrowerName: String,
                       principal: Int,
                       noOfYears: Int,
                       interest: Int){
  assert(interest <= 100)

  def getTotalAmount(): Int = principal + (principal * noOfYears * interest.toFloat/100).ceil.toInt

  def getNumberOfMonths(): Int = 12 * noOfYears

  def getEMI(): Int = (getTotalAmount().toFloat/getNumberOfMonths()).ceil.toInt


  def getRemaining(paid: Int): Int = getTotalAmount() - paid

  def getRemainingEMICount(paid: Int): Int = {
    val remaining = getRemaining(paid)
    (remaining.toFloat/getEMI()).ceil.toInt
  }

  def getTotalEMIPaymentsPaid(emiNo: Int): Int = {
    emiNo * getEMI()
  }

}
