#!/bin/bash

git stash
git checkout pushAndRun
git stash pop

git add .
git commit -m 'commiting to run by pushAndRun'
git push origin pushAndRun

ssh pi@raspi 'cd IOT/pi/;git pull origin pushAndRun; git checkout pushAndRun > /dev/null; echo; echo;node blue.js;git checkout master > /dev/null'



#ssh pi@raspi
