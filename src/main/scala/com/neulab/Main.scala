package com.neulab;

import breeze.stats.distributions.{Dirichlet, Gaussian};
import com.neulab.lda.LDA

object Main extends App {
  println("Hello World!")

  var gau = new Gaussian(1.0, 2.0)

  for (i <- 1 to 100) {
    println(gau.draw())
  }

  var ldaModel = new  LDA(0.1, 0.1, 10, 10, 100)
  ldaModel.init()
}
