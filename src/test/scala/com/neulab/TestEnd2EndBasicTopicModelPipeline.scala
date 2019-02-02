package com.neulab

import breeze.linalg.{DenseMatrix, DenseVector, argmax}
import com.neulab.lda.LDA
import org.scalatest.{FlatSpec, Matchers}

class TestEnd2EndBasicTopicModelPipeline extends FlatSpec with Matchers{

  it should "Test End2End Pipeline" in {
    val W = DenseVector(
      0,1,2,3,4
    )
    val X = DenseMatrix(
      (0,0,1,2,2,1,2,2),
      (0,0,1,1,1,1,1,1),
      (0,1,2,2,2,2,2,2),
      (4,4,4,4,4,4,4,4),
      (3,3,4,4,4,3,4,3),
      (3,4,4,4,4,4,4,4)
    )
    val nDocuments = X.rows
    val nVocab = W.length
    val nTopic = 2

    var ldaModel = new  LDA(1, 1, nTopic, nDocuments, nVocab)
    ldaModel.init()
    ldaModel.train(X)
    // the first-3 sample must shared the same topic
    // as the last-3 share the other.
    val topicResult = ldaModel.documentTopicDistribution(breeze.linalg.*,::).map(
      topicVector => {
        argmax(topicVector.toArray)

      }
    )
    println(topicResult)
    assert(topicResult(0) == topicResult(1))
    assert(topicResult(1) == topicResult(2))
    assert(topicResult(2) != topicResult(3))
    assert(topicResult(3) == topicResult(4))
    assert(topicResult(4) == topicResult(5))
  }

}
