#!/bin/bash

git stash
git checkout pushAndRun
git stash pop

git add .
git commit -m 'commiting to run by pushAndRun'
git push origin pushAndRun

ssh pi@raspi 'cd ~/IOT/pi/; git checkout -q pushAndRun;git pull origin pushAndRun; echo; echo;sudo node blue.js;echo "Following errors occured: "'
