#!/bin/bash

sbt package

if [ $? != 0 ]
then
  echo "'sbt package' failed, exiting now"
  exit 1
fi

cp target/scala-2.10/hourlychime_2.10-0.1.jar HourlyChime.jar

ls -l HourlyChime.jar

echo ""
echo "Created HourlyChime.jar. Copy that file to /Users/al/Sarah/plugins/DDHourlyChime, like this:"
echo "cp HourlyChime.jar /Users/al/Sarah/plugins/DDHourlyChime"

