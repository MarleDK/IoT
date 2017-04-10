#!/bin/bash

git stash
git checkout pushAndRun
git stash pop

git add .
git commit -m 'commiting to run by pushAndRun'
git push origin pushAndRun

ssh pi@raspi 'cd ~/IOT/pi/;git pull origin pushAndRun; git checkout -q pushAndRun; echo; echo;node blue.js 2>error.log;git checkout -q master;echo "Following errors occured: ";cat error.log;rm error.log'

ssh pi@raspi 'echo "Following errors occured: ";cat error.log'
