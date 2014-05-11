package com.devdaily.sarah.plugin.twitter

import twitter4j.conf.ConfigurationBuilder
import twitter4j.Twitter
import twitter4j.TwitterFactory

trait TwitterBase {

  // twitter4j properties (get these from your properties file)
  var consumerKey = ""
  var consumerSecret = ""
  var accessToken = ""
  var accessTokenSecret = ""
  var twitterUsername = ""

  // add to twitter4j.properties
  // https://dev.twitter.com/discussions/2757
  // http.useSSL=true
      
  def getTwitterInstance: Twitter = {
      val cb = new ConfigurationBuilder()
      cb.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
      return new TwitterFactory(cb.build()).getInstance
  }

  def populateTwitterProperties(properties: java.util.Properties) {
      consumerKey = properties.getProperty("oauth.consumerKey")
      consumerSecret = properties.getProperty("oauth.consumerSecret")
      accessToken = properties.getProperty("oauth.accessToken")
      accessTokenSecret = properties.getProperty("oauth.accessTokenSecret")
      twitterUsername = properties.getProperty("twitter_username")
  }
 
}

