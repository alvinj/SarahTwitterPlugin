package com.devdaily.sarah.plugin.twitter

import com.devdaily.sarah.plugins._
import java.io._
import scala.collection.mutable.StringBuilder
import java.util.Properties
import scala.collection.mutable.ListBuffer
import akka.actor.ActorSystem
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import twitter4j.TwitterException
import twitter4j.{Paging, Query, Twitter}

/**
 * Get (a) the current weather or (b) the weather forecast.
 */
class InteractiveTwitterClient extends SarahPlugin with TwitterBase {

  val relativePropertiesFileName = "Twitter.properties"
  val phrasesICanHandle = new ListBuffer[String]()
  var canonPluginDirectory = ""

  // read from properties file
  val woeidKey = "woeid"
  var woeid = 1 // 1 == world

  val phraseKey = "what_you_say"
  var twitterPhrase = "current trends"
 
  // this used to be in 'handlePhrase' (which was wrong)
  implicit val system = ActorSystem("TwitterFutureSystem")

  // sarah callback
  def textPhrasesICanHandle: List[String] = {
      return phrasesICanHandle.toList
  }

  // sarah callback
  override def setPluginDirectory(dir: String) {
      canonPluginDirectory = dir
      val propertiesFilename = getCanonPropertiesFilename
      populatePropertiesFromConfigFile(propertiesFilename) 
  }

  // NOTE - startPlugin is no longer called by Sarah because Akka no longer works like that;
  // functionality was moved to setPluginDirectory
  def startPlugin {}

  // sarah callback. handle our phrases when we get them.
  def handlePhrase(phrase: String): Boolean = {
      if (phrase.trim.equalsIgnoreCase(twitterPhrase)) {
          val f = Future { brain ! PleaseSay("Stand by.") }
          brain ! PleaseSay(getCurrentTrends)
          true
      } else {
          false
      }
  }

  def getCanonPropertiesFilename: String = {
      canonPluginDirectory + PluginUtils.getFilepathSeparator + relativePropertiesFileName
  }

  /**
   * Returns the string that Sarah will say.
   */
  private def getCurrentTrends: String = {
      val future = Future { getCurrentTrendsAsString2(woeid) }
      Await.result(future, 8 seconds)
  }
  
  def getCurrentTrendsAsString2(desiredWoeid: Int): String = {
      try {
          val twitter = getTwitterInstance
          val trends = getLocationTrends(twitter, desiredWoeid)
          convertTrendsToString(trends)
      } catch {
          case te: TwitterException => {
              te.printStackTrace
              "Sorry, I could not get the current Twitter trends"
          }
      }
  }

  // getPlaceTrends: returns the top 10 trending topics for a specific WOEID
  // @see The Twitter class at http://twitter4j.org/javadoc/index.html
  private def getLocationTrends(twitter: Twitter, loc: Int): Array[twitter4j.Trend] = {
      val dailyTrends = twitter.getPlaceTrends(loc)
      dailyTrends.getTrends
  }

  private def convertTrendsToString(trends: Array[twitter4j.Trend]): String = {
      var sb = new scala.collection.mutable.StringBuilder
      for (t <- trends) {
          val a = t.getName.replace("#", "")
          val b = splitCamelCaseStringIntoWords(a)
          sb.append(b + ". \n")
      } 
      sb.toString
  }
  
  // convert a string like "FooBarBaz" to "Foo Bar Baz"
  def splitCamelCaseStringIntoWords(s: String): String = {
      return s.replaceAll(
          String.format("%s|%s|%s",
              "(?<=[A-Z])(?=[A-Z][a-z])",
              "(?<=[^A-Z])(?=[A-Z])",
              "(?<=[A-Za-z])(?=[^A-Za-z])"
          ),
          " "
      ).replaceAll("  ", " ")
  }
  
  // TODO handle exceptions
  def populatePropertiesFromConfigFile(canonConfigFilename: String) {
      val properties = new Properties()
	  val in = new FileInputStream(canonConfigFilename)
      properties.load(in)
      in.close()
      woeid = properties.getProperty(woeidKey).toInt
      twitterPhrase = properties.getProperty(phraseKey)
      populateTwitterProperties(properties)
      phrasesICanHandle += twitterPhrase
  }
  
}









