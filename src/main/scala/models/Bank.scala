package models

import scala.collection.mutable

class Bank(val name: String){
  val accounts: mutable.Seq[LoanAccount] = mutable.ListBuffer[LoanAccount]()

  def getAccounts(): Seq[LoanAccount] = accounts.toSeq
}
