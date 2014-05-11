
Sarah 'Twitter' Plugin
======================

This plugin enables Sarah to check some Twitter information for you.
If you say something like, "Get Twitter trends", this plugin will be triggered,
and will retrieve the latest trends and read them to you.


Files
-----

The jar file built by this project needs to be copied to the Sarah plugins directory.
On my computer that directory is _/Users/al/Sarah/plugins/DDCurrentTime_.

Files in that directory should be:

    Twitter.jar
    Twitter.info
    Twitter.properties
    README.txt

The _Twitter.info_ file currently contains these contents:

    main_class = com.devdaily.sarah.plugin.twitter.InteractiveTwitterClient
    plugin_name = Twitter

The _Twitter.properties_ file currently has these contents:

    # This is the woeid for the United States. The woeid for the 'world' is 1.
    woeid=23424977

    # this is the text you say to trigger this plugin.
    # it must match the text in the sarah.gram file.
    what_you_say=current trends

    # Twitter4J properties
    oauth.consumerKey=KEY1
    oauth.consumerSecret=KEY2
    oauth.accessToken=KEY3
    oauth.accessTokenSecret=KEY4
    twitter_username=USERNAME

Change the `woeid` setting in that file to get current trends for different locations.


To-Do
-----

* It would be good if this plugin handled multiple woeids. Then you could say,
  "Get the current US trends", "Get the Boulder trends", etc.
* I'd like this plugin to show and or speak twitter posts from my lists and
  saved searches.

Some things in this project are a kludge, mainly because this plugin has the same
dependencies that Sarah has, and I'm not doing any dynamic class loading. 
So, in short, I've copied those dependencies here, both in the _lib_ folder and in 
the _build.sbt_ file. At the moment I don't need to deploy those dependences to the 
final "plugin" folder destination (_/Users/al/Sarah/plugins/DDTwitter_).
So, as a to-do item, I need to improve this build process.

I also need to improve the Sarah2 jar-building process, because this plugin and all
other plugins are dependent on that jar, but that's more of a Sarah2 "to do" than 
anything that needs to be done here. 


Developers - Building this Plugin
---------------------------------

You can build this plugin using the shell script named _build-jar.sh. It currently looks like this:

    #!/bin/bash

    sbt package

    if [ $? != 0 ]
    then
        echo "'sbt package' failed, exiting now"
        exit 1
    fi

    cp target/scala-2.10/twitter_2.10-0.1.jar Twitter.jar

    ls -l Twitter.jar

    echo ""
    echo "Created Twitter.jar. Copy that file to /Users/al/Sarah/plugins/DDTwitter, like this:"
    echo "cp Twitter.jar /Users/al/Sarah/plugins/DDTwitter"


Dependencies
------------

This plugin depends on:

* The Sarah2.jar file.
* The Akka/Scala actors. The actor version needs to be kept in sync with whatever actor version
  Sarah2 uses.

As mentioned above, I need to improve the process of requiring and using the Sarah2.jar file,
but that's more of a problem for the Sarah2 project than for this project. 









