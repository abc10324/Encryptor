# Encryptor
encrypt/decrypt by AES256 algorithm  
  
## Environment  
 > Project: Gradle  
 > Framework: Spring MVC    
 > Server: Tomcat v9.0  

## Function  
 > encrypt by AES256  
 > End point : POST \<url\>/Encryptor/encrypt   
 > Content-Type : application/x-www-form-urlencoded  
 > Field name : algorithm(fixed AES256) / encryptSrc 
 > decrypt by AES256  
 > End point : POST \<url\>/Encryptor/decrypt   
 > Content-Type : application/x-www-form-urlencoded  
 > Field name : algorithm(fixed AES256) / decryptSrc 
 
## how to use gradleDeploy.bat and gradleDebugDeploy.bat  
 1.set environment variable TOMCAT_HOME to your tomcat install location (ex:D:\apache-tomcat-9.0.22)  
 2.set environment variable JAVA_HOME to your java install location (ex:C:\Program Files\ojdkbuild\java-1.8.0-openjdk)  
 3.run gradleDeploy.bat/gradleDebugDeploy.bat(for remote debug, open port 8000)
 
