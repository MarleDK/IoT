#!/bin/bash

git stash
git checkout pushAndRun
git stash pop
git add .
git commit -m 'commiting to run by pushAndRun'
git push

ssh pi@raspi 'cd IOT/pi/;git pull origin pushAndRun; git checkout pushAndRun; node blue.js > output.txt'



#ssh pi@raspi
