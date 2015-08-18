#/bin/sh

convert ic_launcher.svg -background transparent -gravity center -scale 174x174 -extent 192x192 res/drawable-xxxhdpi/ic_launcher.png
convert ic_launcher.svg -background transparent -gravity center -scale 130x130 -extent 144x144 res/drawable-xxhdpi/ic_launcher.png
convert ic_launcher.svg -background transparent -gravity center -scale 90x90 -extent 96x96 res/drawable-xhdpi/ic_launcher.png
convert ic_launcher.svg -background transparent -gravity center -scale 68x68 -extent 72x72 res/drawable-hdpi/ic_launcher.png
convert ic_launcher.svg -background transparent -gravity center -scale 46x46 -extent 48x48 res/drawable-mdpi/ic_launcher.png
convert ic_launcher.svg -background transparent -gravity center -scale 34x34 -extent 36x36 res/drawable-ldpi/ic_launcher.png
convert ic_launcher.svg -background transparent -gravity center -scale 500x500 -extent 512x512 ic_launcher-web.png

