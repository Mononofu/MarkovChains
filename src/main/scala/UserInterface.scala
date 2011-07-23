package userinterface

/**
 * Created by IntelliJ IDEA.
 * User: mononofu
 * Date: 7/23/11
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */

import markovchains._

object UserInterface extends App {

  //
  //println("please enter text to train with")
  //

  while (true) {
    println("1) train a new file")
    println("2) generate some words")
    println("3) exit")
    println("4) debug infos")

    readInt() match {
      case 1 =>
        println("please enter the filename")
        val filename = readLine()

        val trainer = new Trainer()
        trainer.learnFile(filename, 5, 12)
        trainer.save("test.data")
      case 2 =>
        val generator = new Generator()
        generator.load("test.data")
        for (i <- 1 to 20) println(generator.generateWord(5, 12))
      case 3 =>
        sys.exit()
      case 4 =>
        println()
        val generator = new Generator()
        generator.load("test.data")
        for( (prevs, probs) <- generator.probs.toList.sortWith( (a, b) => (a._1._1*256 + a._1._2) < (b._1._1*256 + b._1._2))) {
          print(prevs)
          println(":")
          probs.foreach(v => println("   " + v))
        }
      case _ =>
    }

  }

}