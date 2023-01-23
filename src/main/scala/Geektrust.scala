import models.LoanAccount
import services.{InMemoryLoanAccountService, LoanAccountService}

import scala.io.Source

object Geektrust extends App{
  val loanAccountService: LoanAccountService = new InMemoryLoanAccountService()

  def executeCmd(cmd: String)= {
    val splits = cmd.split(" ")
    splits(0) match {
      case "LOAN" => loanAccountService.createLoanAccount(
        splits(1),
        splits(2),
        splits(3).toInt,
        splits(4).toInt,
        splits(5).toInt
      )
      case "PAYMENT" => loanAccountService.lumpSumPayment(
        splits(1),
        splits(2),
        splits(3).toInt,
        splits(4).toInt
      )
      case "BALANCE" => {
        val result = loanAccountService.getBalance(
          splits(1),
          splits(2),
          splits(3).toInt
        )
        val output = Seq(splits(1), splits(2), result._1.toString, result._2.toString)
        println(output.mkString(" "))
      }
    }
  }

  val inputFile = args(0)
  for (line <- Source.fromFile(inputFile).getLines) {
    executeCmd(line)
  }

}