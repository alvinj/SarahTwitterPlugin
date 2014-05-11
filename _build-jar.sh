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

