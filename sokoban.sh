if [ ! -e out/ ]; then
   echo "Creating out/ ..."
   mkdir -p out/
fi
javac src/*.java -d out/.
cd out/
java Demo
