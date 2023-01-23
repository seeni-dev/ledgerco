import models.LoanAccount
import org.scalatest.flatspec.AnyFlatSpec

class LoanAccountSpec extends AnyFlatSpec {
  val loanAccount = LoanAccount(
    "ABC",
    "DEF",
    10000,
    5,
    4
  )
  behavior of "getTotalAmount"
  it should "return correct total amount" in {
    assert(loanAccount.getTotalAmount() === 12000)
  }

  behavior of "getNumberOfMonths"
  it should "return correct getNumberOfMonths" in {
    assert(loanAccount.getNumberOfMonths() === 60)
  }

  behavior of "getEMI"
  it should "return correct emi amount" in {
    assert(loanAccount.getEMI() === 200)
  }

  behavior of "getRemainingEMICount"
  it should "return correct remaining EMI count" in {
    assert(loanAccount.getRemainingEMICount(2000) === 50)
  }

  behavior of "getTotalEMIPaymentsPaid"
  it should "return correct getTotalEMIPaymentsPaid" in {
    assert(loanAccount.getTotalEMIPaymentsPaid(10) === 2000)
  }
}
