# sonar-cpp

Download version 2.6 from http://dist.sonar.codehaus.org/sonar-2.6.zip

unzip sonar-2.6 -d ~/sonar-2.6
cd sonar-2.6/bin/linux-x86-64/
./sonar.sh start

Browse to http://localhost:9000

Compile and install cccc

cd tools/cccc-3.1.4
make
supo cp cccc/cccc /usr/bin/cccc

Compile and install sonar cxx plugin

cd tools/sonar-cpp-plugin
mvn install
cp target/tomtom-sonar-cxx-plugin-0.0.4-SNAPSHOT.jar ~/sonar-2.6/extensions/plugins

Install cppcheck
sudo apt-get install cppcheck

Restart sonar
~/sonar-2.6/bin/linux-x86-64/sonar.sh restart

Analyze project

cd example-project
mvn sonar:sonar
