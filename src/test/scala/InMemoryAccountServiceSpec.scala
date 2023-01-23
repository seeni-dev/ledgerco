import models.LoanAccount
import org.scalatest.flatspec.AnyFlatSpec
import services._

class InMemoryAccountServiceSpec extends AnyFlatSpec {
  val bankName = "ABC"
  val borrowerName = "DEF"
  val principal = 10000
  val noOfYears = 5
  val interest   = 4
  val service: InMemoryLoanAccountService = new InMemoryLoanAccountService()

  behavior of "createLoanAccount and getLoanAccount "
  it should "create account and get should return correct details" in {
    service.createLoanAccount(
      bankName,
      borrowerName,
      principal,
      noOfYears,
      interest
    )
    val account: LoanAccount = service.getLoanAccount(bankName, borrowerName)

    assert(account.bankName === bankName)
    assert(account.borrowerName === borrowerName)
    assert(account.principal === principal)
    assert(account.noOfYears === noOfYears)
    assert(account.interest === interest)
  }


  behavior of "lumpSumPayment and getTotalPaid"

  it should "return 0 if no payments were done" in {
    val account: LoanAccount = service.getLoanAccount(bankName, borrowerName)
    assert(service.getTotalLumpSumsPaid(account, 10) === 0)
  }

  it should "register lump sum payment and payment history should affect that" in {
    service.lumpSumPayment(bankName, borrowerName, amount = 1000, 1)
    service.lumpSumPayment(bankName, borrowerName, amount = 1000, 11)
    val account: LoanAccount = service.getLoanAccount(bankName, borrowerName)

    assert(service.getTotalLumpSumsPaid(account, 1) === 1000)
    assert(service.getTotalLumpSumsPaid(account, 10) === 1000)

    assert(service.getTotalPaid(account, 3 ) === 1000+3*200) // 3 months EMI and 1000 lump sum payment made after 1st EMI
    assert(service.getTotalPaid(account, 10 ) === 1000+10*200) // 10 months EMI and 1000 lump sum payment made after 1st EMI
    assert(service.getTotalPaid(account, 11 ) === 1000+1000+11*200) // 11 months EMI and 2 lump sum payments
  }

  behavior of "getBalance"
  it should "getBalance" in {
    /*
     TOTAL_AMOUNT_LOAN               =  12000

     11 EMIs have been paid = 11*200 =   2200
     2 lumpsum payment               =   2000
       ------------------------------------
      REMAINING_AMOUNT               =   7800
      EMI_AMOUNT                     =    200
      NO OF EMI TO BE PAID =7800/200 =     39
     */
    assert(service.getBalance(bankName, borrowerName, 11 ) === (4200,39))
  }

}