package models

object CountingModel {
  private var counter: Int = 0

  def getCounter() = synchronized { counter }
  def incrementCounter() = synchronized { counter += 1 }
}