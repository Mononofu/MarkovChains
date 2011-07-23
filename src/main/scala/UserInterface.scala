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

    (try { readInt() } catch { case ex => -1 }) match {
      case 1 =>

        val trainer = new Trainer()
        print("input file: ")
        trainer.learnFile(readLine(), 5, 12)

        print("output file [test.data]: ")
        val filename = readLine()
        trainer.save( if (filename == "") "test.data" else filename )
      case 2 =>
        val generator = new Generator()
        print("pattern file [test.data]: ")
        val filename = readLine()
        generator.load( if (filename == "") "test.data" else filename )
        print("words to generate [20]: ")
        val numWords = try {
           readInt()
        } catch {
          case ex => 20
        }
        for (i <- 1 to numWords) println(generator.generateWord(5, 12))
      case 3 =>
        sys.exit()
      case 4 =>
        println()
        val generator = new Generator()
        print("pattern file [test.data]: ")
        val filename = readLine()
        generator.load( if (filename == "") "test.data" else filename )

        for( (prevs, probs) <- generator.probs.toList.sortWith( (a, b) => (a._1._1*256 + a._1._2) < (b._1._1*256 + b._1._2))) {
          print(prevs)
          println(":")
          probs.foreach(v => println("   " + v))
        }
      case _ =>
    }

  }

}