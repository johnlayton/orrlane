#!/usr/bin/env zsh

#cat <<'EOF' | docker build -t al2023-graalvm21:latest -f- .
#FROM public.ecr.aws/amazonlinux/amazonlinux:2023
#
#RUN yum -y update \
#    && yum install -y unzip tar gzip bzip2-devel ed gcc gcc-c++ gcc-gfortran \
#    less libcurl-devel openssl openssl-devel readline-devel xz-devel \
#    zlib-devel glibc-static zlib-static \
#    && rm -rf /var/cache/yum
#
## Graal VM
#ENV GRAAL_VERSION 21.0.2
#ENV GRAAL_FILENAME graalvm-community-jdk-${GRAAL_VERSION}_linux-x64_bin.tar.gz
#RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/jdk-${GRAAL_VERSION}/${GRAAL_FILENAME} | tar -xvz
#RUN mv graalvm-community-openjdk-${GRAAL_VERSION}* /usr/lib/graalvm
#ENV JAVA_HOME /usr/lib/graalvm
#
## Maven
#ENV MVN_VERSION 3.9.6
#ENV MVN_FOLDERNAME apache-maven-${MVN_VERSION}
#ENV MVN_FILENAME apache-maven-${MVN_VERSION}-bin.tar.gz
#RUN curl -4 -L https://archive.apache.org/dist/maven/maven-3/${MVN_VERSION}/binaries/${MVN_FILENAME} | tar -xvz
#RUN mv $MVN_FOLDERNAME /usr/lib/maven
#RUN ln -s /usr/lib/maven/bin/mvn /usr/bin/mvn
#
## Gradle
#ENV GRADLE_VERSION 8.8
#ENV GRADLE_FOLDERNAME gradle-${GRADLE_VERSION}
#ENV GRADLE_FILENAME gradle-${GRADLE_VERSION}-bin.zip
#RUN curl -LO https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
#RUN unzip gradle-${GRADLE_VERSION}-bin.zip
#RUN mv $GRADLE_FOLDERNAME /usr/lib/gradle
#RUN ln -s /usr/lib/gradle/bin/gradle /usr/bin/gradle
#
#VOLUME /project
#WORKDIR /project
#
##WORKDIR /orrlane
#EOF

#docker run -it \
#  -v `pwd`:`pwd` \
#  -w `pwd` \
#  -v ~/.m2:/root/.m2 \
#  al2023-graalvm21:latest \
#  ./mvnw -Pnative clean package -DskipTests

#docker run -it \
#  -v `pwd`:`pwd` \
#  -w `pwd` \
#  al2023-graalvm21:latest \
#  gradle -Pnative app:clean app:nativeCompile app:customDistZip app:createFunctionZip

#docker run -it \
#  -v `pwd`:`pwd` \
#  -w `pwd` \
#  al2023-graalvm21:latest \
#  gradle -Pnative app:clean app:nativeCompile

./gradlew app:clean app:nativeCompile

#gradle -Pnative app:clean app:nativeCompile

#./mvnw -Pnative clean package

#./gradlew -Pnative clean nativeCompile

#./gradlew clean nativeCompile

#sam deploy --guided

#sam deploy