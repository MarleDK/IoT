#!/bin/bash

git stash
git checkout pushAndRun
git stash pop

git add .
git commit -m 'commiting to run by pushAndRun'
git push origin pushAndRun

ssh pi@raspi 'cd IOT/pi/;git pull origin pushAndRun; git checkout pushAndRun; echo '';node blue.js'



#ssh pi@raspi
