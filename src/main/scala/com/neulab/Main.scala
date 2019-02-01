/**
  *
  */
package com.neulab;

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.stats.distributions.{Dirichlet, Gaussian}
import com.neulab.lda.LDA

object Main extends App {
  println("Hello World!")

//  var gau = new Gaussian(1.0, 2.0)
//
//  for (i <- 1 to 100) {
//    println(gau.draw())
//  }


  val W = DenseVector(
    0,1,2,3,4
  )
  val X = DenseMatrix(
    (0,0,1,2,2),
    (0,0,1,1,1),
    (0,1,2,2,2),
    (4,4,4,4,4),
    (3,3,4,4,4),
    (3,4,4,4,4)
  )
  val nDocuments = X.rows
  val nVocab = W.length
  val nTopic = 2


  var ldaModel = new  LDA(1, 1, nTopic, nDocuments, nVocab)
  ldaModel.init()
  ldaModel.train(X)
  println(ldaModel.documentTopicDistribution)
}
