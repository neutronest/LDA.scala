package com.neulab
import org.scalatest._

class TestHelloWorld extends FlatSpec with Matchers {

  it should "Test basic HelloWorld" in {
    val db = "HelloWorld"
    assert(db == "HelloWorld")
  }
}