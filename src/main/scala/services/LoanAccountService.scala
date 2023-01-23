package services

import models._

trait LoanAccountService {

  def createLoanAccount(bankName: String,
                        borrowerName: String,
                        principal: Int,
                        noOfYears: Int,
                        interest: Int
            ): Unit

  def getLoanAccount(bankName: String,
                     borrowerName: String): LoanAccount


  def lumpSumPayment(bankName: String,
                      borrowerName: String,
                      amount: Int, emiNo: Int): Unit

  def getTotalPaid(loanAccount: LoanAccount,
                   emiNo: Int): Int

  def getBalance(bankName: String,
                 borrowerName: String,
                 emiNo: Int): (Int, Int)
}


