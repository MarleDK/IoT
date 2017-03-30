#!/bin/bash

git add .
git commit -m 'commiting to run by pushAndRun'
git push

ssh pi@raspi 'git pull; node blue.js > output.txt'



#ssh pi@raspi
