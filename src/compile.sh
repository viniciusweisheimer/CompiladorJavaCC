# /bin/bash

javacc parser/Fun.jj
mv *.java parser/
javac parser/*.java

