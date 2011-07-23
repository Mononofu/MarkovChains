package markovchains

/**
 * Created by IntelliJ IDEA.
 * User: mononofu
 * Date: 7/23/11
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.{ObjectOutputStream, FileOutputStream}

class Trainer {
  val wordRegex = """\b(\w+)\b""".r
  // key: two previous chars
  // value: total number of next chars, map of number of times each char occurs
  val counts = scala.collection.mutable.Map[(Char, Char), (Int, Map[Char, Int])]()

  // key: two previo
  // value: list of next chars with probabilities
  val probs = scala.collection.mutable.Map[(Char, Char), List[(Double, Char)]]()

  def save(filename: String) {
    val fos = new FileOutputStream(filename)
    val oos = new ObjectOutputStream(fos)
    oos.writeObject(probs)
    oos.close()
  }

  def learnFile(filename: String, minLen: Int = 3, maxLen: Int = 14) {
    learn(io.Source.fromFile(filename).mkString, minLen, maxLen)
  }

  def learn(data: String, minLen: Int = 3, maxLen: Int = 14) {
    for (word <- wordRegex findAllIn data.replaceAll("\\d", "") if (minLen <= word.length() && word.length() <= maxLen)) {
      var prev, pprev = ' '

      for (c <- (word.toLowerCase + ' ')) {
        val (numTransitions, transitions) = counts.getOrElse((pprev, prev), (0, Map[Char, Int]()))
        counts += ((pprev, prev) -> (numTransitions + 1, (transitions + (c -> (transitions.getOrElse(c, 0) + 1)))))

        pprev = prev
        prev = c
      }
    }
    updateProbs()
  }

  def updateProbs() {
    probs.clear()
    for (k <- counts.keys) {
      val (numTransitions, transitions) = counts(k)
      probs(k) = (for ((char, freq) <- transitions) yield (1.0 * freq / numTransitions, char)).toList.sortWith(_._1 > _._1)
    }
  }
}