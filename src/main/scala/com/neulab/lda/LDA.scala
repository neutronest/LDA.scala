/**
  * Copy Right
  */
package com.neulab.lda

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.stats
import breeze.stats.distributions
import breeze.stats.distributions.Dirichlet

class LDA(
           var alpha: Double,
           var gamma: Double,
           var nTopic: Int,
           var nDoucments: Int,
           var nVocab: Int){

  var z = DenseMatrix.zeros[Double](nDoucments, nVocab)
  var documentTopicDistribution = DenseMatrix.zeros[Double](nDoucments, nTopic)
  var topicWordDistribution = DenseMatrix.zeros[Double](nTopic, nVocab)

  def init(): Unit = {

    val r = scala.util.Random

    z = z.map(x => r.nextInt(nTopic).toDouble)

    documentTopicDistribution = documentTopicDistribution(breeze.linalg.*,::).map(
      row => new distributions.Dirichlet(alpha * DenseVector.ones[Double](nTopic)).draw()
    )

    topicWordDistribution = topicWordDistribution(breeze.linalg.*, ::).map(
      row => new distributions.Dirichlet(gamma * DenseVector.ones[Double](nVocab)).draw()
    )
  }

  def train(documentTokenDenseMatrix: DenseMatrix[Double], iterTimes: Int=100) : Unit = {
    print(z)
  }


}
