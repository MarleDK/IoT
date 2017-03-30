#!/bin/bash

git add .
git commit -m 'commiting to run by pushAndRun'
git push

ssh pi@raspi 'cd IOT/pi/; git pull origin master; node blue.js > output.txt'



#ssh pi@raspi
