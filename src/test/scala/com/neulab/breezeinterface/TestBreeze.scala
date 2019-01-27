/**
  *
  */
package com.neulab.breezeinterface

import breeze.linalg.DenseVector
import breeze.numerics.abs
import breeze.stats.distributions.{Dirichlet, Gaussian}
import org.scalatest.{FlatSpec, Matchers}
import com.neulab.utils.LDAConstants

class TestBreeze extends FlatSpec with Matchers {

  it should "Test Gaussian" in {

    val gaussianDistribution = new Gaussian(1.0, 2.0)
    var muAcc = 0.0
    val iterNum = 100000

    for (i <- 1 to iterNum) {
      muAcc += gaussianDistribution.draw()
    }
    val muAccAverage = muAcc / iterNum
    assert(abs(muAccAverage - 1.0) < LDAConstants.MIDDLE_EPS)
  }

  it should "Test Dirichlet" in {

    val nTopics = 10
    val dirichletMachine = new Dirichlet(0.1 * DenseVector.ones[Double](nTopics)).draw()
    assert(dirichletMachine != DenseVector.zeros[Double](nTopics))
    assert(dirichletMachine.data.length == nTopics)

  }

}
