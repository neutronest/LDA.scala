/**
  * Copy Right
  */
package com.neulab.lda

import breeze.linalg.{DenseMatrix, DenseVector, sum}
import breeze.stats
import breeze.stats.distributions
import breeze.numerics.{exp, log, log1p}
import breeze.stats.distributions.{Dirichlet, Multinomial}

class LDA(
           var alpha: Double,
           var gamma: Double,
           var nTopic: Int,
           var nDoucments: Int,
           var nVocab: Int){

  var z = DenseMatrix.zeros[Int](nDoucments, nVocab)
  var documentTopicDistribution = DenseMatrix.zeros[Double](nDoucments, nTopic)
  var topicWordDistribution = DenseMatrix.zeros[Double](nTopic, nVocab)

  def init(): Unit = {

    val r = scala.util.Random

    z = z.map(x => r.nextInt(nTopic))


    documentTopicDistribution = documentTopicDistribution(breeze.linalg.*,::).map(
      row => new distributions.Dirichlet(alpha * DenseVector.ones[Double](nTopic)).draw()
    )

    topicWordDistribution = topicWordDistribution(breeze.linalg.*, ::).map(
      row => new distributions.Dirichlet(gamma * DenseVector.ones[Double](nVocab)).draw()
    )
  }

  def train(documentTokenDenseMatrix: DenseMatrix[Int], iterTimes: Int=1000) : Unit = {

    val iterStart = 1
    val idxStart = 0
    for (step <- iterStart to iterTimes) {

      for (documentIdx <- idxStart to nDoucments-1) {
        for (wordIdx <- idxStart to nVocab-1) {
//          println("documentIdx: ", documentIdx)
//          println("wordIdx: ", wordIdx)
//          println(log(documentTopicDistribution(documentIdx,::)))
//          println(log(topicWordDistribution(::, documentTokenDenseMatrix(documentIdx, wordIdx)).t))

          var piv = exp(log(documentTopicDistribution(documentIdx,::))
            + log(topicWordDistribution(::, documentTokenDenseMatrix(documentIdx, wordIdx)).t ))
          val pivNormalized = (piv / sum(piv)).t

          z(documentIdx, wordIdx) = new Multinomial[DenseVector[Double], Int](pivNormalized).draw()
        }
      }

      for (documentIdx <- 0 to nDoucments-1) {

        var updateTopic = DenseVector.zeros[Double](nTopic)

        for (topicIdx <- 0 to nTopic-1) {
          var f = (x: Int) => x == topicIdx
            updateTopic(topicIdx) = z(documentIdx,::).inner.findAll(f).size

          documentTopicDistribution(documentIdx, ::) := new distributions.Dirichlet(
            alpha * DenseVector.ones[Double](nTopic) + updateTopic).draw().t
        }
      }

      for (topicIdx <- 0 to nTopic-1) {
        var updateVocab = DenseVector.zeros[Double](nVocab)

        for (vocabIdx <- 0 to nVocab-1) {
          for (documentIdx <- 0 to nDoucments-1) {
            for (vocabDualIdx <- 0 to nVocab-1) {
              if (documentTokenDenseMatrix(documentIdx, vocabDualIdx) == vocabIdx &&
              z(documentIdx, vocabDualIdx) == topicIdx) {
                updateVocab(vocabIdx) += 1
              }
            }
          }
        }
        topicWordDistribution(topicIdx, ::) := new distributions.Dirichlet(
          gamma * DenseVector.ones[Double](nVocab) + updateVocab
        ).draw().t
      }


    }
  }


}
