package models


case class LumpSumPayment(bankName: String,
                          borrowerName: String,
                          amount: Int,
                          emiNo: Int)