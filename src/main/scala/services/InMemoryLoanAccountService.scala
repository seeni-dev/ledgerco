package services

import models.{LoanAccount, LumpSumPayment}

import scala.collection.mutable

class InMemoryLoanAccountService extends LoanAccountService {

  // Below arrays will serve as DB and store the loan accounts and payments
  private val loanAccounts: mutable.Map[(String, String), LoanAccount] = mutable.Map[(String, String), LoanAccount]()
  private val lumpSumPayments: mutable.Map[(String, String), mutable.Seq[LumpSumPayment]] = mutable.Map[(String, String), mutable.Seq[LumpSumPayment]]()

  private def getKey(bankName: String, borrowerName: String): (String, String) = (bankName, borrowerName)
  private def getKey(loanAccount: LoanAccount): (String, String) = getKey(loanAccount.bankName, loanAccount.borrowerName)

  override def createLoanAccount(bankName: String, borrowerName: String, principal: Int, noOfYears: Int, interest: Int): Unit = {
    val key = getKey(bankName, borrowerName)
    assert(!loanAccounts.contains(key), "Account already exists")
    loanAccounts.put(key, LoanAccount(bankName, borrowerName, principal, noOfYears, interest))
  }

  override def getLoanAccount(bankName: String, borrowerName: String): LoanAccount = {
    val key = getKey(bankName, borrowerName)
    assert(loanAccounts.contains(key), "Account doesn't exist")
    loanAccounts(key)
  }


  private def getLumpSumpPayments(loanAccount: LoanAccount): mutable.Seq[LumpSumPayment] = {
    val key = getKey(loanAccount)
    lumpSumPayments.getOrElse(key, mutable.ListBuffer[LumpSumPayment]())
  }


  override def lumpSumPayment(bankName: String, borrowerName: String, amount: Int, emiNo: Int): Unit = {
    val loanAccount = getLoanAccount(bankName, borrowerName)
    val key = getKey(loanAccount)
    val payment = LumpSumPayment(bankName, borrowerName, amount, emiNo)
    val payments = getLumpSumpPayments(loanAccount)
    lumpSumPayments.put(key, payments :+ payment)
  }

  /**
   * get total lump sum paid until an emi number
   * @param loanAccount
   * @param untilEMINo
   * @return
   */
  def getTotalLumpSumsPaid(loanAccount: LoanAccount, untilEMINo: Int): Int = {
    getLumpSumpPayments(loanAccount)
      .filter(_.emiNo <= untilEMINo)
      .map(_.amount)
      .sum
  }

  /**
   * get total sum paid until an emi number
   * @param loanAccount
   * @param untilEMINo
   * @return
   */
  override def getTotalPaid(loanAccount: LoanAccount, untilEMINo: Int): Int = {
    getTotalLumpSumsPaid(loanAccount, untilEMINo) + loanAccount.getTotalEMIPaymentsPaid(untilEMINo)
  }

  override def getBalance(bankName: String, borrowerName: String, emiNo: Int): (Int, Int) = {
    val loanAccount = getLoanAccount(bankName, borrowerName)
    val totalPaid = getTotalPaid(loanAccount, emiNo)
    (totalPaid, loanAccount.getRemainingEMICount(totalPaid))
  }

}


